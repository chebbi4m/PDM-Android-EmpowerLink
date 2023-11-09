package tn.esprit.pdm

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.pdm.models.Experience

// ExperienceAdapter.kt
class ExperienceAdapter(
    context: Context,
    private val experiences: List<Experience>
) : ArrayAdapter<Experience>(context, R.layout.experience__item, experiences) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.experience__item, parent, false)

        // Set data to views
        val creatorImage: ImageView = view.findViewById(R.id.creatorImage)
        val creatorName: TextView = view.findViewById(R.id.creatorName)
        val experienceDate: TextView = view.findViewById(R.id.experienceDate)
        val experienceTitle: TextView = view.findViewById(R.id.experienceTitle)
        val experienceText: TextView = view.findViewById(R.id.experienceText)

        val experience = getItem(position)

        experience?.let {
            creatorImage.setImageResource(R.drawable.profile)
            creatorName.text = it.creatorName
            experienceDate.text = it.experienceDate
            experienceTitle.text = it.experienceTitle
            experienceText.text = it.experienceText
        }

        return view
    }
}

