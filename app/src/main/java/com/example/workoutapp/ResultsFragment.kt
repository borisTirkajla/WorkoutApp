package com.example.workoutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.workoutapp.databinding.FragmentResultsBinding
import com.example.workoutapp.viewmodel.ExerciseTimerViewModel

class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private val exerciseTimerViewModel: ExerciseTimerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseTimerViewModel = exerciseTimerViewModel
        binding.btnResume.setOnClickListener {
            exerciseTimerViewModel.pauseOrResumeTimer()
            (activity as MainActivity).navigation?.actionResultsFragmentToExerciseFragment()
        }
        binding.btnRestart.setOnClickListener {
            exerciseTimerViewModel.resetTimer()
            (activity as MainActivity).navigation?.actionResultsFragmentToDurationPickerFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}