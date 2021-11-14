package com.psybm7.fingerpainter

import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psybm7.fingerpainter.objects.Brush

/**
 * View model to store brush details:
 *  - Colour
 *  - Width
 *  - Stroke
 */
class MainViewModel: ViewModel() {
    /**
     * Observable colour value to subscribe to updates
     */
    val colour: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    /**
     * Observable brush value to subscribe to updates
     * @sample Brush Stores brush width and stroke
     */
    val brush: MutableLiveData<Brush> by lazy {
        MutableLiveData<Brush>(Brush())
    }

    /**
     * Get the current colour value (outside the Observable)
     */
    fun getColour(): Int {
        return this.colour.value ?: Color.BLACK
    }

    /**
     * Update the brush colour
     * @param value Integer value representing the colour
     */
    fun setColour(value: Int) {
        this.colour.value = value
    }

    /**
     * Get the current brush value (outside the Observable)
     */
    fun getBrush(): Brush? {
        return this.brush.value
    }

    /**
     * Update the brush size and stroke
     * @param brush Brush object containing config
     */
    fun setBrush(brush: Brush) {
        this.brush.value = brush
    }
}