package com.compunetlimited.ogan.ebsu.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.compunetlimited.ogan.ebsu.R


/**
 * A simple [Fragment] subclass.
 */
class CourseRegistrationFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.title = resources.getString(R.string.course_reg)
        return inflater.inflate(R.layout.fragment_course_registration, container, false)
    }

}// Required empty public constructor
