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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        buttonGrid = makeButtonGrid()
        updateButtons()
        println(buttonGrid[0][0])
        binding.upperText.text = "Check"
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
        game.tap(xy)
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
            println(x)
            for (y in 0..3) {
                buttonGrid[y][x].text = game.get(x, y).toString()
            }
        }
    }

    fun newGame(view: View) {
        println("New game")
        game = Game15(level)
        updateButtons()
    }
}