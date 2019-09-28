package com.danjorn.ui.availableChats

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.danjorn.ktx.openChat
import com.danjorn.models.ChatResponse
import com.danjorn.presentation.availableChats.AvailableChatsViewModel
import com.danjorn.ui.availableChats.list.ChatAdapter
import com.danjorn.ui.createChat.CreateChatActivity
import com.danjorn.views.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_main.*

private const val RC_SIGN_IN = 1

class AvailableChatsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    private val tag = AvailableChatsActivity::class.java.simpleName

    private lateinit var barToggle: ActionBarDrawerToggle
    private lateinit var refreshLayout: SwipeRefreshLayout

    private lateinit var viewModel: AvailableChatsViewModel
    private var chatsAdapter = ChatAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(AvailableChatsViewModel::class.java)

        if (!userLoggedIn()) {
            showLoginActivity()
        }

        initDrawerLayout()
        refreshLayout = refresh_layout
        refreshLayout.setOnRefreshListener(this)

        chats_recycler.adapter = chatsAdapter

        viewModel.chatsLiveData.observe(this, Observer {
            onChatChanged(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> handleSignInResult(resultCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main, menu) //TODO Clean all these onWhatever methods
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (barToggle.onOptionsItemSelected(item)) return true
        when (item?.itemId) {
            R.id.action_refresh -> {
                refreshLayout.isRefreshing = true
                refreshChats()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        refreshChats()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        barToggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_go_to_create_chat -> handleGoToCreateChat()
            R.id.menu_action_go_to_test_chat -> handleGoToTestChat()
        }
        return true
    }

    private fun handleGoToCreateChat() {
        startActivity(Intent(this, CreateChatActivity::class.java))
    }

    private fun handleGoToTestChat() {
        openChat("TestChat")
    }

    private fun onChatChanged(chatResponse: ChatResponse) {
        chatsAdapter.addOrUpdateChat(chatResponse)
    }

    private fun userLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    //TODO Cancel refreshing of refresh_layout if user doesn't grant a permission
    private fun refreshChats() = runWithPermissions(Manifest.permission.ACCESS_FINE_LOCATION) {
        viewModel.userRefreshChats {
            refreshLayout.isRefreshing = false
        }
    }

    private fun handleSignInResult(resultCode: Int) {
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, getString(R.string.msg_successfully_signed_in), Toast.LENGTH_SHORT).show()
        } else {
            showLoginDialog()
        }
    }

    private fun showLoginActivity() {
        viewModel.showLoginActivity(this, RC_SIGN_IN)
    }

    private fun showLoginDialog() {
        val dialog = AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_auth_necessary))
                .setNegativeButton(getString(R.string.action_leave_app)) { _, _ -> this.finishAffinity() }
                .setPositiveButton(getString(R.string.action_authenticate)) { _, _ -> showLoginActivity() }
        dialog.create().show()
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