package tn.esprit.pdm.uikotlin.searchuser

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.esprit.pdm.databinding.ItemSuggsetionBinding
import tn.esprit.pdm.models.request.LoginRequest
class SuggestionAdapter(private var suggestions: List<LoginRequest>) :
    RecyclerView.Adapter<SuggestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSuggsetionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setData(newData: List<LoginRequest>) {
        suggestions = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suggestion = suggestions[position]

        // Bind data to the views using View Binding
        holder.binding.usernameTextView.text = suggestion.username
        Glide.with(holder.itemView.context).load(suggestion.image).circleCrop().into(holder.binding.profileImage)

        // Set click listener for the action button
        holder.binding.actionButton.setOnClickListener {
            // Handle the action button click (e.g., add the user)
        }
    }

    override fun getItemCount(): Int {
        return suggestions.size
        Log.d("SuggestionAdapter", "Updating suggestion list with ${suggestions.size} items")
    }


    inner class ViewHolder(val binding: ItemSuggsetionBinding) : RecyclerView.ViewHolder(binding.root)
}

