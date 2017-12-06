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
class ELearningFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.title = resources.getString(R.string.lms)
        return inflater.inflate(R.layout.fragment_e_learning, container, false)
    }

}
