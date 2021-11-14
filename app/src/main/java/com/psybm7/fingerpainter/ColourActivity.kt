package com.psybm7.fingerpainter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.psybm7.fingerpainter.components.ColourButton
import com.psybm7.fingerpainter.databinding.ActivityBrushBinding
import com.psybm7.fingerpainter.databinding.ActivityColourBinding

class ColourActivity : AppCompatActivity() {
    private var colour = Color.CYAN

    private lateinit var binding: ActivityColourBinding

    /**
     * 1. Set up binding
     * 2. Get the colour from the intent
     * 3. Set the `colour` variable
     * 4. Pass selected colour down to ColourButtons
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_colour)

        title = "Select a colour"

        val bundle = intent.extras
        val colour = bundle?.getInt("colour")

        Log.d("comp3018", "Passed: $colour")

        if (colour != null) {
            this.colour = colour
            this.binding.selectedColour = this.colour
        }
    }

    /**
     * Handle a tap on a colour
     *
     * Cast the tapped view to a `ColourButton`, which has a public `colour` property.
     * Send this colour value back to the `MainActivity`.
     */
    fun onClickColour(view: View) {
        val bundle = Bundle()
        this.colour = (view as ColourButton).colour
        bundle.putInt("colour", this.colour)

        val intent = Intent()
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}