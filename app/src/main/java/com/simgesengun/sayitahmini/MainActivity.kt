package com.simgesengun.sayitahmini

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.simgesengun.sayitahmini.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var points = 100
        val random = (1..100).random()

        //Logs the random number for testing purposes
        Log.e("Random",random.toString())

        //remove the error when the text is changed
        binding.editTextNumber.doOnTextChanged { _, _, _, _ ->
            binding.textInputLayoutNumber.error = null
        }

        binding.buttonGuess.setOnClickListener {
            val input = binding.editTextNumber.text.toString()
            //check if the input is blank
            if(input.isBlank()){
                binding.textInputLayoutNumber.error = getString(R.string.error)
            }else{
                val number = input.toInt()
                if (number < random) {
                    binding.textViewHint.text = getString(R.string.info_bigger)
                    points -= 1
                } else if (number > random) {
                    binding.textViewHint.text = getString(R.string.info_smaller)
                    points -= 1
                } else {
                    //win!
                    endGame(getString(R.string.info_win,points))
                }
                //clears the guessed number, updates the score and the last guess text views
                binding.editTextNumber.text = null
                binding.textViewScore.text = getString(R.string.score_updated,points)
                binding.textViewGuess.text = getString(R.string.guess_updated,number)
                if (points == 0) {
                    //lose!
                    endGame(getString(R.string.info_lose))
                }
            }
        }
    }
    /*shows the result message, makes the score unvisible, disables the text input layout
    * and changes button for a new game
    * */
    private fun endGame(result:String){
        binding.textViewHint.text = result
        binding.textViewScore.isVisible = false
        binding.textInputLayoutNumber.isEnabled = false
        binding.buttonGuess.text =  getString(R.string.new_game)
        binding.buttonGuess.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

    }
}