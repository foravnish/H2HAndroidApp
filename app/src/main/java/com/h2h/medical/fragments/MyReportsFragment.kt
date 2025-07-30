package com.h2h.medical.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h2h.medical.R

class MyReportsFragment : Fragment() {

    private lateinit var v: View
    private lateinit var mContext: Context
    private lateinit var myActivity: Activity

    companion object {
        fun newInstance(): MyReportsFragment {
            return MyReportsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.orders_fragment, container, false)

        mContext = requireContext()
        myActivity = this.activity!!

        return v
    }

}