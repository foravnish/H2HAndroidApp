package com.h2h.medical.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.h2h.medical.R
import com.h2h.medical.activities.DailyFlowFormActivity
import com.h2h.medical.activities.NursingAssessmentFormActivity
import com.h2h.medical.activities.WeeklyFlowSheetActivity

open class HomeFragment : Fragment() {

    private lateinit var v: View
    private lateinit var mContext: Context
    private lateinit var myActivity: Activity

    private lateinit var mBtnNursing: Button
    private lateinit var mBtnDailyFlow: Button
    private lateinit var mBtnWeeklyFlowSheet: Button


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.home_fragment, container, false)


        initViews()
        listeners()

        return v
    }


    private fun listeners() {

        mBtnDailyFlow.setOnClickListener {
            DailyFlowFormActivity.open(myActivity,0)
        }

        mBtnNursing.setOnClickListener {
            NursingAssessmentFormActivity.open(myActivity)
        }

        mBtnWeeklyFlowSheet.setOnClickListener {
            DailyFlowFormActivity.open(myActivity,2)
        }
       /* mBtnWeeklyFlowSheet.setOnClickListener {
            WeeklyFlowSheetActivity.open(myActivity)
        }*/
    }


    private fun initViews() {
        mContext = requireContext()
        myActivity = this.activity!!

        mBtnNursing = v.findViewById(R.id.btn_nursing)
        mBtnDailyFlow = v.findViewById(R.id.btn_daily_flow)
        mBtnWeeklyFlowSheet = v.findViewById(R.id.btn_weelky_time_sheet)
    }

}