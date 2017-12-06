package com.compunetlimited.ogan.ebsu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_check_id.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckIdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_id)

    }

    override fun onStart() {
        super.onStart()

        checkID_btn.setOnClickListener {
            val studentId = et_student_id.text.toString()

            if (studentId.isEmpty()) {
                Toast.makeText(applicationContext, "Enter your student ID", Toast.LENGTH_LONG)
                        .show()
            } else {
                loading()
                checkStudentId(studentId)
                /*intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)*/
            }

        }
    }

    private fun checkStudentId(studentId: String){
        ServiceGenerator.apiMethods.checkStudentId(studentId)
                .enqueue(object : Callback<Student> {
                    override fun onResponse(call: Call<Student>, response: Response<Student>?) {
                        doneLoading()
                        if (response != null) {
                            if (response.isSuccessful) {
                                val userID = response.body()?.StudentId
                                val firstName = response.body()?.FirstName
                                val lastName = response.body()?.LastName
                                val department = response.body()?.Department
                                intent = Intent(applicationContext, RegisterActivity::class.java)
                                intent.putExtra("userID", userID)
                                intent.putExtra("firstName", firstName)
                                intent.putExtra("lastName", lastName)
                                intent.putExtra("department", department)
                                startActivity(intent)
                            } else {
                                MyUtilClass.showErrorMessage(checkId_container.context, response)
                            }
                        }
                    }

                    override fun onFailure(call: Call<Student>, t: Throwable?) {
                        doneLoading()
                        Toast.makeText(applicationContext, t?.message, Toast.LENGTH_LONG)
                                .show()
                    }
                })
    }

    private fun loading(){
        checkId_progress_bar.visibility = View.VISIBLE
        checkId_container.visibility = View.GONE
    }

    private fun doneLoading(){
        checkId_progress_bar.visibility = View.GONE
        checkId_container.visibility = View.VISIBLE
    }
}
