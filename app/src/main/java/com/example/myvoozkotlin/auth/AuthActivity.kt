package com.example.myvoozkotlin.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myvoozkotlin.BaseApp
import com.example.myvoozkotlin.R
import com.example.myvoozkotlin.auth.viewModels.AuthViewModel
import com.example.myvoozkotlin.databinding.FragmentAuthBinding
import com.example.myvoozkotlin.helpers.*
import com.example.myvoozkotlin.helpers.forView.NewDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import com.yandex.authsdk.YandexAuthToken
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AuthActivity: AppCompatActivity() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private var dialog: NewDialog? = null
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val scope2: Set<String> = HashSet()
    private var yandexSdk:YandexAuthSdk? = null

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
        yandexSdk = YandexAuthSdk( YandexAuthOptions(this, true))

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

        binding.cvYaAuthButton.setOnClickListener {
            val sdk = YandexAuthSdk(this@AuthActivity, YandexAuthOptions(this@AuthActivity, true))
            startActivityForResult(sdk.createLoginIntent(this@AuthActivity, scope2), 6)
        }
    }

    private var yandexResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

    }

    private fun initObservers() {
        observeOnVkResponse()
        observeOnYandexResponse()
    }

    private fun observeOnVkResponse() {
        authViewModel.authVkResponse.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showWait(true)
                }
                Status.SUCCESS -> {

                    if (it.data == null) {

                    } else {
                        showWait(false)
                        finish()
                    }
                }
                Status.ERROR -> {
                    UtilsUI.makeToast("error auth")
                }
            }
        })
    }

    fun showWait(isShow: Boolean){
        if(dialog == null){
            dialog = NewDialog(R.layout.dialog_fragment_loading)
        }
        dialog?.let {
            it.isCancelable = false
        }
        if(isShow){
            if(!dialog!!.isAdded){
                dialog!!.show(supportFragmentManager, null)
            }
        }
        else{
            if(dialog!!.isAdded){
                dialog!!.dismiss()
            }
        }
    }

    private fun observeOnYandexResponse() {
        authViewModel.authYaResponse.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showWait(true)
                }
                Status.SUCCESS -> {
                    Log.d("bermlrbemerb", "bvwebwbwbwb")
                    if (it.data == null) {

                    } else {
                        showWait(false)
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
        else if(requestCode == 6){
            if (resultCode == Activity.RESULT_OK) {
                println("kbenlkrbnlkerbnlkerbnlkernblkernblk")
                val yandexAuthToken: YandexAuthToken = yandexSdk?.extractToken(resultCode, data)!!
                if(yandexAuthToken != null){
                    authViewModel.authYa(yandexAuthToken.value, BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)
                        , BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0),
                        FirebaseInstanceId.getInstance().token!!)
                }
            }
        }
        else{
            val callback = object : VKAuthCallback {
                override fun onLogin(token: VKAccessToken) {
                    authViewModel.authVk(token.accessToken
                        , token.userId, BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_UNIVERSITY_ID, 0)
                        , BaseApp.getSharedPref().getInt(Constants.APP_PREFERENCES_USER_GROUP_ID, 0),
                        FirebaseInstanceId.getInstance().token!!
                    )
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