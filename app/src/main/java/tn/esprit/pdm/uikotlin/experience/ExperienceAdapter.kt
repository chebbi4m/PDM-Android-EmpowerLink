// ExperienceAdapter.kt
package tn.esprit.pdm.uikotlin.experience

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience

class ExperienceAdapter(
    context: Context,
    private val experiences: List<Experience>,
    private val onItemClick: (Experience) -> Unit
) : ArrayAdapter<Experience>(context, R.layout.experience__item, experiences) {

    @SuppressLint("ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.experience__item, parent, false)

        val experienceImage: ImageView = view.findViewById(R.drawable.profile) // Assuming this is the correct ID
        val experienceTitle: TextView = view.findViewById(R.id.experienceTitle)
        val experienceDate: TextView = view.findViewById(R.id.experienceDate)

        val experience = getItem(position)

        experience?.let {
            // Assuming creatorImage is an Int representing a drawable resource
            experienceImage.setImageResource(R.drawable.profile)
            experienceTitle.text = it.experienceTitle
            experienceDate.text = it.experienceDate
        }

        // Set click listener for the item
        view.setOnClickListener {
            experience?.let {
                onItemClick(it)
            }
        }

        return view
    }
}
