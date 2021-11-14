package com.psybm7.fingerpainter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.psybm7.fingerpainter.databinding.ActivityMainBinding
import com.psybm7.fingerpainter.objects.Brush

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /**
     * Initialise the view model to store bush data
     */
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    /**
     * Launch `ColourActivity` and provide a handler for the
     * resulting value.
     *
     * When a result is returned, update the view model with the new colour.
     */
    private val colourLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val bundle = data?.extras
            val colour = bundle?.getInt("colour")

            // Set the brush colour to the selected colour
            if (colour != null) {
                this.viewModel.setColour(colour)
            }
        }
    }

    /**
     * Launch `BrushActivity` and provide a handler for the
     * resulting value.
     *
     * When a result is returned, update the view model with the new brush config.
     */
    private val brushLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val bundle = data?.extras
            val size = bundle?.getInt("width")
            val cap = bundle?.get("cap")

            if (size != null && cap != null) {
                // Cast cap to the enum value - this is dangerous but is the only way to pass enum values through intents?
                this.viewModel.setBrush(Brush(size, cap as Paint.Cap))
            }
        }
    }

    /**
     * 1. Create the data binding
     * 2. Load the passed image URI from the intent (if given)
     * 3. Observe the ViewModel's `colour` property, and update the painter view when it changes
     * 4. Observe the ViewModel's `brush` object, and update the painter view's width and stroke when it changes
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.vwPainter.load(intent.data)

        viewModel.colour.observe(this, Observer<Int> { colour ->
            binding.vwPainter.colour = colour
        })

        viewModel.brush.observe(this, Observer<Brush> { brush ->
            binding.vwPainter.brush = brush.cap
            binding.vwPainter.brushWidth = brush.width
        })
    }


    /**
     * User clicked Colour select button
     * Send current colour to Activity
     */
    fun onColourClick(view: View) {
        val intent = Intent(this, ColourActivity::class.java)

        val bundle = Bundle()
        bundle.putInt("colour", this.viewModel.getColour())

        intent.putExtras(bundle)

        colourLauncher.launch(intent)
    }

    /**
     * User clicked Brush select button
     * Send current brush width and stroke to Activity so the UI can display the current values
     */
    fun onBrushClick(view: View) {
        val intent = Intent(this, BrushActivity::class.java)

        var brush = this.viewModel.getBrush()
        if (brush == null) brush = Brush()

        val bundle = Bundle()

        bundle.putInt("width", brush.width)
        bundle.putSerializable("cap", brush.cap)

        intent.putExtras(bundle)

        brushLauncher.launch(intent)
    }
}