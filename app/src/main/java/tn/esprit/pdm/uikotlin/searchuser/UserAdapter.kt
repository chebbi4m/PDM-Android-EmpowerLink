    package tn.esprit.pdm.uikotlin.searchuser
    import android.content.Context
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import tn.esprit.pdm.R
    import tn.esprit.pdm.models.request.LoginRequest

    class UserAdapter(private val context: Context, private val userList: List<LoginRequest>) :
        RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        // ViewHolder class to hold the views
        class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
            // Add other user details views as needed
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val currentUser = userList[position]

            // Bind user data to views
            holder.usernameTextView.text = currentUser.username
            // Bind other user details as needed
        }

        override fun getItemCount(): Int {
            return userList.size
        }
    }
