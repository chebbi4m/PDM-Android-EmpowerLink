package tn.esprit.pdm.uikotlin.experience

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Experience
import tn.esprit.pdm.uikotlin.SessionManager

class NewExperienceAdapter(
    private val context: Context,
    private val experiences: List<Experience>,
    private val onItemClick: (Experience) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sessionManager = SessionManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val experience = experiences[viewType]
        val username = "wassim" // Replace with your username retrieval logic
        Log.d("CurrentUser", "Current User: $username")
        Log.d("UsernameMatch", "Match: ${experience.creatorName == username}")
        if (experience.creatorName == username) {
            view = LayoutInflater.from(context).inflate(R.layout.user_experience, parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.received_experience, parent, false)
        }
        return ExperienceViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExperienceViewHolder) {
            val experience = experiences[position]
            holder.bind(experience)
            holder.itemView.setOnClickListener {
                onItemClick(experience)
            }
        }
    }


    override fun getItemCount(): Int {
        return experiences.size
    }

    inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTextView: TextView = itemView.findViewById(R.id.experienceText)
        private val circularImageView: ImageView = itemView.findViewById(R.id.creatorImage)
        private val circularInitialTextView: TextView = itemView.findViewById(R.id.creatorInitial)

        fun bind(experience: Experience) {
            textTextView.text = experience.experienceText

            // Set the circular background with the first letter of the username
            val firstLetter = experience.creatorName.firstOrNull()?.toUpperCase()
            circularInitialTextView.text = firstLetter?.toString()
        }
    }

    private fun getLayoutResId(position: Int): Int {
        if (position in 0 until experiences.size) {
            val experience = experiences[position]
            //val token = sessionManager.getUserName().toString()
            //val decodedToken = sessionManager.decodeToken(token)
            //val username = decodedToken.username.toString()
            val username = "wassim"
            Log.d("CurrentUser", "Current User: $username")
            // Add this log statement to check if the creatorName and username match
            Log.d("UsernameMatch", "Match: ${experience.creatorName == username}")
            if (experience.creatorName == username) {
                return R.layout.user_experience
            }
        }
        return R.layout.received_experience // Replace with your default layout resource ID
    }
}
