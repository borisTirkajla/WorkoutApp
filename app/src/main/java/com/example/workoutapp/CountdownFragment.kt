package com.example.workoutapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.workoutapp.databinding.FragmentCountdownBinding
import com.example.workoutapp.viewmodel.CDTimerViewModel

private const val TAG = "CountdownFragment"

class CountdownFragment : Fragment() {
    private var _binding: FragmentCountdownBinding? = null
    private val binding get() = _binding!!

    private val cdTimerViewModel: CDTimerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        _binding = FragmentCountdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cdTimerViewModel = cdTimerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        cdTimerViewModel.timerDone.observe(viewLifecycleOwner) {
            if (it) {
                cdTimerViewModel.resetTimer()
                (activity as MainActivity).navigation?.actionCountdownFragmentToExerciseFragment()
            }
        }
        cdTimerViewModel.startTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}