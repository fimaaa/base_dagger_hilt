package com.feature.baseapp.firebasetest.login

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.data.common.ViewState
import com.general.common.base.BaseViewModel
import com.general.common.extension.toViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginFirebaseViewModel : BaseViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _actionUser = MutableSharedFlow<FirebaseUser>(replay = 1)
    val actionUser: SharedFlow<FirebaseUser>
        get() = _actionUser

    fun checkUser() {
        viewModelScope.launch(Dispatchers.IO) {
            auth.currentUser?.let {
                navigateToHome()
            }
        }
    }

    fun login(mActivity: Activity) {
        setStatusViewModel(ViewState.LOADING)
        auth.signInWithEmailAndPassword("email@testmail.com", "password")
            .addOnSuccessListener(mActivity) {
                val user = auth.currentUser
                user?.let {
                    navigateToHome()
                }
                setStatusViewModel(ViewState.SUCCESS(true))
                // Sign in success, update UI with the signed-in user's informatio
            }
            .addOnFailureListener(mActivity) {
                it.printStackTrace()
                register(mActivity)
            }
    }

    private fun register(mActivity: Activity) {
        setStatusViewModel(ViewState.LOADING)
        auth.createUserWithEmailAndPassword("email@testmail.com", "password")
            .addOnCompleteListener(mActivity) { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        user?.let {
                            _actionUser.emit(it)
                            setStatusViewModel(ViewState.SUCCESS(true))
                            return@launch
                        }
                    }
                    throw Exception("Error Login Firebase")
                }
            }
            .addOnFailureListener(mActivity) {
                setStatusViewModel(it.toViewState())
            }
    }

    private fun navigateToHome() {
        navigate(LoginFirebaseFragmentDirections.actionLoginFirebaseFragmentToHomeFirebaseFragment())
    }
}