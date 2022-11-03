package com.example.workoutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.databinding.FragmentExerciseBinding
import com.example.workoutapp.viewmodel.ExerciseTimerViewModel
import com.example.workoutapp.viewmodel.ExerciseViewModel

private const val TAG = "ExerciseFragment"

class ExerciseFragment : Fragment() {
    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val exerciseTimerViewModel: ExerciseTimerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseViewModel = exerciseViewModel
        binding.exerciseTimerViewModel = exerciseTimerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        exerciseTimerViewModel.timerStarted.value?.let {
            if (!it) {
                exerciseTimerViewModel.startTimer()
            }
        }
        exerciseTimerViewModel.nextPhoto.observe(viewLifecycleOwner){
            if(it){
                exerciseViewModel.getRandomPhoto()
                exerciseTimerViewModel.setNextPhotoToFalse()
            }
        }
        binding.btnPause.setOnClickListener{
            binding.btnPause.visibility = View.INVISIBLE
            binding.btnResume.visibility = View.VISIBLE
            exerciseTimerViewModel.pauseOrResumeTimer()
        }
        binding.btnResume.setOnClickListener{
            binding.btnPause.visibility = View.VISIBLE
            binding.btnResume.visibility = View.INVISIBLE
            exerciseTimerViewModel.pauseOrResumeTimer()
        }
        binding.btnStop.setOnClickListener {
            exerciseTimerViewModel.pauseOrResumeTimer()
            (activity as MainActivity).navigation?.actionExerciseFragmentToResultsFragment()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}