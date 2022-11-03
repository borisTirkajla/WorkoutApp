package com.example.workoutapp.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val DURATION = 6000L
private const val ONE_SEC = 1000L
private const val COUNT_DOWN_INTERVAL = 20L
private const val TAG = "ExerciseTimerViewModel"

class ExerciseTimerViewModel() : ViewModel() {
    private val _totalElapsedTime = MutableLiveData<Long>(0)
    val totalElapsedTime: LiveData<Long> = _totalElapsedTime

    private var _offsetTime : Long = 0L

    private var _numberOfIntervalsInOneSec: Int = 0
    private var _numberOfIntervals: Int = 0

    private var _counter: Int = 0

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

        _isCounterRunning.value = true
//        _timerStarted.value = true
    }

    fun formatString(int: Int) {

        var millisString = (int % 1000).toString()
        var secString = (int / 1000).toString()
        var minString = (int / 60000).toString()


        if (millisString.length == 2) millisString = "${millisString}0"

        if (millisString.length == 1) millisString = "${millisString}00"

        if (secString.length == 1) secString = "0${secString}" ?: secString
        if (minString.length == 1) minString = "0${minString}" ?: minString

        _totalElapsedTimeString.value = "$minString:$secString:$millisString"
    }

    fun setNextPhotoToFalse() {
        _nextPhoto.value = false
    }

    fun setDuration(duration: Long) {
        _durationInMills.value = duration * 1000
        _timeLeft.value = (duration).toInt()
        configureTimer()
        setTimer()
    }

    private fun setTimer(duration: Long = _durationInMills.value!!) {
        timerExercise = object : CountDownTimer(_durationInMills.value!!, COUNT_DOWN_INTERVAL) {
            override fun onTick(p0: Long) {
//                formatString(_durationInMills.value!!.toInt() - p0.toInt())
                _totalElapsedTime.value = (_durationInMills.value!!-p0) + _offsetTime
                formatString(totalElapsedTime.value!!.toInt())
            }

            override fun onFinish() {
                _nextPhoto.value = true
                _offsetTime = _totalElapsedTime.value!!
                startTimer()
//                _counter++
//                _timeLeft.value = _timeLeft.value!!.minus(1)
//                if (_counter == (_durationInMills.value!!/1000).toInt()) {
//                    _nextPhoto.value = true
//                    _timeLeft.value = (_durationInMills.value!! / 1000).toInt()
//                    _counter = 0
//                }

            }

        }
    }

    private fun configureTimer() {
        _numberOfIntervals = (_durationInMills.value!!.toInt() / COUNT_DOWN_INTERVAL).toInt()
        _numberOfIntervalsInOneSec = (1000 / COUNT_DOWN_INTERVAL).toInt()

    }

    fun resetTimer() {
        timerExercise.cancel()
        _offsetTime = 0
        _totalElapsedTime.value = 0
    }

    fun pauseOrResumeTimer() {
        if (_isCounterRunning.value!!) {
            _isCounterRunning.value = false
            timerExercise.cancel()
            _offsetTime = _totalElapsedTime.value!!
        } else {
            timerExercise.start()
            _isCounterRunning.value = true
//            _timeLeft.value = (durationInMills.value)!!.toInt() / 1000
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