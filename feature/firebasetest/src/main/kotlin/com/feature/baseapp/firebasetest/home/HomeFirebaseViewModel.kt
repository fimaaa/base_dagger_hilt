package com.feature.baseapp.firebasetest.home

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.basedagger.common.base.BaseViewModel
import com.basedagger.common.provider.LocationProvider
import com.fonfon.kgeohash.GeoHash
import com.fonfon.kgeohash.toGeoHash
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFirebaseViewModel @Inject constructor(private val locationProvider: LocationProvider) :
    BaseViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val database =
        FirebaseDatabase.getInstance("https://basedaggerhilt-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val userReference = database.getReference("hero_firebase_development")
    private val authStateListener = AuthStateListener { firebaseAuth ->
        viewModelScope.launch(Dispatchers.Main) {
            if (firebaseAuth.currentUser == null) {
                navigate(HomeFirebaseFragmentDirections.actionHomeFirebaseFragmentToLoginFirebaseFragment())
            }
            _actionUser.emit(firebaseAuth.currentUser)
        }
    }

    private val _actionUser = MutableSharedFlow<FirebaseUser?>(replay = 1)
    val actionUser: SharedFlow<FirebaseUser?>
        get() = _actionUser

    private val _tokenFirebase = MutableSharedFlow<String>(replay = 1)
    val tokenFirebase: SharedFlow<String?>
        get() = _tokenFirebase

    init {
        auth.addAuthStateListener(authStateListener)
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("TAG DATACHANGED = $snapshot")
            }

            override fun onCancelled(error: DatabaseError) {
                println("TAG ERROR = $error")
            }
        })
    }

    fun saveUserToDatabase(user: FirebaseUser) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            viewModelScope.launch {
                _tokenFirebase.emit(token)
                val location = Location("geohash")
//                location.latitude = 53.2030476
//                location.longitude = 45.0324948

                locationProvider.getLocation()?.let {
                    location.latitude = it.latitude
                    location.longitude = it.longitude
                }

                userReference.child("heroes").child(user.uid)
                    .setValue(mapOf<String, Any>("company" to "paxel",
                        "firebase_token" to token,
                        "latlong" to GeoHash(location).toString(),
                        "hero_geohash4" to location.toGeoHash(4).toString(),
                        "hero_geohash5" to location.toGeoHash(5).toString(),
                        "hero_geohash6" to location.toGeoHash(6).toString(),
                        "hero_geohash7" to location.toGeoHash(7).toString(),
                        "hero_id" to user.uid,
                        "hero_name" to (user.displayName?.ifEmpty { user.email?.ifEmpty { "-" } }
                            ?: "-"),
                        "hero_position" to mapOf(
                            "latitude" to location.latitude, "longitude" to location.longitude
                        ),
                        "service" to "instant-multidrop",
                        "timestamp" to (System.currentTimeMillis() / 1000).toString()))
                    .addOnFailureListener {
                        println("TAG DATABASE ERROR $it")
                    }.addOnSuccessListener {
                        println("TAG DATABASE SUCCESS")
                    }
            }
//                        database.child("users").child(user.uid).setValue(mapOf(
//                            "user" to user,
//                            "token" to token
//                        ))

//            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    fun signOut() {
        auth.signOut()
    }
}