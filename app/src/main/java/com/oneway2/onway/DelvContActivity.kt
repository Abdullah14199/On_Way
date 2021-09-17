package com.oneway2.onway

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_delv_cont.*
import kotlinx.android.synthetic.main.activity_main2.layoutDisconnect
import java.lang.reflect.Field

class DelvContActivity : AppCompatActivity() {

    private var flage: Boolean = true
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delv_cont)
        loadURL()

        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {

            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
                webview5.reload()
            }, 4000)
        }

        if (ConnectionManager.checkConnection(baseContext)) {
            // we are disabling layoutDisconnect for checking connection
            // and enabling webView

            webview5.visibility = View.VISIBLE
            layoutDisconnect.visibility = View.INVISIBLE
        } else {
            // if not connected to internet
            // and enabling layoutDisconnect
            // we are disabling webView for checking connection

            webview5.visibility = View.INVISIBLE
            layoutDisconnect.visibility = View.VISIBLE

        }

        navigation_view2.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
                R.id.nav_contact -> {
                    val contact = "+972 59-805-1195" // use country code with your phone number

                    val url = "https://api.whatsapp.com/send?phone=$contact"
                    try {
                        val pm: PackageManager = this.getPackageManager()
                        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    } catch (e: PackageManager.NameNotFoundException) {
                        Toast.makeText(
                            this,
                            "Whatsapp app not installed in your phone",
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }
                }
                R.id.nav_orders -> {
                    val intent = Intent(this, MainActivity3::class.java)
                    startActivity(intent)
                }
                R.id.nav_add_res -> {
                    val intent = Intent(this, RestActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_res_controll -> {
                    val intent = Intent(this, RestConActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_del_controll -> {
                    val intent = Intent(this, DelvContActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_about_us -> {
                    val intent = Intent(this, imageActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }



        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout2,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout2.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }

    override fun onBackPressed() {
        if (drawer_layout2.isDrawerOpen(GravityCompat.START)) {
            drawer_layout2.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout2.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }



}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview5.canGoBack()) {
            webview5.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadURL() {
        webview5.loadUrl("http://www.onway.ps/cpanel-delivery")
        webview5.settings.javaScriptEnabled = true
        webview5.settings.loadsImagesAutomatically = true

        webview5.webViewClient = WebViewClient()
    }

    override fun onPause() {
        flage = false
        super.onPause()
    }

}