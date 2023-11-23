package tn.esprit.pdm.uikotlin.searchuser

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tn.esprit.pdm.R
import tn.esprit.pdm.models.request.LoginRequest

class UserAdapter(private val context: Context, private val userList: List<LoginRequest>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder class to hold the views
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val userProfileImageView: ImageView = itemView.findViewById(R.id.userProfileImageView) // Ajustez le nom ici

        init {
            // Ajoutez un écouteur de clic à l'élément utilisateur
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedUser: LoginRequest = userList[position]

                    // Créez une intention pour démarrer l'activité VisitProfileActivity
                    val intent = Intent(context, VisitProfile::class.java).apply {
                        putExtra(EXTRA_USER, clickedUser)
                    }

                    // Démarrer l'activité avec l'intention
                    context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        val imageUrl = currentUser.image

        // Bind user data to views
        holder.usernameTextView.text = currentUser.username
        Glide.with(context)
            .load(imageUrl)
            .into(holder.userProfileImageView) // Ajustez le nom ici
        // Bind other user details as needed
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
