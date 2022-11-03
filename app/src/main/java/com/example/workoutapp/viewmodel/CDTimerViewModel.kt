package com.example.workoutapp.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val DURATION = 4000L
private const val COUNT_DOWN_INTERVAL = 1000L
private const val TAG = "CDTimerViewModel"

class CDTimerViewModel() : ViewModel() {
    private val _duration = MutableLiveData<Int>(DURATION.toInt())
    val duration: LiveData<Int> = _duration

    private val _timerDone = MutableLiveData(false)
    val timerDone: LiveData<Boolean> = _timerDone

    private val _time = MutableLiveData(DURATION / 1000)
    val time: LiveData<Long> = _time

    private val _timeString = MutableLiveData<String>()
    val timeString: LiveData<String> = _timeString

    private val timer = object : CountDownTimer(DURATION, COUNT_DOWN_INTERVAL) {
        override fun onTick(p0: Long) {
            _time.value = time.value!! - 1
            formatString(time.value!!.toInt())
        }

        override fun onFinish() {
            _timerDone.value = true
        }

    }

    fun startTimer() {
        timer.start()
    }

    fun formatString(int: Int) {
        _timeString.value = int.toString()
    }

    fun resetTimer() {
        _time.value = DURATION / 1000
        _timerDone.value = false
        timer.cancel()
    }

}