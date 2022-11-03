package com.example.workoutapp.navigation

import androidx.navigation.NavController
import com.example.workoutapp.R

class Navigation(private val navController: NavController) {

    fun mainToDurationPicker() {
        navController.navigate(R.id.action_mainFragment2_to_durationPickerFragment)
    }

    fun actionDurationPickerFragmentToCountdownFragment() {
        navController.navigate(R.id.action_durationPickerFragment_to_countdownFragment)
    }

    fun actionCountdownFragmentToExerciseFragment() {
        navController.navigate(R.id.action_countdownFragment_to_exerciseFragment)
    }

    fun actionExerciseFragmentToResultsFragment() {
        navController.navigate(R.id.action_exerciseFragment_to_resultsFragment)
    }

    fun actionResultsFragmentToExerciseFragment() {
        navController.navigate(R.id.action_resultsFragment_to_exerciseFragment)
    }

    fun actionResultsFragmentToDurationPickerFragment() {
        navController.navigate(R.id.action_resultsFragment_to_durationPickerFragment)
    }
}