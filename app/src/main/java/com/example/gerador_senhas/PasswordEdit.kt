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

class PasswordEdit : AppCompatActivity() {
    private lateinit var dao: SenhaDAO

    private lateinit var descriptionEditText: EditText
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var uppercaseCheckBox: CheckBox
    private lateinit var numbersCheckBox: CheckBox
    private lateinit var specialCharsCheckBox: CheckBox
    private lateinit var alterarButton: Button
    private lateinit var excluirButton: Button
    private lateinit var cancelButton: Button
    private lateinit var passwordToEdit: Senha

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_edit)
        val id = intent.getIntExtra("id", 0)

        this.dao = SenhaDAO(this)
        val passwordToEdit = this.dao.find(id)

        descriptionEditText = findViewById(R.id.editTextDescription)
        sizeSeekBar = findViewById(R.id.seekBarSize)
        uppercaseCheckBox = findViewById(R.id.checkBoxUpperCase)
        numbersCheckBox = findViewById(R.id.checkBoxNumbers)
        specialCharsCheckBox = findViewById(R.id.checkBoxSpecialChars)
        alterarButton = findViewById(R.id.buttonAlterar)
        excluirButton = findViewById(R.id.buttonExcluir)
        cancelButton = findViewById(R.id.buttonCancelar)

        //val selectedDescription = intent.getStringExtra("id")

        descriptionEditText.setText(passwordToEdit?.description)


        alterarButton.setOnClickListener {

            val updatedDescription = descriptionEditText.text.toString()
            val updatedSize = sizeSeekBar.progress + 1
            val useUppercase = uppercaseCheckBox.isChecked
            val useNumbers = numbersCheckBox.isChecked
            val useSpecialChars = specialCharsCheckBox.isChecked
            generatePassword(updatedSize, useUppercase, useNumbers, useSpecialChars)


            showToast("Senha atualizada com sucesso")
            val passwordListIntent = Intent(this, PasswordList::class.java)
            startActivity(passwordListIntent)
        }

        excluirButton.setOnClickListener {
            val idToDelete = this.passwordToEdit.id
            this.dao.delete(idToDelete)

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
    ) {
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
            this.dao.update(Senha(descriptionEditText.text.toString(), password.toString()))
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}