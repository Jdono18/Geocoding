package com.example.geocoding

import android.content.Intent  // imports the following
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

private const val TAG = "GEOCODE_PLACE_ACTIVITY"  // TAG for logCat
class MainActivity : AppCompatActivity() {

    private lateinit var placeNameInput: EditText  // initializes the listed variables and their types
    private lateinit var mapButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeNameInput = findViewById(R.id.place_name_input)  // ties listed variables to listed resource IDs
        mapButton = findViewById(R.id.map_button)

        mapButton.setOnClickListener {  // sets OnClickListener to mapButton
            val placeName = placeNameInput.text.toString()  // initializes placeName variable that converts user placeNameInput to a string value
            if (placeName.isBlank()) {  // if statement placeName variable value isBlank
                Toast.makeText(this, getString(R.string.no_place_entered_error), Toast.LENGTH_LONG).show()  // prints error Toast message
            } else {
                Log.d(TAG, "About to geocode $placeName")  // prints logCat TAG and listed message
                showMapForPlace(placeName)  // calls showMapForPlace function with placeName parameter

            }
        }
    }

    private fun showMapForPlace(placeName: String) {  // defines showMapForPlace function with placeName: String parameter
        // TODO geocode place name to get list of locations
        val geocoder = Geocoder(this)  // initializes geocoder variable that uses geocoder library

        try {  // error handling
            val addresses = geocoder.getFromLocationName(placeName, 1)  // initializes addresses variable that is a mutableList data type as geocoder library call with 2 listed parameters (placeName variable, and max results)

            // TODO use an intent to launch map, for first location if location is found
            if (addresses?.isNotEmpty() == true) {  // if addresses variable is not empty then
                val address = addresses.first()  // initializes address constant value that returns the first element from addresses mutableList
                Log.d(TAG, "First address is $address")  // logCat TAG
                val geoUriString = "geo:${address?.latitude},${address?.longitude}" // "geo:45,-90"  // initializes geoUriString value that ties latitude and longitude values from user location input
                Log.d(TAG, "Using geo uri $geoUriString")  // logCat TAG
                val geoUri = Uri.parse(geoUriString)  // initializes geoUri variable the parses geoUriString value
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)  // initializes mapIntent value that calls an intent to display an activity to the user
                Log.d(TAG, "Launching map activity")  // logCat TAG
                startActivity(mapIntent)  // calls startActivity function with mapIntent parameter

            } else {
                Log.d(TAG, "No places found for string $placeName")  // logCat TAG
                Toast.makeText(  // calls Toast popup with listed message
                    this,
                    getString(R.string.no_places_found_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch ( e: IOException) {  // error handling
            Log.e(TAG, "Unable to geocode place $placeName", e)  // LogCat TAG
            Toast.makeText(this, "Sorry, unable to geocode.  Are you online?", Toast.LENGTH_LONG).show()  // Toast message with error response 
        }
    }
}