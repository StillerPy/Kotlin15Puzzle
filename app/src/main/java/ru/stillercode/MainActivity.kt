package ru.stillercode

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.stillercode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var buttonGrid: List<List<Button>>
    private lateinit var binding: ActivityMainBinding
    private var game = Game15(1)
    private var level = 1
    private var gameFinished = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonGrid = makeButtonGrid()
        updateButtons()

    }


    fun boardTapped(view: View) {
        if (view !is Button) {
            return
        }
        tapButton(view)
    }
    private fun getButtonCords(button: Button): XY {
        for (x in 0..3) {
            for (y in 0..3) {
                if (buttonGrid[y][x] === button) {
                    return XY(x, y)
                }
            }
        }
        throw IllegalStateException("could not find $button in buttonGrid!")
    }

    private fun tapButton(button: Button) {
        val xy = getButtonCords(button)
        binding.lowerText.text = game.tap(xy)
        if (game.isSolved && !gameFinished) {
            gameFinished = true
            level++
        }
        updateButtons()
    }

    fun makeButtonGrid(): List<List<Button>> {
        return listOf(
            listOf(
                binding.B00,
                binding.B10,
                binding.B20,
                binding.B30,
            ),
            listOf(
                binding.B01,
                binding.B11,
                binding.B21,
                binding.B31,
            ),
            listOf(
                binding.B02,
                binding.B12,
                binding.B22,
                binding.B32,
            ),
            listOf(
                binding.B03,
                binding.B13,
                binding.B23,
                binding.B33,
            ),
        )
    }
    @SuppressLint("SetTextI18n")
    fun updateButtons() {
        for (x in 0..3) {
            for (y in 0..3) {
                val n = game.get(x, y)
                if (n == 0) {
                    buttonGrid[y][x].text = ""
                } else {
                    buttonGrid[y][x].text = n.toString()
                }
            }
        }
        binding.upperText.text = "Level: $level"
    }
    fun newGame(view: View) {
        println("New game")
        game = Game15(level)
        gameFinished = false
        updateButtons()
        binding.lowerText.text = ""
    }
}