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
import tn.esprit.pdm.uikotlin.formation.DetailsFormationsActivity

class FormationAdapter(private var formations: List<Formation>) :
    RecyclerView.Adapter<FormationAdapter.FormationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.formation_item, parent, false)
        return FormationViewHolder(view, formations)
    }

    override fun onBindViewHolder(holder: FormationViewHolder, position: Int) {
        val formation = formations[position]
        holder.bind(formation)
    }

    override fun getItemCount(): Int = formations.size

    // Add this method to update the data
    fun setData(newFormations: List<Formation>) {
        formations = newFormations
        notifyDataSetChanged()
    }

    class FormationViewHolder(itemView: View,formations: List<Formation>) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val nbPlaceTextView: TextView = itemView.findViewById(R.id.nbPlace)
        private val nbParticipantTextView: TextView = itemView.findViewById(R.id.nbParticipant)
        private val formationImageView: ImageView = itemView.findViewById(R.id.formationImage)
        private val seeDetailsButton: Button = itemView.findViewById(R.id.seeDetailsFormations)

        init {
            // Set click listener for the item view or the "See Details" button
            itemView.setOnClickListener {
                // Handle item click here if needed
            }

            seeDetailsButton.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailsFormationsActivity::class.java)

                // Pass data to the intent
                intent.putExtra("_id", formations[adapterPosition]._id)
                intent.putExtra("title", titleTextView.text.toString())
                intent.putExtra("description", formations[adapterPosition].description)
                intent.putExtra("nbParticipant", formations[adapterPosition].nbParticipant)
                intent.putExtra("nbPlace", formations[adapterPosition].nbPlace)
                intent.putExtra("image", formations[adapterPosition].image)  // Pass the actual image data
                // Add any other data you want to pass

                // Start the activity
                context.startActivity(intent)
            }
        }

        fun bind(formation: Formation) {
            titleTextView.text = formation.title
            nbPlaceTextView.text = "Number of Places: ${formation.nbPlace}"
            nbParticipantTextView.text = "Number of Participants: ${formation.nbParticipant}"
            Glide.with(itemView.context)
                .load(formation.image)
                .into(formationImageView)
        }
    }


}
