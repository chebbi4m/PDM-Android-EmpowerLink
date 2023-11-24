package tn.esprit.pdm.uikotlin.experience

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.uikotlin.SessionManager


class ExperienceAdapter(
    private val context: Context,
    private val experiences: List<Experience>,
    private val onItemClick: (Experience) -> Unit

) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {
    private lateinit var sessionManager: SessionManager
    init {
        sessionManager = SessionManager(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)

        return ExperienceViewHolder(view)

    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = experiences[position]
        holder.bind(experience)
        holder.itemView.setOnClickListener {
            onItemClick(experience)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutResId(position)
    }

    override fun getItemCount(): Int {
        return experiences.size
    }

    inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.experienceTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.experienceDate)
        private val usernameTextView: TextView = itemView.findViewById(R.id.creatorName)
        private val textTextView: TextView = itemView.findViewById(R.id.experienceText)


        fun bind(experience: Experience) {
            titleTextView.text = experience.experienceTitle
            dateTextView.text = experience.experienceDate
            usernameTextView.text = experience.creatorName
            textTextView.text = experience.experienceText
        }
    }

    private fun getLayoutResId(position: Int): Int {

        val experience = experiences[position]
        val token = sessionManager.getUserName().toString()
        val decodedToken = sessionManager.decodeToken(token)
        val username = decodedToken.username.toString()
        return if (experience.creatorName == username) {
            R.layout.user_experience
        } else {
            R.layout.received_experience
        }
    }
}