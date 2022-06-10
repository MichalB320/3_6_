package com.example.arrows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.arrows.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
        binding.tlacidlo.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment2_to_levelFragment)
            //view.findNavController().navigate(R.id.action_titleFragment2_to_gameOverFragment)
        }
        return binding.root

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_title, container, false)
    }
}