package com.danjorn.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.danjorn.configs.MAX_RADIUS
import com.danjorn.configs.chatLocation
import com.danjorn.coroutines.getListOfKeys
import com.danjorn.coroutines.suspendLocation
import com.danjorn.models.database.ChatPojo
import com.danjorn.presenters.MainActivityPresenter
import com.danjorn.views.R
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val RC_SIGN_IN = 1

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var barToggle: ActionBarDrawerToggle

    private val presenter = MainActivityPresenter()

    private val tag = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawerLayout()

        GlobalScope.launch(Dispatchers.Main.immediate) {
            loadChats(application){

            }
        }


        //MainViewModel(application).updateAvailableChats()?.observe(this, Observer {
        //    recycler_view.adapter = ChatAdapter(this, it)
        //})
    }


    private suspend fun loadChats(application: Application, onFinish: () -> Unit) = runWithPermissions(Manifest.permission.ACCESS_FINE_LOCATION) {
        GlobalScope.launch(Dispatchers.Main.immediate) {

            val location = suspendLocation(application)
            val listOfLocation = loadChatsWithRadius(location)


            onFinish()
        }
    }

    suspend fun loadChatsWithRadius(location: Location) : ArrayList<String>{
        val geofireRef = FirebaseDatabase.getInstance().getReference(chatLocation)
        val geoFire = GeoFire(geofireRef)
        val geoQuery = geoFire.queryAtLocation(GeoLocation(location.latitude,
                location.longitude), MAX_RADIUS.toDouble())

        return getListOfKeys(geoQuery)
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
                            presenter.showLoginActivity(this, RC_SIGN_IN)
                        }
                dialog.create().show()
            } //TODO Bad creation of dialogs, same code in two places
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (barToggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
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
                intent.putExtra("room", "TestChat")

                startActivity(intent)
            }
        }
        return true
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



