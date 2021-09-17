package com.oneway2.onway

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.activity_main3.*
import java.lang.reflect.Field

class imageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        var actionBar = getSupportActionBar()

        if (actionBar != null) {

            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        navigation_view6.setNavigationItemSelectedListener{
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
            drawer_layout6,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout6.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer_layout6.isDrawerOpen(GravityCompat.START)) {
            drawer_layout6.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout6.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}