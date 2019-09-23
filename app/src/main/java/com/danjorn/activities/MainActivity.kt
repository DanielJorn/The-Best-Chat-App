package com.danjorn.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.danjorn.models.ChatPojo
import com.danjorn.viewModels.MainViewModel
import com.danjorn.views.R
import com.danjorn.views.adapters.ChatAdapter
import com.google.android.material.navigation.NavigationView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_main.*


private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var barToggle: ActionBarDrawerToggle

    private lateinit var viewModel: MainViewModel

    private val tag = MainActivity::class.java.simpleName

    private var chatsAdapter = ChatAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawerLayout()

        chats_recycler.adapter = chatsAdapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.chatsLiveData.observe(this, Observer {
            onChatChanged(it)
        })

    }

    private fun onChatChanged(chatPojo: ChatPojo) {
        chatsAdapter.addOrUpdateChat(chatPojo)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this,
                        getString(R.string.msg_successfully_signed_in),
                        Toast.LENGTH_SHORT).show()
            } else {
                val dialog = AlertDialog.Builder(this)
                        .setMessage(getString(R.string.msg_auth_necessary))
                        .setNegativeButton(getString(R.string.action_leave_app))
                        { _: DialogInterface, _: Int ->
                            this.finishAffinity()
                        }
                        .setPositiveButton(getString(R.string.action_authenticate))
                        { _: DialogInterface, _: Int ->
                            viewModel.showLoginActivity(this, RC_SIGN_IN)
                        }
                dialog.create().show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu) //TODO Clean all these onWhatever methods
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (barToggle.onOptionsItemSelected(item)) return true
        else {
            when (item?.itemId) {
                R.id.action_refresh -> {
                    refreshChats()
                }
            }
        }
        return false
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        barToggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_create_chat -> {
                startActivity(Intent(this, CreateChatActivity::class.java))
            }
            R.id.menu_action_go_to_test_chat -> {
                val intent = Intent(this, ChatRoomActivity::class.java)
                intent.putExtra("room", "TestChat") //TODO Put something real at all

                startActivity(intent)
            }
        }
        return true
    }

    private fun refreshChats() = runWithPermissions(Manifest.permission.ACCESS_FINE_LOCATION) {
        viewModel.userUpdatesChats()
    }

    private fun initDrawerLayout() {

        val navView = main_nav_layout
        val drawerLayout = main_drawer_layout

        navView.setNavigationItemSelectedListener(this)
        navView.itemIconTintList = null

        barToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawerLayout.addDrawerListener(barToggle)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    } //TODO bad init of the drawer layout . Find way to simplify it. UPD: is the initDrawerLayout a hack?
}



