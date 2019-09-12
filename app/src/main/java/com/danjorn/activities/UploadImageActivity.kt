package com.danjorn.activities

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.views.R
import kotlinx.android.synthetic.main.activity_upload_image.*
import java.io.File

class UploadImageActivity : AppCompatActivity(), View.OnClickListener {

    private val tag = UploadImageActivity::class.java.simpleName

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        test_button_upload_todo_remove_it.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.test_button_upload_todo_remove_it -> {
                val rootStorage = Environment.getExternalStorageDirectory()
                val exactlyFile = File("$rootStorage/download/q1.jpeg")//or whatever

                // CloudStorageUtils.uploadFile("/example1/" + exactlyFile.name, exactlyFile)
                //CloudStorageUtils.getDownloadURL("/example1/" + exactlyFile.name, this)
            }
        }
    }
}
