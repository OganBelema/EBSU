package com.compunetlimited.ogan.ebsu.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.compunetlimited.ogan.ebsu.Dashboard
import com.compunetlimited.ogan.ebsu.R
import com.compunetlimited.ogan.ebsu.ServiceGenerator
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private  var userId: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.title = resources.getString(R.string.dashboard)
        // get user data from session
        val user = UserSessionManager.userDetails

        // get userId
        userId = user[UserSessionManager.KEY_USER_ID]

        loadDashboard()
        return inflater?.inflate(R.layout.fragment_dashboard, container, false)
    }

    private fun loadDashboard() {
        val dashboardCall = ServiceGenerator.apiMethods.getDashboard(userId)
        dashboardCall.enqueue(object : Callback<Dashboard> {

            override fun onResponse(call: Call<Dashboard>, response: Response<Dashboard>?) {

                home_progress_bar.visibility = View.GONE

                if (response != null) {
                    if (response.isSuccessful) {
                        cd_dashboard_details.visibility = View.VISIBLE
                        val dashboard = response.body()
                        if (dashboard != null) {
                            tv_dashboard_studentId.text = resources.getString(R.string.userId, dashboard.studentId)
                            tv_dashboard_fullName.text = resources.getString(R.string.fullName, dashboard.fullName)
                            tv_dashboard_level.text = resources.getString(R.string.levelName, dashboard.levelName)
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

            override fun onFailure(call: Call<Dashboard>, t: Throwable?) {

                home_progress_bar.visibility = View.GONE
                if (t != null) {
                }
            }
        })

    }

}
