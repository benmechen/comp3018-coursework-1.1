package com.psybm7.fingerpainter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.psybm7.fingerpainter.databinding.ActivityBrushBinding

class BrushActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBrushBinding

    /**
     * 1. Register view binding
     * 2. Get the width & cap from the intent
     * 3. Set the width slider value to the passed in value
     * 4. Cast the serialised enum cap value to `Paint.Cap` and select the correct radio button
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Update brush details"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_brush)

        val bundle = intent.extras

        val width = bundle?.getInt("width") ?: 20
        val cap = (bundle?.get("cap") as Paint.Cap?) ?: Paint.Cap.ROUND

        Log.d("comp3018", width.toString())
        Log.d("comp3018", cap.toString())

        binding.sbWidth.progress = width

        when (cap) {
            Paint.Cap.SQUARE -> binding.rgShape.check(binding.rbSquare.id)
            else -> {
                binding.rgShape.check(binding.rbRounded.id)
            }
        }
    }

    /**
     * Get current selection values from the UI and send back in an Intent
     */
    fun onClickSave(view: View) {
        val width = this.binding.sbWidth.progress
        val capId = this.binding.rgShape.checkedRadioButtonId

        var cap = Paint.Cap.ROUND

        when (capId) {
            this.binding.rbRounded.id -> cap = Paint.Cap.ROUND
            this.binding.rbSquare.id -> cap = Paint.Cap.SQUARE
        }

        val bundle = Bundle()
        bundle.putInt("width", width)
        bundle.putSerializable("cap", cap)

        val intent = Intent()
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)

        finish()
    }
}