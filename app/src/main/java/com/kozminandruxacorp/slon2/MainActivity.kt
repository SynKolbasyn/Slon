package com.kozminandruxacorp.slon2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews(){
        main_latitude_tv.text = "Latitude"
        main_longitude_tv.text = "Longitude"
        main_latitude_val.text = "0"
        main_longitude_val.text = "0"
    }
}