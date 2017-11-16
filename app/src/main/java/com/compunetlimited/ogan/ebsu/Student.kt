package com.compunetlimited.ogan.ebsu

/**
 * Created by belema on 11/15/17.
 */

data class Student (
        private val Email: String,
        private val Password: String
    ){

    constructor(email: String,
                password: String,
                FirstName: String,
                LastName: String,
                Department: String,
                StudentId: String,
                ConfirmPassword: String) : this(email, password)
}
