package tn.esprit.pdm.uikotlin.skills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R

class SkillsAdapter(private var skills: List<String>) : RecyclerView.Adapter<SkillsAdapter.SkillViewHolder>() {

    fun setSkills(skills: List<String>) {
        this.skills = skills
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemskils, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = skills[position]
        holder.bind(skill)
    }

    override fun getItemCount(): Int = skills.size

    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeEducation: TextView = itemView.findViewById(R.id.tvSkill)

        fun bind(skill: String) {
            typeEducation.text = skill
        }
    }
}
