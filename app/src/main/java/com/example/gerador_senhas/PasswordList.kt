package com.example.gerador_senhas

import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class PasswordList : AppCompatActivity() {
    private lateinit var dao: SenhaDAO

    private lateinit var passwordListView: ListView
    private lateinit var passwordAdapter: ArrayAdapter<String>
    private lateinit var passwordList: List<Senha>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_list)

        this.dao = SenhaDAO(this)
        this.passwordList = this.dao.select()

        passwordListView = findViewById(R.id.listViewPasswords)
        passwordAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, passwordList.map { it.description })
        passwordListView.adapter = passwordAdapter
        passwordAdapter.notifyDataSetChanged()

        passwordListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PasswordEdit::class.java)
            val editPassword = passwordList[position]
            intent.putExtra("id", editPassword.id)

            startActivity(intent)
        }

        val fabAddPassword = findViewById<View>(R.id.fabAddPassword)
        fabAddPassword.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        
        passwordListView.setOnItemLongClickListener { _, _, position, _ ->
            val selectedPassword = passwordList[position]
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Senha", selectedPassword.senha)
            clipboard.setPrimaryClip(clip)
            showToast("Senha copiada para a área de transferência")
            true
        }
    }

    private fun showToast(s: String) {

    }
}