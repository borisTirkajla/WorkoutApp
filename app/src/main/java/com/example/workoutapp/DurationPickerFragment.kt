package com.example.workoutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.workoutapp.databinding.FragmentDurationPickerBinding
import com.example.workoutapp.viewmodel.ExerciseTimerViewModel
import com.google.android.material.snackbar.Snackbar

private const val TAG = "DurationPickerFragment"
class DurationPickerFragment : Fragment() {
    private var _binding: FragmentDurationPickerBinding? = null
    private val binding get() = _binding!!

    private val exerciseTimerViewModel: ExerciseTimerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDurationPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseTimerViewModel = exerciseTimerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnStart.setOnClickListener {
            if (isIntervalValueValid()) {
                exerciseTimerViewModel.setDuration(binding.etInterval.text.toString().toLong())
                (activity as MainActivity).navigation?.actionDurationPickerFragmentToCountdownFragment()
            } else {
                Snackbar.make(
                    binding.root,
                    "Interval must be greater than 3 sec",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun isIntervalValueValid(): Boolean {
        return exerciseTimerViewModel.isIntervalValueValid(
            binding.etInterval.text.toString().toLong()
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}