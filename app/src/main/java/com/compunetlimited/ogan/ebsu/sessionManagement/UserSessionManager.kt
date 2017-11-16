package com.compunetlimited.ogan.ebsu.sessionManagement

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.compunetlimited.ogan.ebsu.LoginActivity
import java.util.*

class UserSessionManager(private val _context: Context) {

    // Editor reference for Shared preferences
    private val editor: Editor

    // Shared pref mode
    private val PRIVATE_MODE = 0


    // Check for login
    val isUserLoggedIn: Boolean
        get() = pref.getBoolean(IS_USER_LOGIN, false)

    init {
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    //Create login session
    fun createUserLoginSession(email: String, password: String) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true)

        // Storing name in pref
        editor.putString(KEY_USER_ID, email)

        // Storing email in pref
        editor.putString(KEY_PASSWORD, password)

        // commit changes
        editor.commit()
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    fun checkLogin(): Boolean {
        // Check login status
        if (!this.isUserLoggedIn) {

            // user is not logged in redirect him to Login Activity
            val i = Intent(_context, LoginActivity::class.java)

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            // Add new Flag to start new Activity
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Staring Login Activity
            _context.startActivity(i)

            return true
        }
        return false
    }

    /**
     * Clear session details
     */
    fun logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear()
        editor.commit()

        // After logout redirect user to Login Activity
        val i = Intent(_context, LoginActivity::class.java)

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // Staring Login Activity
        _context.startActivity(i)
    }

    companion object {

        // Shared Preferences reference
        private lateinit var pref: SharedPreferences

        // Sharedpref file name
        private val PREFER_NAME = "AndroidExamplePref"

        // All Shared Preferences Keys
        private val IS_USER_LOGIN = "IsUserLoggedIn"

        // User name (make variable public to access from outside)
        val KEY_USER_ID = "name"

        // Email address (make variable public to access from outside)
        val KEY_PASSWORD = "email"

        /**
         * Get stored session data
         */
        //Use hashmap to store user credentials
        // user email
        // user password
        // return user
        val userDetails: HashMap<String, String>
            get() {
                val user = HashMap<String, String>()
                user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null))
                user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null))
                return user
            }
    }
}
