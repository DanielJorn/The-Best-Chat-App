package com.danjorn.presentation.availableChats

import android.app.Activity
import android.app.Application
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.danjorn.coroutines.suspendLocation
import com.danjorn.database.CHATS
import com.danjorn.database.FirebaseDatabaseManager
import com.danjorn.ktx.getValueAndId
import com.danjorn.ktx.toDatabaseRef
import com.danjorn.models.ChatResponse
import com.danjorn.utils.liveData.FirebaseObserveLiveData
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.launch

class AvailableChatsViewModel(application: Application) : AndroidViewModel(application) {

    private val tag = AvailableChatsViewModel::class.java.simpleName
    private val rootChatRef = CHATS.toDatabaseRef()
    private val databaseManager = FirebaseDatabaseManager()

    private val _databaseErrorLiveData = MutableLiveData<Throwable>()
    val databaseErrorLiveData: LiveData<Throwable> = _databaseErrorLiveData

    private val _locationErrorLiveData = MutableLiveData<Throwable>()
    val locationErrorLiveData: LiveData<Throwable> = _locationErrorLiveData

    private val chatUpdateObserver = FirebaseObserveLiveData(rootChatRef)
    private val loadedChats = MutableLiveData<ChatResponse>()

    val chatsLiveData = MediatorLiveData<ChatResponse>().apply {
        addSource(chatUpdateObserver) { snapshot ->
            val chatPojo = snapshot.getValueAndId(ChatResponse::class.java)
            this.value = chatPojo
        }
        addSource(loadedChats) { chatPojo ->
            this.value = chatPojo
        }
    }

    fun userRefreshChats(onComplete: () -> Unit) {
        getUserLocation { location ->
            databaseManager.getAvailableChats(location,
                    onComplete = onComplete,
                    onChatGot = {
                        startObserveChat(it.id!!)
                        //loadedChats.value = it // We have to map it to Ui version of Chat in Manager...
                    },
                    onError = this::notifyDatabaseError)
        }
    }

    private fun notifyDatabaseError(error: Throwable) {
        _databaseErrorLiveData.value = error
    }

    fun showLoginActivity(activity: Activity, requestCode: Int) {

        val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build()
        )

        ActivityCompat.startActivityForResult(
                activity,
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                requestCode,
                null
        )
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