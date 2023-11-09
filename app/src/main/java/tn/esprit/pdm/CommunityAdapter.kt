package tn.esprit.pdm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.pdm.models.Community

class CommunityAdapter(
    context: Context,
    private val communities: List<Community>,
    private val onItemClick: (Community) -> Unit
) : ArrayAdapter<Community>(context, R.layout.community__item, communities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.community__item, parent, false)

        val communityImage: ImageView = view.findViewById(R.id.communityImage)
        val communityName: TextView = view.findViewById(R.id.communityName)
        val communityCategory: TextView = view.findViewById(R.id.communityCategory)

        val community = getItem(position)

        // Set data to views
        community?.let {
            communityImage.setImageResource(it.communityImage)
            communityName.text = it.communityTitle
            communityCategory.text = it.communityCategory
        }

        // Set click listener for the item
        view.setOnClickListener {
            community?.let {
                onItemClick(it)
            }
        }

        return view
    }
}


