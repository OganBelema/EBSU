package com.compunetlimited.ogan.ebsu.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.compunetlimited.ogan.ebsu.Dashboard
import com.compunetlimited.ogan.ebsu.R
import com.compunetlimited.ogan.ebsu.ServiceGenerator
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.progress_bar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private var userId: String? = null
    private lateinit var call: Call<Dashboard>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        activity?.title = resources.getString(R.string.dashboard)

        // get user data from session
        val user = UserSessionManager.userDetails

        // get userId
        userId = user[UserSessionManager.KEY_USER_ID]

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onStart() {
        super.onStart()
        loadDashboard()
    }



    private fun loadDashboard() {
        call = ServiceGenerator.apiMethods.getDashboard(userId)
        call.enqueue(object : Callback<Dashboard> {

            override fun onResponse(call: Call<Dashboard>, response: Response<Dashboard>?) {

                hideProgressBar()

                if (response != null) {
                    if (response.isSuccessful) {
                        activity?.let {
                            cd_dashboard_details.visibility = View.VISIBLE //display the dashboard
                            val dashboard = response.body()
                            if (dashboard != null) {
                                tv_dashboard_studentId.text = resources.getString(R.string.userId, dashboard.studentId)
                                tv_dashboard_fullName?.text = resources.getString(R.string.fullName, dashboard.fullName)
                                tv_dashboard_level?.text = resources.getString(R.string.levelName, dashboard.levelName)
                                tv_dashboard_programmeName.text = resources.getString(R.string.programmeName, dashboard.programmeName)
                                tv_dashboard_departmentName.text = resources.getString(R.string.departmentName, dashboard.departmentName)
                                tv_dashboard_semesterName.text = resources.getString(R.string.semesterName, dashboard.semesterName)
                                tv_dashboard_sessionName.text = resources.getString(R.string.sessionName, dashboard.sessionName)
                                tv_dashboard_facultyName.text = resources.getString(R.string.facultyName, dashboard.facultyName)
                                tv_dashboard_noOfRegisteredCourses.text = resources.getString(R.string.registeredCourses, dashboard.noOfRegCourses)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Dashboard>, t: Throwable?) {
                hideProgressBar()
                if (t != null)
                    activity?.let {
                        Toast.makeText(activity?.applicationContext,
                                t.message, Toast.LENGTH_LONG).show()
                    }
            }
        })

    }

    private fun hideProgressBar() {

        progress_bar?.let { progress_bar.visibility = View.GONE }

    }

    override fun onStop() {
        super.onStop()
        call.cancel()
    }

}
