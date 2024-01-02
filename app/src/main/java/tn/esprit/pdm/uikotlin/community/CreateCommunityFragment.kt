package tn.esprit.pdm.uikotlin.community
import  android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tn.esprit.gamer.utils.CommunityServices
import tn.esprit.pdm.R
import tn.esprit.pdm.models.CommunityDTO
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.utils.RetrofitInstance

class CreateCommunityFragment : DialogFragment() {

    private val communityService: CommunityServices = RetrofitInstance.createCommunityService()
    private lateinit var sessionManager: SessionManager

    interface OnCommunityCreatedListener {
        fun onCommunityCreated()
    }

    private var communityCreatedListener: OnCommunityCreatedListener? = null
    private var communityActivity: CommunityActivity? = null

    fun setOnCommunityCreatedListener(listener: OnCommunityCreatedListener, activity: CommunityActivity) {
        communityCreatedListener = listener
        communityActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_community, container, false)
        sessionManager = SessionManager(requireContext())

        // Find views in the fragment layout
        val communityNameEditText: EditText = view.findViewById(R.id.communityNameEditText)
        val communityCategoryEditText: EditText = view.findViewById(R.id.communityCategoryEditText)
        val communityObjectiveEditText: EditText = view.findViewById(R.id.communityObjectiveEditText)
        val createCommunityButton: Button = view.findViewById(R.id.createCommunityButton)

        // Set up onClickListener for the create community button
        createCommunityButton.setOnClickListener {
            // Get values from EditTexts
            val communityName = communityNameEditText.text.toString()
            val communityCategory = communityCategoryEditText.text.toString()
            val communityObjective = communityObjectiveEditText.text.toString()
            val token = sessionManager.getUserName().toString()
            val decodedToken = sessionManager.decodeToken(token)
            val username = decodedToken.username.toString()

            // Call the function to create a new community in the database
            createCommunityInDatabase(communityName, username, communityCategory, communityObjective)

            // Notify the listener that a community has been created
            communityCreatedListener?.onCommunityCreated()

            // Dismiss the dialog
            dismiss()
        }

        return view
    }

    private fun createCommunityInDatabase(name: String, username: String, category: String, objectif: String) {
        // Use Kotlin coroutines to perform the network operation on a background thread
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val token = sessionManager.getUserName().toString()
                val decodedToken = sessionManager.decodeToken(token)

                // Create a CommunityDTO object with the provided data
                val communityDTO = CommunityDTO(
                    communityId = 0,  // Replace with the actual ID
                    image = R.drawable.profile,  // Replace with the actual resource ID or image data
                    name = name,
                    category = category,
                    objectif = objectif,
                    username = decodedToken.username.toString()
                )

                // Make a network request to create the community
                val response = communityService.createCommunity(communityDTO)

                // Check if the request was successful
                if (response.isSuccessful) {
                    // Community created successfully
                    // Trigger list refresh in CommunityActivity
                    withContext(Dispatchers.Main) {
                        communityActivity?.fetchAndRefreshList()
                    }
                } else {
                    // Handle error cases
                    // You might want to log the error, show an error message, etc.
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle exceptions, e.g., network errors
            }
        }
    }
}