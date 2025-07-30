package com.h2h.medical.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.h2h.medical.R

class AdapterLoopingViewPager(
    context: Context,
    itemList: ArrayList<String>,
    isInfinite: Boolean
) : LoopingPagerAdapter<String>(itemList, isInfinite) {

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context)
            .inflate(R.layout.item_looping_viewpager, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun bindView(convertView: View, position: Int, viewType: Int) {

        (convertView.findViewById(R.id.tv_text) as TextView).text = itemList!![position]

    }
}