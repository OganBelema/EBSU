package com.compunetlimited.ogan.ebsu.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.compunetlimited.ogan.ebsu.Dashboard
import com.compunetlimited.ogan.ebsu.R
import com.compunetlimited.ogan.ebsu.ServiceGenerator
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    internal lateinit var view: View
    private lateinit var dashboardView: LinearLayout
    private  var userId: String? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity.title = resources.getString(R.string.dashboard)
        // get user data from session
        val user = UserSessionManager.userDetails

        // get userId
        userId = user[UserSessionManager.KEY_USER_ID]
        view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)
        progressBar = view.findViewById(R.id.home_progress_bar)
        dashboardView = view.findViewById(R.id.cd_dashboard_details)

        loadDashboard()
        return view
    }

    private fun loadDashboard() {
        val dashboardCall = ServiceGenerator.apiMethods.getDashboard(userId)
        dashboardCall.enqueue(object : Callback<Dashboard> {

            override fun onResponse(call: Call<Dashboard>, response: Response<Dashboard>?) {

                progressBar.visibility = View.GONE

                if (response != null) {
                    if (response.isSuccessful) {
                        dashboardView.visibility = View.VISIBLE
                        val dashboard = response.body()
                        val studentIdTextView = view.findViewById<TextView>(R.id.tv_dashboard_studentId)
                        val fullNameTextView = view.findViewById<TextView>(R.id.tv_dashboard_fullName)
                        val levelTextView = view.findViewById<TextView>(R.id.tv_dashboard_level)
                        val programmeTextView = view.findViewById<TextView>(R.id.tv_dashboard_programmeName)
                        val departmentTextView = view.findViewById<TextView>(R.id.tv_dashboard_departmentName)
                        val semesterTextView = view.findViewById<TextView>(R.id.tv_dashboard_semesterName)
                        val sessionTextView = view.findViewById<TextView>(R.id.tv_dashboard_sessionName)
                        val facultyTextView = view.findViewById<TextView>(R.id.tv_dashboard_facultyName)
                        val registeredTextView = view.findViewById<TextView>(R.id.tv_dashboard_noOfRegisteredCourses)

                        if (dashboard != null) {
                            studentIdTextView.text = resources.getString(R.string.userId, dashboard.studentId)
                            fullNameTextView.text = resources.getString(R.string.fullName, dashboard.fullName)
                            levelTextView.text = resources.getString(R.string.levelName, dashboard.levelName)
                            programmeTextView.text = resources.getString(R.string.programmeName, dashboard.programmeName)
                            departmentTextView.text = resources.getString(R.string.departmentName, dashboard.departmentName)
                            semesterTextView.text = resources.getString(R.string.semesterName, dashboard.semesterName)
                            sessionTextView.text = resources.getString(R.string.sessionName, dashboard.sessionName)
                            facultyTextView.text = resources.getString(R.string.facultyName, dashboard.facultyName)
                            registeredTextView.text = resources.getString(R.string.registeredCourses, dashboard.noOfRegCourses)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Dashboard>, t: Throwable?) {

                progressBar.visibility = View.GONE
                if (t != null) {
                }
            }
        })

    }

}// Required empty public constructor
