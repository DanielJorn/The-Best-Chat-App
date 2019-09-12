package com.danjorn.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.danjorn.models.database.ChatPojo
import com.danjorn.utils.OverflowTextWatcher
import com.danjorn.viewModels.CreateChatViewModel
import com.danjorn.views.R
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_create_chat.*

private const val GALLERY_REQUEST_CODE = 1

class CreateChatActivity : AppCompatActivity(), View.OnClickListener {

    private var chatImage: String? = null

    private val tag = CreateChatActivity::class.java.simpleName
    private lateinit var viewModel: CreateChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_chat)

        edit_chat_title.editText?.addTextChangedListener(OverflowTextWatcher(edit_chat_title))
        edit_chat_radius.editText?.addTextChangedListener(OverflowTextWatcher(edit_chat_radius))

        btn_choose_chat_image.setOnClickListener(this)
        btn_upload_chat.setOnClickListener(this)

        viewModel = ViewModelProviders.of(this).get(CreateChatViewModel::class.java)

        viewModel.liveData.observe(this, Observer {
            Log.d(tag, "onCreate: the id of the chat is $it")
            //Go to the chat or say what error is :) (problems only with uploading/accessing to GPS)
        })

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_choose_chat_image -> {
                imageFromGallery()
            }
            R.id.btn_upload_chat -> {
                //CheckAllFields(){
                //  onFieldsNormal {
                //      uploadChat
                //  }
                //  onFieldsUbnormal {
                //      showToUserMistakes
                //  }
                //}
                //Log.d(tag, "onClick: clicked")
                //uploadChat(application, edit_chat_title.getText(), chatImage, edit_chat_radius.getText().toInt()) {
                //    Toast.makeText(this, "success man", Toast.LENGTH_SHORT).show()
                //}
                //Toast.makeText(this, "the image is $chatImage", Toast.LENGTH_SHORT).show()

                // LocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, )
                val chatPojo = ChatPojo(edit_chat_title.getText(), chatImage, edit_chat_radius.getText().toInt())
                viewModel.uploadChat(this, chatPojo)
            }
        }
    }

    private fun imageFromGallery() = runWithPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    chatImage = data?.data.toString()
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                } else Log.d(tag, "onActivityResult: something is wrong with image or whatever")
            }
        }
    }
}
