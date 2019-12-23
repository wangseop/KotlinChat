package com.example.kotlinchat.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R
import com.example.kotlinchat.data.user.LoginData
import com.example.kotlinchat.network.NetworkLoginTask

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editID: EditText
    private lateinit var editPassword: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button
//    private lateinitval pref: SharedPreference
    private lateinit var autoLogin: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
//        val intent = intent
//        val logState = intent.getBooleanExtra("logout", false)
        // SharedPreference 생성
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //        // 액션바 비활성화
//        actionbar = getSupportActionBar();
//        actionbar.hide();
        editID = findViewById(R.id.id_input_login)
        editPassword = findViewById(R.id.password_input_login)
        loginBtn = findViewById(R.id.login_btn)
        signupBtn = findViewById(R.id.signup_btn)
        autoLogin = findViewById(R.id.auto_login_check)
        findViewById<View>(R.id.login_thumbnail).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fadein)
        )
        loginBtn.setOnClickListener(this)
        signupBtn.setOnClickListener(this)
//        autoLogin.setChecked(logState)
        // SharedPreference 호출
//        if(pref.getBoolean("state", false)){
//            Log.d("State True", "Log Confirm");
//            ContentValues values = new ContentValues();
//            values.put("id", pref.getString("id", null));
//            values.put("password", pref.getString("password", null));
//
//            NetworkLoginTask task = new NetworkLoginTask(loginUrl, values);
//            task.execute();
//        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause()", " LoginActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop()", " LoginActivity")
        //        finish();
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy()", " LoginActivity")
    }

    override fun onClick(v: View) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        v.startAnimation(animation)
        when (v.id) {
            R.id.login_btn -> {
                // HttpUrlRequest에 담을 값
                val values = ContentValues()
                val id = editID.text.toString()
                val password = editPassword.text.toString()
//                val autoChecked = autoLogin.isChecked
                values.put("id", id)
                values.put("password", password)
//                val task = NetworkLoginTask(this, loginUrl, values, SharedPreference(this, "login_shared", Context.MODE_PRIVATE), LoginData(id, password))
                val task = NetworkLoginTask(this, loginUrl, values, LoginData(id, password))

                task.execute()
            }
            R.id.signup_btn -> {
//                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
//                startActivity(intent)
            }
        }
    }

    companion object {
        //    private ActionBar actionbar;
        private const val loginUrl = "http://3.16.169.18:5000/login"
    }
}