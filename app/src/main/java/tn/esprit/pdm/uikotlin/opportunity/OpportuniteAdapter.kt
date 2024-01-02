package tn.esprit.pdm.uikotlin.opportunity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.request.Opportunite

class OpportuniteAdapter(private var opportunites: List<Opportunite>) : RecyclerView.Adapter<OpportuniteAdapter.OpportuniteViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class OpportuniteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoIv: ImageView = itemView.findViewById(R.id.logoIv)
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val description: TextView = itemView.findViewById(R.id.description)

        init {
            // Add code here to handle clicks on items if necessary
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
    fun getOpportunites(): List<Opportunite> {
        return opportunites
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpportuniteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return OpportuniteViewHolder(view)
    }

    override fun onBindViewHolder(holder: OpportuniteViewHolder, position: Int) {
        val opportunite = opportunites[position]
        holder.titleTv.text = opportunite.title
        holder.description.text = opportunite.description
        // Load the image here if necessary
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DescriptionActivity::class.java)
            intent.putExtra("description", opportunite.description)
            intent.putExtra("title", opportunite.title)
            intent.putExtra("skill", opportunite.skill)
            intent.putExtra("email", opportunite.contactEmail)
            intent.putExtra("enterprise", opportunite.nomEntreprise)
            intent.putExtra("salary", opportunite.salary)
            intent.putExtra("lieu", opportunite.lieu)
            intent.putExtra("contract", opportunite.Typedecontrat)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return opportunites.size
    }

    fun updateOpportunites(newOpportunites: List<Opportunite>) {
        opportunites = newOpportunites
        notifyDataSetChanged()
    }

    fun getCurrentOpportunites(): List<Opportunite> {
        return opportunites
    }

}