package tn.esprit.pdm.uikotlin.educations

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Formation
import android.widget.TextView
import com.bumptech.glide.Glide
import tn.esprit.pdm.models.Education
import tn.esprit.pdm.uikotlin.formation.DetailsFormationsActivity

class EducationAdapter(private var educations: List<Education>) :
    RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.education_item, parent, false)
        return EducationViewHolder(view, educations)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val education = educations[position]
        holder.bind(education)
    }

    override fun getItemCount(): Int = educations.size

    // Add this method to update the data
    fun setData(newEducations: List<Education>) {
        educations = newEducations
        notifyDataSetChanged()
    }

    class EducationViewHolder(itemView: View, educations: List<Education>) : RecyclerView.ViewHolder(itemView) {
        val typeEducation: TextView = itemView.findViewById(R.id.typeEducation)

        init {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailsFormationsActivity::class.java)

                // Pass data to the intent
                intent.putExtra("type", educations[adapterPosition].type)
                intent.putExtra("description", educations[adapterPosition].description)
                intent.putExtra("dure", educations[adapterPosition].dure)
                // Add any other data you want to pass

                // Start the activity
                context.startActivity(intent)
            }

        }

        fun bind(education: Education) {
            typeEducation.text = education.type
        }
    }


}
