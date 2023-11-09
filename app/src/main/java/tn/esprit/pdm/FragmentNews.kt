package tn.esprit.pdm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tn.esprit.pdm.databinding.FragmentNewsBinding

private lateinit var binding: FragmentNewsBinding
class FragmentNews : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)



        return binding.root
    }

}