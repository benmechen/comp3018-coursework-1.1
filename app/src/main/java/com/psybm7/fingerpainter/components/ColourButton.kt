package com.psybm7.fingerpainter.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.psybm7.fingerpainter.R

/**
 * Wrapper around the default Button to add:
 *  - A custom attribute to pass in this button's colour
 *  - A `colour` property for click handlers to fetch this button's colour
 *  - A set of default layout properties to help display this button in a `GridLayout`
 */
class ColourButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatButton(context, attrs, defStyleAttr) {
    var colour = Color.BLACK

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColourButton)
        val colour = typedArray.getColor(R.styleable.ColourButton_pickerColour, 0)
        val selectedColour = typedArray.getColor(R.styleable.ColourButton_selectedColour, 0)
        this.colour = colour ?: Color.BLACK
        this.setBackgroundColor(this.colour)

        this.gravity = Gravity.CENTER
        this.width = 0
        this.height = 300

        typedArray.recycle()
    }

    /**
     * The `ColourActivity` has updated the selected colour, so check if it matches this Button's colour
     */
    fun setSelectedColour(selected: Int) {
        Log.d("comp3018", "Selected: $selected")
        if (this.colour == selected) this.setSelected()
    }

    /**
     * Add an arrow indicator to the Button
     */
    private fun setSelected() {
        val indicator = AppCompatResources.getDrawable(context, android.R.drawable.arrow_up_float)
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, indicator)
    }
}