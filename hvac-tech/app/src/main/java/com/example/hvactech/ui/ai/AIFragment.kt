package com.example.hvactech.ui.ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.hvactech.core.ServiceLocator
import com.example.hvactech.databinding.FragmentSimpleBinding
import kotlinx.coroutines.launch

class AIFragment : Fragment() {
    private var _binding: FragmentSimpleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleBinding.inflate(inflater, container, false)
        binding.textView.text = "Asistente AI"
        binding.textView.setOnClickListener {
            lifecycleScope.launch {
                val answer = ServiceLocator.aiRepository.ask(null, null, "Unidad no enfría, error E1")
                binding.textView.text = answer
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

