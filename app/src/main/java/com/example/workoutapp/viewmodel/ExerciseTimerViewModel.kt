package com.example.workoutapp.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val DURATION = 6000L
private const val COUNT_DOWN_INTERVAL = 1000L
private const val TAG = "ExerciseTimerViewModel"

class ExerciseTimerViewModel() : ViewModel() {
    private val _totalElapsedTime = MutableLiveData<Long>(0)
    val totalElapsedTime: LiveData<Long> = _totalElapsedTime

    private val _timerStarted = MutableLiveData<Boolean>(false)
    val timerStarted: LiveData<Boolean> = _timerStarted

    private val _durationInMills = MutableLiveData<Long>(0)
    val durationInMills: LiveData<Long> = _durationInMills

    private val _isCounterRunning = MutableLiveData(false)
    val isCounterRunning: LiveData<Boolean> = _isCounterRunning

    private val _nextPhoto = MutableLiveData(false)
    val nextPhoto: LiveData<Boolean> = _nextPhoto

    private val _timeLeft = MutableLiveData<Int>(5)
    val timeLeft: LiveData<Int> = _timeLeft

    private val _totalElapsedTimeString = MutableLiveData<String>()
    val totalElapsedTimeString: LiveData<String> = _totalElapsedTimeString

    private lateinit var timerExercise: CountDownTimer


    fun startTimer() {
        timerExercise.start()
        Log.d(TAG,"HERE")
        _isCounterRunning.value = true
        _timerStarted.value = true
    }

    fun formatString(int: Int) {

        var secString = (int % 60).toString()
        var minString = (int / 60).toString()


        if (secString.length == 1) secString = "0${secString.toString()}" ?: secString
        if (minString.length == 1) minString = "0${minString.toString()}" ?: minString

        _totalElapsedTimeString.value = "$minString:$secString"
    }

    fun setNextPhotoToFalse() {
        _nextPhoto.value = false
    }

    fun setDuration(duration: Long) {
        _durationInMills.value = duration * 1000
        _timeLeft.value = (duration).toInt()
        setTimer()
    }

    private fun setTimer(duration: Long = _durationInMills.value!!) {
        timerExercise = object : CountDownTimer(duration, COUNT_DOWN_INTERVAL) {
            override fun onTick(p0: Long) {
                _totalElapsedTime.value = totalElapsedTime.value!! + 1
                Log.d(TAG,_durationInMills.value.toString())
                formatString(totalElapsedTime.value!!.toInt())
                _timeLeft.value = timeLeft.value!! - 1
            }

            override fun onFinish() {
                _timeLeft.value = _durationInMills.value!!.toInt() / 1000
                _nextPhoto.value = true
                startTimer()
            }

        }
    }



    fun resetTimer() {
        timerExercise.cancel()
        _timerStarted.value = false
        _totalElapsedTime.value = 0
//        _totalElapsedTimeString.value = "Boris"
//        _durationInMills.value = 0
    }

    fun pauseOrResumeTimer() {
        if (_isCounterRunning.value!!) {
            _isCounterRunning.value = false
            timerExercise.cancel()
        } else {
            timerExercise.start()
            _isCounterRunning.value = true
            _timeLeft.value = (durationInMills.value)!!.toInt()/1000
        }
    }

    fun isIntervalValueValid(interval: Long): Boolean {
        if (interval < 3) {
            _timeLeft.value = 3
            return false
        }
        _timeLeft.value = interval.toInt()
        return true
    }
}