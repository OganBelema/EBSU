package com.compunetlimited.ogan.ebsu.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.compunetlimited.ogan.ebsu.R
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var fm: FragmentManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.title = resources.getString(R.string.home)
        fm = activity?.supportFragmentManager
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timetable_btn.setOnClickListener {
            buttonClicked(R.id.timetable_btn)
        }
        assignment_btn.setOnClickListener {
            buttonClicked(R.id.assignment_btn)
        }
    }

    private fun buttonClicked(id: Int){
        var fragment: Fragment? = null
        when(id){
            R.id.assignment_btn -> fragment = AssignmentFragment()
            R.id.timetable_btn -> fragment = TimetableFragment()
        }

        if (fragment != null) {
            fm?.beginTransaction()?.replace(R.id.content, fragment)?.addToBackStack(null)?.commit()
        }
    }
}
