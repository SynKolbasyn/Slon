package com.kozminandruxacorp.slon2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

const val GEO_LOCATION_REQUEST_COD_SUCCESS = 1
const val TAG = "GPS_TEST"

var lat = "0"
var lon = "0"

class MainActivity : AppCompatActivity() {

    // ------------------------- GPS -------------------------

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    // ------------------------- GPS -------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        initViews()
        geoService.requestLocationUpdates(locationRequest, geoCallback, null)
    }

    private fun initViews(){
        main_latitude_tv.text = "Latitude"
        main_longitude_tv.text = "Longitude"
        main_latitude_val.text = lat
        main_longitude_val.text = lon
    }

    // ------------------------- GPS -------------------------

    private fun initLocationRequest() : LocationRequest {
        val request = LocationRequest.create()
        return request.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            Log.d(TAG, "onLocationResult: ${geo.locations.size}")
            for (location in geo.locations){
                mLocation = location
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                initViews()
                Log.d(TAG, "onLocationResult: lat: ${location.latitude} ; lon: ${location.longitude}")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionResult: $requestCode")
        // Тут будет запуск MainActivity.kt
    }

    private fun checkPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
           ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Нам нужны гео данные")
                .setMessage("Пожалуйста, разрешите доступ к геоданным для продолжения работы приложения")
                .setPositiveButton("ОК"){ _,_ ->
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GEO_LOCATION_REQUEST_COD_SUCCESS)
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), GEO_LOCATION_REQUEST_COD_SUCCESS)}
                .setNegativeButton("НЕТ") { dialog,_ -> dialog.dismiss()}
                .create()
                .show()
        }
    }

    // ------------------------- GPS -------------------------
}