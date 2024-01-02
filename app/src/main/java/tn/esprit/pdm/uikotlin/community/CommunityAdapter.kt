package tn.esprit.pdm.uikotlin.community

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.pdm.R
import tn.esprit.pdm.models.Community
import tn.esprit.pdm.models.username
import tn.esprit.pdm.uikotlin.SessionManager

class CommunityAdapter(
    context: Context,
    communities: List<Community>,
    private val onItemClick: (Community) -> Unit
) : ArrayAdapter<Community>(context, R.layout.community__item, communities) {

    private lateinit var sessionManager: SessionManager
    init {
        sessionManager = SessionManager(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.community__item, parent, false)

        val communityImage: ImageView = view.findViewById(R.id.communityImage)
        val communityName: TextView = view.findViewById(R.id.communityName)
        val communityCategory: TextView = view.findViewById(R.id.communityCategory)
        val memberCount: TextView = view.findViewById(R.id.memberCount)
        val pinButton: ImageButton = view.findViewById(R.id.pinButton)

        val community = getItem(position)

        community?.let {
            communityImage.setImageResource(it.image)
            communityName.text = it.title
            communityCategory.text = it.category
            memberCount.text = context.getString(R.string.member_count, it.members.size)

        }
        val token = sessionManager.getUserName().toString()
        val decodedToken = sessionManager.decodeToken(token)
        val username = decodedToken.username.toString()


        if (community != null) {
            if (username in community.pinned) {
                pinButton.setImageResource(R.drawable.pin)
            } else {
                pinButton.setImageResource(R.drawable.thumbtacks)
            }
        }

        view.setOnClickListener {
            community?.let {
                onItemClick(it)
            }
        }

        return view
    }




    private fun isUserInMembersList(members: List<String>): Boolean {
        //val token = sessionManager.getUserName().toString()
        //val decodedToken = sessionManager.decodeToken(token)
        //val username = decodedToken.username.toString()
        val username = "wassim"
        return members.contains(username)
    }

    private fun isUserInPinnedList(members: List<String>): Boolean {
        //val token = sessionManager.getUserName().toString()
        //val decodedToken = sessionManager.decodeToken(token)
        //val username = decodedToken.username.toString()
        val username = "wassim"
        return members.contains(username)
    }

    private fun isUserInPendingList(members: List<String>): Boolean {
        //val token = sessionManager.getUserName().toString()
        //val decodedToken = sessionManager.decodeToken(token)
        //val username = decodedToken.username.toString()
        val username = "wassim"
        return members.contains(username)
    }
}
