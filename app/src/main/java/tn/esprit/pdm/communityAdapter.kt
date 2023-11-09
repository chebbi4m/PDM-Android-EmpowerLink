package tn.esprit.pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(private val communityList: ArrayList<Community>) :
    RecyclerView.Adapter<CommunityAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.community__item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCommunity = communityList[position]
        holder.communityTitle.text = currentCommunity.communityTitle
        holder.communityCategory.text = currentCommunity.communityCategory
        holder.communityObjectif.text = currentCommunity.communityObjectif
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val communityImage: ImageView = itemView.findViewById(R.id.CommunityImage)
        val communityTitle: TextView = itemView.findViewById(R.id.communityName)
        val communityCategory: TextView = itemView.findViewById(R.id.communityCategory)
        val communityObjectif: TextView = itemView.findViewById(R.id.communityObjectif)
    }
}
