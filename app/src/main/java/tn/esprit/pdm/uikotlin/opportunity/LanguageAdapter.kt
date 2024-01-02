package tn.esprit.pdm.uikotlin.opportunity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R

class LanguageAdapter(var mList: List<LanguageData>) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {



    inner class LanguageViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val desc : ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setFilteredList(mList: List<LanguageData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    private lateinit var description : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        description = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item , parent , false)
        return LanguageViewHolder(view,description)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.desc.setImageResource(mList[position].logo)
        holder.titleTv.text = mList[position].title
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}