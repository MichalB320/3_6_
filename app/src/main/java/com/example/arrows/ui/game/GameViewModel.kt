package com.example.arrows.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _stlacil = false
    private var _index = 0
    private val _score = MutableLiveData(0)
    private val _currentArrowCount = MutableLiveData(0)
    private val _koliduje = MutableLiveData(false)
    val koliduje: LiveData<Boolean>
        get() = _koliduje

    fun prehral() {
        if (_koliduje.value == false) {
            _koliduje.value = true
        } else {
            _koliduje.value = false
        }
    }

    val stlacil: Boolean
        get() = _stlacil

    val index: Int
        get() = _index

    val score: LiveData<Int>
        get() = _score

    val currentArrowCount: LiveData<Int>
        get() = _currentArrowCount

    fun onPal() { _stlacil = true }

    fun nestlacil() { _stlacil = false }

    fun zvysIndex() = _index++

    fun pripocitajScore() { _score.value = (_score.value)?.plus(15) }

    fun pocitadlo() { _currentArrowCount.value = (_currentArrowCount.value)?.inc() }

    fun reinitializeData() {
        _stlacil = false
        _index = 0
        _score.value = 0
        _currentArrowCount.value = 0
    }
}