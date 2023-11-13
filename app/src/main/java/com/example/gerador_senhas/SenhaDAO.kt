package com.example.gerador_senhas

import android.content.ContentValues
import android.content.Context
import java.util.Calendar

class SenhaDAO {
    private val banco: BancoHelper

    constructor(context: Context) {
        this.banco = BancoHelper(context)
    }

    fun insert(senha: Senha) {
        val dataHora = Calendar.getInstance().timeInMillis
        val cv = ContentValues()
        cv.put("description", senha.description)
        cv.put("senha", senha.senha)
        cv.put("created_at", dataHora)
        cv.put("updated_at", dataHora)
        this.banco.writableDatabase.insert("senhas", null, cv)
    }

    fun select(): List<Senha> {
        var lista = ArrayList<Senha>()
        val colunas = arrayOf("id", "description", "senha", "created_at", "updated_at")
        val c = this.banco.readableDatabase.query("senhas", colunas, null, null, null, null,null)

        c.moveToFirst()
        for (i in 1 .. c.count) {
            val id = c.getInt(0)
            val description = c.getString(1)
            val senha = c.getString(2)
            val created_at = c.getLong(3)
            val updated_at = c.getLong(4)
            val senha_ = Senha(id, description, senha, created_at, updated_at)
            lista.add(senha_)
            c.moveToNext()
        }
        return lista
    }

    fun find (id: Int): Senha? {
        val colunas = arrayOf("id", "description", "senha", "created_at", "updated_at")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        val c = this.banco.readableDatabase.query("senhas", colunas, where, pWhere, null, null,null)

        c.moveToFirst()
        if (c.count == 1) {
            val id = c.getInt(0)
            val description = c.getString(1)
            val senha = c.getString(2)
            val created_at = c.getLong(3)
            val updated_at = c.getLong(4)
            return Senha(id, description, senha, created_at, updated_at)
        }
        return null
    }

    fun delete (id: Int) {
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("senhas", where, pWhere)
    }

    fun update (senha: Senha) {
        val where = "id = ?"
        val pWhere = arrayOf(senha.id.toString())
        val cv = ContentValues()
        cv.put("description", senha.description)
        cv.put("senha", senha.senha)
        cv.put("updated_at", Calendar.getInstance().timeInMillis)
        this.banco.writableDatabase.update("senhas",cv , where, pWhere)
    }
}

