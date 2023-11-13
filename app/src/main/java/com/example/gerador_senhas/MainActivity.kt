package com.example.gerador_senhas


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import kotlin.random.Random



class MainActivity : AppCompatActivity() {
    private lateinit var dao: SenhaDAO

    private lateinit var descriptionEditText: EditText
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var uppercaseCheckBox: CheckBox
    private lateinit var numbersCheckBox: CheckBox
    private lateinit var specialCharsCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = SenhaDAO(this)

        descriptionEditText = findViewById(R.id.editTextDescription)
        sizeSeekBar = findViewById(R.id.seekBarSize)
        uppercaseCheckBox = findViewById(R.id.checkBoxUpperCase)
        numbersCheckBox = findViewById(R.id.checkBoxNumbers)
        specialCharsCheckBox = findViewById(R.id.checkBoxSpecialChars)

        val generateButton = findViewById<Button>(R.id.buttonGenerate)
        generateButton.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val size = sizeSeekBar.progress + 1
            val useUppercase = uppercaseCheckBox.isChecked
            val useNumbers = numbersCheckBox.isChecked
            val useSpecialChars = specialCharsCheckBox.isChecked

            generatePassword(size, useUppercase, useNumbers, useSpecialChars, description)


        }
    }
    private fun generatePassword(size: Int, useUppercase: Boolean, useNumbers: Boolean, useSpecialChars: Boolean, description: String) {
        val lowercaseLetters = "abcdefghijklmnopqrstuvwxyz"
        val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val specialChars = "!@#\$%^&*()-_=+[]{}|;:'\",.<>?/~"

        val allowedChars = StringBuilder(lowercaseLetters)
        if (useUppercase) allowedChars.append(uppercaseLetters)
        if (useNumbers) allowedChars.append(numbers)
        if (useSpecialChars) allowedChars.append(specialChars)

        val password = StringBuilder(size)
        val random = Random.Default
        for (i in 0 until size) {
            val randomIndex = random.nextInt(allowedChars.length)
            password.append(allowedChars[randomIndex])
        }

        val generatedPassword = password.toString()
        this.dao.insert(Senha(description, generatedPassword))
        showToast("Senha gerada: ${password}")
        val passwordListIntent = Intent(this, PasswordList::class.java)
        startActivity(passwordListIntent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}