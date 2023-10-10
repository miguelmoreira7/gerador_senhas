package com.example.gerador_senhas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.gerador_senhas.PasswordList.Companion.passwordList
import kotlin.random.Random

class PasswordEdit : AppCompatActivity() {

    private lateinit var descriptionEditText: EditText
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var uppercaseCheckBox: CheckBox
    private lateinit var numbersCheckBox: CheckBox
    private lateinit var specialCharsCheckBox: CheckBox
    private lateinit var alterarButton: Button
    private lateinit var excluirButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_edit)

        descriptionEditText = findViewById(R.id.editTextDescription)
        sizeSeekBar = findViewById(R.id.seekBarSize)
        uppercaseCheckBox = findViewById(R.id.checkBoxUpperCase)
        numbersCheckBox = findViewById(R.id.checkBoxNumbers)
        specialCharsCheckBox = findViewById(R.id.checkBoxSpecialChars)
        alterarButton = findViewById(R.id.buttonAlterar)
        excluirButton = findViewById(R.id.buttonExcluir)
        cancelButton = findViewById(R.id.buttonCancelar)

        val selectedDescription = intent.getStringExtra("desc")

        descriptionEditText.setText(selectedDescription)


        alterarButton.setOnClickListener {

            val updatedDescription = descriptionEditText.text.toString()
            val updatedSize = sizeSeekBar.progress + 1
            val useUppercase = uppercaseCheckBox.isChecked
            val useNumbers = numbersCheckBox.isChecked
            val useSpecialChars = specialCharsCheckBox.isChecked

            val senhaAtualizada = passwordList.find { it.description == selectedDescription }


            senhaAtualizada?.apply {
                description = updatedDescription
                password = generatePassword(updatedSize, useUppercase, useNumbers, useSpecialChars)
            }
            showToast("Senha atualizada com sucesso")
            val passwordListIntent = Intent(this, PasswordList::class.java)
            startActivity(passwordListIntent)
        }

        excluirButton.setOnClickListener {
            val senhaExcluida = passwordList.find { it.description == selectedDescription }

            if (senhaExcluida != null) {
                passwordList.remove(senhaExcluida)
                showToast("Senha excluída com sucesso")
            } else {
                showToast("Senha não encontrada para exclusão")
            }
            val passwordListIntent = Intent(this, PasswordList::class.java)
            startActivity(passwordListIntent)

        }

        cancelButton.setOnClickListener {
            val voltar = Intent(this, PasswordList::class.java)
            startActivity(voltar)
        }

    }
    private fun generatePassword(
        size: Int,
        useUppercase: Boolean,
        useNumbers: Boolean,
        useSpecialChars: Boolean
    ): String {
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

        return password.toString()

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}