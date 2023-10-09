package com.example.gerador_senhas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.view.View
import android.widget.TextView
import android.widget.Toast

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
        val selectedSize = intent.getIntExtra("selectedSize", 8) // Tamanho padrão de 8 caracteres

        descriptionEditText.setText(selectedDescription)
        sizeSeekBar.progress = selectedSize - 1 // Ajuste o valor no SeekBar

        alterarButton.setOnClickListener {
            // Recupere os valores atualizados dos campos
            val updatedDescription = descriptionEditText.text.toString()
            val updatedSize = sizeSeekBar.progress + 1
            val useUppercase = uppercaseCheckBox.isChecked
            val useNumbers = numbersCheckBox.isChecked
            val useSpecialChars = specialCharsCheckBox.isChecked

            // Execute a lógica de atualização da senha com os valores atualizados
            // Você pode implementar essa lógica conforme necessário

            // Exiba uma mensagem de sucesso
            showToast("Senha atualizada com sucesso")
        }

        excluirButton.setOnClickListener {
            // Execute a lógica de exclusão da senha
            // Você pode implementar essa lógica conforme necessário

            // Exiba uma mensagem de sucesso ou confirmação de exclusão
            showToast("Senha excluída com sucesso")
        }

        cancelButton.setOnClickListener {
            val voltar = Intent(this, PasswordList::class.java)
            startActivity(voltar)
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}