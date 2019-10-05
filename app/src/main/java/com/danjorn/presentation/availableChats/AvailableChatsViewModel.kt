package com.danjorn.presentation.availableChats

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.lifecycle.*
import com.danjorn.coroutines.suspendLocation
import com.danjorn.firebase.auth.FirebaseAuthManager
import com.danjorn.firebase.database.CHATS
import com.danjorn.firebase.database.FirebaseDatabaseManager
import com.danjorn.ktx.getValueAndId
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.ChatResponse
import com.danjorn.models.UIChat
import com.danjorn.ui.createChat.CreateChatActivity
import com.danjorn.utils.liveData.FirebaseObserveLiveData
import kotlinx.coroutines.launch

class AvailableChatsViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = AvailableChatsViewModel::class.java.simpleName
    private val rootChatRef = CHATS.toDatabaseRef()

    private val databaseManager = FirebaseDatabaseManager()
    private val authManager = FirebaseAuthManager()

    //It's overkill to have several instances of live data just to show some error but if we want to show
    //something more complex than a Toast with a error message, we should use this approach
    private val _databaseErrorLiveData = MutableLiveData<Throwable>()
    val databaseErrorLiveData: LiveData<Throwable> = _databaseErrorLiveData

    private val _locationErrorLiveData = MutableLiveData<Throwable>()
    val locationErrorLiveData: LiveData<Throwable> = _locationErrorLiveData

    private val chatUpdateObserver = FirebaseObserveLiveData(rootChatRef)
    private val loadedChats = MutableLiveData<UIChat>()

    val chatsLiveData = MediatorLiveData<UIChat>().apply {
        addSource(chatUpdateObserver) { snapshot ->
            val chatResponse = snapshot.getValueAndId(ChatResponse::class.java)
            this.value = chatResponse!!.toUIChat()
        }
        addSource(loadedChats) { chatPojo ->
            this.value = chatPojo
        }
    }

    fun goToCreateChat(context: Context) {
        context.startActivity(Intent(context, CreateChatActivity::class.java))
    }

    fun userRefreshChats(onComplete: () -> Unit) {
        getUserLocation { location ->
            databaseManager.getAvailableChats(location,
                    onComplete = onComplete,
                    onChatGot = {
                        startObserveChat(it.id!!)
                        loadedChats.postValue(it)
                    },
                    onError = this::notifyDatabaseError)
        }
    }

    fun loginUserIfNeeded(activity: Activity, requestCode: Int) {
        if (userNotLoggedIn()) {
            showLoginActivity(activity, requestCode)
        }
    }

    private fun showLoginActivity(launchFrom: Activity, requestCode: Int) {
        authManager.showLoginActivity(launchFrom, requestCode)
    }

    private fun userNotLoggedIn(): Boolean {
        return !authManager.isUserLoggedIn()
    }

    private fun notifyDatabaseError(error: Throwable) {
        _databaseErrorLiveData.value = error
    }

    private fun startObserveChat(chatId: String) {
        chatUpdateObserver.addUpdateListener(chatId)
    }

    private fun getUserLocation(onLocationGot: (Location) -> Unit) {
        viewModelScope.launch {
            try {
                val location = suspendLocation(getApplication())
                onLocationGot(location)
            } catch (error: Throwable) {
                _locationErrorLiveData.value = error
            }
        }
    }
}