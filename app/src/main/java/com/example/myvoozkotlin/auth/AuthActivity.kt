package com.example.myvoozkotlin.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.databinding.FragmentAuthBinding
import com.example.myvoozkotlin.helpers.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity: AppCompatActivity() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val scope = listOf(VKScope.PAGES, VKScope.EMAIL, VKScope.OFFLINE)

    companion object {
        private const val RC_SIGN_IN = 1
        fun newInstance(): AuthActivity {
            return AuthActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureViews()
        setListeners()
        initObservers()
    }

    private fun configureViews() {
        initToolbar()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun setListeners(){
        binding.cvVkAuthButton.setOnClickListener {
            //signIn()
            VK.login(this, scope)
        }
    }

    private fun initObservers() {
        observeOnNewsResponse()
    }

    private fun observeOnNewsResponse() {
        authViewModel.authVkResponse.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        finish()
                    }
                }
                Status.ERROR -> {
                    UtilsUI.makeToast("error auth")
                }
            }
        })
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    UtilsUI.makeToast(getString(R.string.toast_repeat_auth))
                }
            } else {
                UtilsUI.makeToast(getString(R.string.toast_repeat_auth))
            }
        }
        else{
            val callback = object : VKAuthCallback {
                override fun onLogin(token: VKAccessToken) {
                    authViewModel.authVk(token.accessToken
                        , token.userId, BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)
                        , BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0), "s")
                    //todo add notification accessToken
                }

                override fun onLoginFailed(errorCode: Int) {
                    UtilsUI.makeToast(getString(R.string.toast_repeat_auth))
                }
            }
            if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun initToolbar() {
        addBackButton()
    }

    private fun addBackButton(){
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                } else {
                    UtilsUI.makeToast(getString(R.string.toast_repeat_auth))
                }
            }
    }
}