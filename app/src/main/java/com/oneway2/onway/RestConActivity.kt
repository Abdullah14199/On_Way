package com.oneway2.onway

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main2.layoutDisconnect
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_rest.*
import kotlinx.android.synthetic.main.activity_rest_con.*
import java.lang.reflect.Field

class RestConActivity : AppCompatActivity() {
    private var flage: Boolean = true
    private var mUploadMessage: ValueCallback<Uri>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 1
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_con)
        loadURL()

        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {

            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
                webview4.reload()
            }, 4000)
        }
        val mWebSettings: WebSettings = webview4.getSettings()
        mWebSettings.javaScriptEnabled = true
        mWebSettings.setSupportZoom(false)
        mWebSettings.allowFileAccess = true
        mWebSettings.allowContentAccess = true

        webview4.setWebChromeClient(object : WebChromeClient() {
            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected fun openFileChooser(uploadMsg: ValueCallback<*>, acceptType: String?) {
                uploadMsg.also { mUploadMessage = it as ValueCallback<Uri> }
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            // For Lollipop 5.0+ Devices
            override fun onShowFileChooser(
                mWebView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }
                uploadMessage = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE)
                } catch (e: ActivityNotFoundException) {
                    uploadMessage = null
                    Toast.makeText(
                        applicationContext,
                        "Cannot Open File Chooser",
                        Toast.LENGTH_LONG
                    ).show()
                    return false
                }
                return true
            }

            //For Android 4.1 only
            protected fun openFileChooser(
                uploadMsg: ValueCallback<Uri?>,
                acceptType: String?,
                capture: String?
            ) {
                uploadMsg.also { mUploadMessage = it as ValueCallback<Uri> }
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri?>) {
                uploadMsg.also { mUploadMessage = it as ValueCallback<Uri> }
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }
        })

        if (ConnectionManager.checkConnection(baseContext)) {
            // we are disabling layoutDisconnect for checking connection
            // and enabling webView

            webview4.visibility = View.VISIBLE
            layoutDisconnect.visibility = View.INVISIBLE
        } else {
            // if not connected to internet
            // and enabling layoutDisconnect
            // we are disabling webView for checking connection

            webview4.visibility = View.INVISIBLE
            layoutDisconnect.visibility = View.VISIBLE

        }

        navigation_view5.setNavigationItemSelectedListener{
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
            drawer_layout5,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout5.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }

    override fun onBackPressed() {
        if (drawer_layout5.isDrawerOpen(GravityCompat.START)) {
            drawer_layout5.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout5.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview4.canGoBack()) {
            webview4.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadURL() {
        webview4.loadUrl("http://onway.ps/cpanel-resturants")
        webview4.settings.javaScriptEnabled = true
        webview4.settings.loadsImagesAutomatically = true

        webview4.webViewClient = WebViewClient()
    }

    override fun onPause() {
        flage = false
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null) return
                uploadMessage!!.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        resultCode,
                        intent
                    )
                )
                uploadMessage = null
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            val result =
                if (intent == null || resultCode != RESULT_OK) null else intent.data
            mUploadMessage!!.onReceiveValue(result)
            mUploadMessage = null
        } else{
            Toast.makeText(applicationContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
        }
    }


}