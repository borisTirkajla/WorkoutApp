package com.example.workoutapp.viewmodel

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.R
import com.example.workoutapp.viewmodel.ExerciseViewModel.Companion.photoList

private const val TAG = "ExerciseViewModel"

class ExerciseViewModel : ViewModel() {

    private var _currentPhoto = MutableLiveData<Int>()
    val currentPhoto: LiveData<Int> = _currentPhoto

    init {
        _currentPhoto.value = photoList.shuffled()[0]
    }

    fun getRandomPhoto() {
        val oldPhoto = _currentPhoto.value
        var check = true
        while (check) {
            _currentPhoto.value = photoList.shuffled()[0]
            if (oldPhoto.toString() != _currentPhoto.value.toString()) {
                check = false
            }
        }
    }

    companion object {
        val photoList = listOf<Int>(
            R.drawable.position1,
            R.drawable.position2,
            R.drawable.posotion3
        )
    }
}