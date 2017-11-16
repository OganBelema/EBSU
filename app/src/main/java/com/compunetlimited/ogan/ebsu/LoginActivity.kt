package com.compunetlimited.ogan.ebsu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    internal lateinit var intent: Intent
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var session: UserSessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = resources.getString(R.string.login)
        // User Session Manager
        session = UserSessionManager(applicationContext)

        val loginButton = findViewById<Button>(R.id.btn_login)
        loginButton.setOnClickListener {
            //validateInput();
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.btn_register)
        registerButton.setOnClickListener {
            intent = Intent(applicationContext, CheckIdActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        showProgressBar()
        val login = ServiceGenerator.apiMethods.login(Student(email, password))
        login.enqueue(object : Callback<UserId> {
            override fun onResponse(call: Call<UserId>, response: Response<UserId>?) {

                hideProgressBar()

                if (response != null) {
                    if (response.isSuccessful) {
                        session.createUserLoginSession(email, password)
                        finish()
                        //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        //startActivity(intent);
                    }
                    println(response.message())
                    println(response.code())
                }


            }

            override fun onFailure(call: Call<UserId>, t: Throwable?) {
                hideProgressBar()
                if (t != null) {
                    println(t.message)
                }

            }
        })
    }

    private fun validateInput() {

        // Reset errors.
        et_username.error = null
        et_password.error = null

        // Store values at the time of the login attempt.
        email = et_username.text.toString()
        password = et_password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            et_password.error = getString(R.string.error_field_required)
            focusView = et_password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_username.error = getString(R.string.error_field_required)
            focusView = et_username
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            login()
        }
    }

    private fun showProgressBar() {
        login_view.visibility = View.GONE
        pb_login.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_login.visibility = View.GONE
        login_view.visibility = View.VISIBLE
    }

}
