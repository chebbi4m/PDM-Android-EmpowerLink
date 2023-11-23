package tn.esprit.pdm.uikotlin.experience

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience

class ExperienceAdapter(
    private val context: Context,
    private val experiences: List<Experience>,
    private val onItemClick: (Experience) -> Unit
) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.experience__item, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = experiences[position]

        holder.experienceTitle.text = experience.experienceTitle
        holder.experienceDate.text = experience.experienceDate

        holder.itemView.setOnClickListener {
            onItemClick(experience)
        }
    }

    override fun getItemCount(): Int = experiences.size

    inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val experienceTitle: TextView = itemView.findViewById(R.id.experienceTitle)
        val experienceDate: TextView = itemView.findViewById(R.id.experienceDate)
        // Add other views if needed
    }
}
