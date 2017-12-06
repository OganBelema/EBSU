package com.compunetlimited.ogan.ebsu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userId = intent.getStringExtra("userID") ?: ""
        reg_userId_tv.text = resources.getString(R.string.userId, userId)
        val firstName = intent.getStringExtra("firstName") ?: ""
        val lastName = intent.getStringExtra("lastName")?: ""
        reg_fullName_tv.text = resources.getString(R.string.register_full_name, firstName ,lastName)
        val department = intent.getStringExtra("department") ?: ""
        reg_department_tv.text = resources.getString(R.string.departmentName, department)

        register_btn.setOnClickListener { validateInput(userId, firstName, lastName, department) }
    }

    private fun validateInput(userId: String, firstName: String, lastName: String,
                              department: String) {

        // Reset errors.
        et_reg_email.error = null
        et_reg_password.error = null
        et_reg_confirm_password.error = null

        // Store values at the time of the login attempt.
        val email = et_reg_email.text.toString()
        val password = et_reg_password.text.toString()
        val confirmPassword = et_reg_confirm_password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            et_reg_password.error = getString(R.string.error_field_required)
            focusView = et_reg_password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_reg_email.error = getString(R.string.error_field_required)
            focusView = et_reg_email
            cancel = true
        }

        if (confirmPassword != password){
            et_reg_confirm_password.error = getString(R.string.error_field_required)
            focusView = et_reg_confirm_password
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            loading()
            val student = Student(email,password, firstName, lastName, department, userId, confirmPassword)
            registerStudent(student)
        }
    }

    private fun registerStudent(student: Student){
        ServiceGenerator.apiMethods.registerStudent(student).enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                doneLoading()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                doneLoading()
            }
        })
    }

    private fun loading(){
        register_view.visibility = View.GONE
        register_pb.visibility = View.VISIBLE
    }

    private fun doneLoading(){
        register_pb.visibility = View.GONE
        register_view.visibility = View.VISIBLE
    }
}
