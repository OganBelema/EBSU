package com.compunetlimited.ogan.ebsu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.compunetlimited.ogan.ebsu.fragments.CourseRegistrationFragment
import com.compunetlimited.ogan.ebsu.fragments.DashboardFragment
import com.compunetlimited.ogan.ebsu.fragments.ELearningFragment
import com.compunetlimited.ogan.ebsu.fragments.HomeFragment
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private var paymentStatus: Boolean? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //check if user is logged in
        val sessionManager = UserSessionManager(applicationContext)
        if(sessionManager.checkLogin())
            finish()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // get user data from session
        val user = UserSessionManager.userDetails

        // get userId
        userId = user[UserSessionManager.KEY_USER_ID]

        val navView = nav_view.getHeaderView(0)
        val headerText = navView.findViewById<TextView>(R.id.nav_header_text)
        headerText.text = userId
        /*val headerImage = navView.findViewById<CircleImageView>(R.id.imageView)
        Picasso.with(applicationContext).load(resources.getString(R.string.image_url, userId))
                .into(headerImage) */

    }

    override fun onStart() {
        super.onStart()
        displaySelectedMenu(R.id.nav_home)
        if (MyUtilClass.checkNetworkConnection(applicationContext))
        //display menu if student has paid fee
        showMenu()
        else MyUtilClass.showNoInternetMessage(drawer_layout.context)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_signOut) {
            // Clear the User session data
            // and redirect user to LoginActivity
            UserSessionManager(applicationContext).logoutUser()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        displaySelectedMenu(id)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedMenu(id: Int){
        var fragment: Fragment? = null
        when (id) {
            R.id.nav_home -> fragment = HomeFragment()
            R.id.nav_dashboard -> fragment = DashboardFragment()
            R.id.nav_course_reg -> fragment = CourseRegistrationFragment()
            R.id.nav_lms -> fragment = ELearningFragment()
            R.id.nav_payfee -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(resources.getString(R.string.fee_payment_url))
                startActivity(intent)
            }
            R.id.nav_accommodation -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(resources.getString(R.string.accommodation_payment_url))
                startActivity(intent)
            }
        }
        fragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit()
        }
    }

    private fun checkPaymentStatus(): Boolean {
        ServiceGenerator.apiMethods.checkPayment(userId)
                .enqueue(object : Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (response.isSuccessful) {
                            paymentStatus = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                                .show()
                    }
                })
        return paymentStatus ?: false
    }


    private fun showMenu() {
        if (checkPaymentStatus()) {
            nav_view.menu.findItem(R.id.nav_dashboard).isVisible = true
            nav_view.menu.findItem(R.id.nav_course_reg).isVisible = true
            nav_view.menu.findItem(R.id.nav_accommodation).isVisible = true
            nav_view.menu.findItem(R.id.nav_result).isVisible = true
            nav_view.menu.findItem(R.id.nav_lms).isVisible = true
        }
    }

}
