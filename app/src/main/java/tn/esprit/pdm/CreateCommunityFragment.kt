// CreateCommunityFragment.kt
package tn.esprit.pdm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class CreateCommunityFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_community, container, false)

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

            // TODO: Add logic to create a new community with the entered values

            // Dismiss the dialog
            dismiss()
        }

        return view
    }
}
