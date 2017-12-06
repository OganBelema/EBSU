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
class AssignmentFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.title = resources.getString(R.string.assignment)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

}
