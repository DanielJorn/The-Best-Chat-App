package com.danjorn.ui.createChat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.danjorn.ktx.openChat
import com.danjorn.models.ChatResponse
import com.danjorn.presentation.createChat.CreateChatViewModel
import com.danjorn.ui.createChat.input.OverflowTextWatcher
import com.danjorn.views.R
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_create_chat.*

private const val RC_GALLERY = 1

class CreateChatActivity : AppCompatActivity() {

    private val tag = CreateChatActivity::class.java.simpleName

    private var chatImageUri: Uri? = null
    private lateinit var viewModel: CreateChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_chat)

        setListeners()
        setEditTextWatchers()

        viewModel = ViewModelProviders.of(this).get(CreateChatViewModel::class.java)

        viewModel.onChatCreatedLiveData.observe(this, Observer { chatId ->
            openChat(chatId)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_GALLERY ->
                if (resultCode == Activity.RESULT_OK) {
                    chatImageUri = data?.data
                    image_preview.setImageURI(chatImageUri)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setListeners() {
        btn_choose_chat_image.setOnClickListener { getImageFromGallery() }
        btn_upload_chat.setOnClickListener { uploadChat() }
    }

    private fun setEditTextWatchers() {
        edit_chat_title.editText?.addTextChangedListener(OverflowTextWatcher(edit_chat_title))
        edit_chat_radius.editText?.addTextChangedListener(OverflowTextWatcher(edit_chat_radius))
    }

    private fun uploadChat() = runWithPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION) {
        viewModel.uploadChat(this, createChatFromInput(), chatImageUri)
    }

    private fun createChatFromInput(): ChatResponse {
        return ChatResponse(null, edit_chat_title.getText(), null, edit_chat_radius.getText().toInt())
    }

    private fun getImageFromGallery() = runWithPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RC_GALLERY)
    }
}
