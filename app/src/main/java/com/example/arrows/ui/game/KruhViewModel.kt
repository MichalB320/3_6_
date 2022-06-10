package com.example.arrows.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KruhViewModel : ViewModel() {
    private val polomerKruhu = 145f
    private val _rotKruhu = MutableLiveData(0f)
    val rotKruhu: LiveData<Float>
        get() = _rotKruhu

    fun getPolomerKruhu() = polomerKruhu

    fun rotuj() {
        _rotKruhu.value = (_rotKruhu.value)?.plus(3.9999999999999999999f)
    }
}