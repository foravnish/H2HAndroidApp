package com.kamdhenuteam.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.h2h.medical.models.CommentData
import java.util.*
import com.h2h.medical.R
import kotlin.collections.ArrayList

class CommentsListAdapter(
    val mList: ArrayList<CommentData>,
    var itemClick: (CommentData, String) -> Unit
) :
    RecyclerView.Adapter<CommentsListAdapter.MyHolder>(), Filterable {

    var countryFilterList = ArrayList<CommentData>()
    var edtMessage : TextView? = null
    var lnbackground : RelativeLayout? = null
    var imgDelete : ImageView? = null

    init {
        countryFilterList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment_list, parent, false)

        edtMessage = v.findViewById(R.id.edtMessage) as TextView
        lnbackground = v.findViewById(R.id.lnbackground) as RelativeLayout
        imgDelete = v.findViewById(R.id.imgDelete) as ImageView

        return MyHolder(v)
    }

    override fun getItemCount(): Int {
        if (countryFilterList==null) return 0
        return countryFilterList.size
    }

    fun addList(mUpdateList: ArrayList<CommentData>) {
        mList.clear()
        mList.addAll(mUpdateList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder, p1: Int) {
        try {
            holder.bindData(countryFilterList[p1])
        } catch (e: Exception) {
        }
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(mList: CommentData) {


            edtMessage!!.text = mList.msg.toString()

            lnbackground!!.setOnClickListener {
                itemClick(countryFilterList[adapterPosition],"All")
            }
            imgDelete!!.setOnClickListener {
                itemClick(countryFilterList[adapterPosition],"Delete")
            }
        }
    }

//    fun setSelectedItem() {
//        if (id == null) return
//        val result: List<String> = id.split(",").map { it.trim() }
//        result.forEach {
//            println(it)
//            for (i in countryFilterList.indices) {
//                if (it.equals(countryFilterList[i].id, ignoreCase = true)) {
//                }
//            }
//            notifyDataSetChanged()
//        }
//    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = mList
                } else {
                    val resultList = ArrayList<CommentData>()
                    for (row in mList) {
                        if (row.msg.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<CommentData>
                notifyDataSetChanged()
            }

        }
    }

}