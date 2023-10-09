package com.example.gerador_senhas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class PasswordList : AppCompatActivity() {

    private lateinit var passwordListView: ListView
    private lateinit var passwordAdapter: ArrayAdapter<String>
    private val passwordList = mutableListOf<Password>() // Agora é uma lista de objetos Password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_list)
        val password = intent.getStringExtra("newPassword")
        val desc = intent.getStringExtra("newDescription")
        val newPassword = Password(desc.toString(), password.toString())
        passwordList.add(newPassword)

        passwordListView = findViewById(R.id.listViewPasswords)
        passwordAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, passwordList.map { it.description })
        passwordListView.adapter = passwordAdapter
        passwordAdapter.notifyDataSetChanged()

        passwordListView.setOnItemClickListener(AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, PasswordEdit::class.java)
            val editPassword = passwordList[position]
            intent.putExtra("password", editPassword.password)
            intent.putExtra("desc", editPassword.description)
            startActivity(intent)
        })

        val fabAddPassword = findViewById<View>(R.id.fabAddPassword)
        fabAddPassword.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}