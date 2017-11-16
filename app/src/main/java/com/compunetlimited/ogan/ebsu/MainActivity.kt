package com.compunetlimited.ogan.ebsu

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
import com.compunetlimited.ogan.ebsu.fragments.CourseRegistrationFragment
import com.compunetlimited.ogan.ebsu.fragments.DashboardFragment
import com.compunetlimited.ogan.ebsu.fragments.ELearningFragment
import com.compunetlimited.ogan.ebsu.fragments.HomeFragment
import com.compunetlimited.ogan.ebsu.sessionManagement.UserSessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.content, HomeFragment()).commit()
        nav_view.setCheckedItem(R.id.nav_home)

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
        var fragment: Fragment? = null
        when (id) {
            R.id.nav_home -> fragment = HomeFragment()
            R.id.nav_dashboard -> fragment = DashboardFragment()
            R.id.nav_course_reg -> fragment = CourseRegistrationFragment()
            R.id.nav_lms -> fragment = ELearningFragment()
        }
        fragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
