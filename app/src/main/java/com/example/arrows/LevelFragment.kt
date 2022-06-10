package com.example.arrows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.arrows.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLevelBinding>(inflater, R.layout.fragment_level, container, false)
        binding.level1Button.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_levelFragment_to_gameFragment2)
        }
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_level, container, false)
    }

}