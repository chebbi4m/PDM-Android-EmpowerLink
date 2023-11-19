package tn.esprit.pdm.uikotlin.skills

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import tn.esprit.pdm.databinding.FragmentEditBinding
import tn.esprit.pdm.uikotlin.SessionManager
import tn.esprit.pdm.utils.Apiuser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SkillsFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentEditBinding
    private lateinit var apiUser: Apiuser
    private lateinit var skillAdapter: SkillsAdapter
    private lateinit var skillsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        skillsList = binding.rvSkills
        apiUser = Apiuser.create()
        sessionManager = SessionManager(requireContext())
        skillAdapter = SkillsAdapter(mutableListOf()) // Utilisez une liste mutable ici

        skillsList.adapter = skillAdapter
        skillsList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        fetchSkills()
    }

    private fun fetchSkills() {
        val userId = "6550bce0a0d1a744ea94d641"
        val call = apiUser.getSkills(userId)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val skillsObject = response.body()
                    Log.e("hhh", "Response successful")

                    skillsObject?.let {
                        val skillsList = it.getAsJsonArray("skills")
                            ?.map { it.asString }
                            ?: emptyList()

                        requireActivity().runOnUiThread {
                            updateSkillsList(skillsList)
                        }
                    }
                } else {
                    Log.e("hhh", "Response not successful: ${response.code()}")
                    Log.e("hhh", "Response message: ${response.message()}")
                    Log.e("hhh", "Response error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("zzz", "Failure: ${t.message}")
            }
        })
    }



    private fun updateSkillsList(skillsList: List<String>) {
        skillAdapter.setSkills(skillsList)
    }
}
