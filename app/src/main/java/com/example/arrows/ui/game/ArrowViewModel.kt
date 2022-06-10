package com.example.arrows.ui.game

import android.graphics.Rect
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class ArrowViewModel : ViewModel() {
    private val polkaSipky = 160
    private val polomerKruhu = 145f
    private val pohyb = 14f //7f
    private var kolizie = arrayOf(Rect(), Rect(), Rect(), Rect(), Rect())
    private val _rotArrows = arrayOf(1.5f, 1.5f, 1.5f, 1.5f, 1.5f)
    private val _rotacia = MutableLiveData(arrayOf(0f, 0f, 0f, 0f, 0f))
    val rotacia: MutableLiveData<Array<Float>>
        get() = _rotacia

    fun pocitajRotaciuSipky(i: Int) {
        _rotacia.value!![i] = _rotacia.value!![i].plus(3.9999999999999999999f)
        _rotacia.apply { postValue(value) }
        _rotArrows[i] = _rotArrows[i].plus(0.06978888888999999999f)
    }

    fun nastavKoliziu(i: Int, stredKruhuX: Float, stredKruhuY: Float, polomerKruhu: Float) {
        if (_rotacia.value!![i].mod(360f) >= 350f || _rotacia.value!![i].mod(360f) <= 10) { //jeZvisloDole
            setRect(i, stredKruhuX.roundToInt() - 5, stredKruhuY.roundToInt() + polomerKruhu.toInt(), stredKruhuX.roundToInt() + 5,  stredKruhuY.roundToInt() + polomerKruhu.roundToInt() + polkaSipky)
        } else {
            setRect(i, -5, -5, -5,  -5)
        }
    }

    fun koliduje(i: Int): Boolean {
        for (j in 0..4) {
            if (kolizie[i].intersect(kolizie[j]) && i != j) {
                return true
            }
        }
        return false
    }

    fun jeZapichnuta(stredKruhuY: Float, polomerKruhu: Float, spicY: Float, spicX: Float, stredKruhuX: Float) = !(stredKruhuY + polomerKruhu <= spicY && spicX == stredKruhuX)

    fun getPohyb() = pohyb

    fun getSurYSip(i: Int) = (polomerKruhu + polkaSipky) * sin(_rotArrows[i])

    fun getSurXSip(i: Int) = (polomerKruhu + polkaSipky) * cos(_rotArrows[i]) - 50

    private fun setRect(i: Int, left: Int, top: Int, right: Int, bottom: Int) = kolizie[i].set(left, top, right, bottom)
}