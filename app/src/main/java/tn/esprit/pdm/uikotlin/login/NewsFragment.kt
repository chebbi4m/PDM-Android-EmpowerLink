package tn.esprit.pdm.uikotlin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tn.esprit.pdm.R
import tn.esprit.pdm.databinding.FragmentNewsBinding
import tn.esprit.pdm.uikotlin.SessionManager

class NewsFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        val token = sessionManager.getUserDescription().toString()

        // Décodez le token
        val decodedToken = sessionManager.decodeToken(token)

        // Utilisez les informations du token
        binding.txtAbout.text = decodedToken.description
    }
}
