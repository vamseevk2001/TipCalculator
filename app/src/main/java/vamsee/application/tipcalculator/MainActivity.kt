
package vamsee.application.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import vamsee.application.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener{ calculateTip() }
        binding.costOfServiceText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculateTip(){
        val costOfService = binding.costOfServiceText.text.toString()
        val cost = costOfService.toDoubleOrNull()
        if (cost == null) {
            binding.tipAmount.text = ""
            return
        }

        val tipPresent = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.option_20_persent -> 0.20
            R.id.option_18_persent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPresent
        val roundUp = binding.roundUpTip.isChecked
        if (roundUp) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)


    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}