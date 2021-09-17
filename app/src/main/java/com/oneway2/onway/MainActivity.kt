package com.oneway2.onway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var flage:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splash_screen.alpha=0f
        splash_screen.animate().setDuration(4000).alpha(40f).withEndAction {
            if(flage){


                val intent= Intent(this,MainActivity2::class.java)
                startActivity(intent)


                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }

            flage=true



        }




    }

    override fun onResume() {
        flage=true

        splash_screen.alpha=0f
        splash_screen.animate().setDuration(4000).alpha(40f).withEndAction {
            if(flage){


                val intent= Intent(this,MainActivity2::class.java)
                startActivity(intent)


                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }


        }

        super.onResume()
    }

    override fun onPause() {
        flage=false
        super.onPause()
    }

}