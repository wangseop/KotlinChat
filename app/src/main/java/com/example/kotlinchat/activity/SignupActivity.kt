package com.example.kotlinchat.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import com.example.kotlinchat.R
import com.example.kotlinchat.data.user.LoginData
import com.example.kotlinchat.data.user.SignupData
import com.example.kotlinchat.network.NetworkLoginTask
import com.example.kotlinchat.network.NetworkSignupTask

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var editID: EditText
    private lateinit var editNickname: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPasswordConfirm: EditText
    private lateinit var signupBtn: Button
    private lateinit var cancelBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)       //        // 액션바 비활성화
//        actionbar = getSupportActionBar();
//        actionbar.hide();
        editID = findViewById(R.id.id_input_signup)
        editNickname = findViewById(R.id.nickname_input_signup)
        editPassword = findViewById(R.id.password_input_signup)
        editPasswordConfirm = findViewById(R.id.pw_confirm_input_signup)
        signupBtn = findViewById(R.id.signup_ok_btn)
        cancelBtn = findViewById(R.id.signup_cancel_btn)

        findViewById<View>(R.id.login_thumbnail).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fadein)
        )
        signupBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
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
        Log.d("onPause()", " SignupActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop()", " SignupActivity")
        //        finish();
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy()", " SignupActivity")
    }

    override fun onClick(v: View) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        v.startAnimation(animation)
        when (v.id) {
            R.id.signup_ok_btn -> {
                // HttpUrlRequest에 담을 값
                val values = ContentValues()
                val id = editID.text.toString()
                val nick = editNickname.text.toString()
                val password = editPassword.text.toString()
                val passwordConfirm = editPasswordConfirm.text.toString()

                if (password.equals(passwordConfirm)){
                    values.put("id", id)
                    values.put("nick", nick)
                    values.put("password", password)
//                val task = NetworkLoginTask(this, loginUrl, values, SharedPreference(this, "login_shared", Context.MODE_PRIVATE), LoginData(id, password))
                    val task = NetworkSignupTask(this, signupUrl, values, SignupData(id, nick, password))

                    task.execute()
                }

            }
            R.id.signup_cancel_btn -> {
                this.finish()
            }
        }
    }

    companion object {
        //    private ActionBar actionbar;
        private const val signupUrl = "http://3.15.44.44:5000/signup"
    }
}
