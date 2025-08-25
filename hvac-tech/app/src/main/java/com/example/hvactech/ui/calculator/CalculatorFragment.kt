package com.example.hvactech.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hvactech.databinding.FragmentSimpleBinding

class CalculatorFragment : Fragment() {
    private var _binding: FragmentSimpleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleBinding.inflate(inflater, container, false)
        binding.textView.text = "Cálculo de gas"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

