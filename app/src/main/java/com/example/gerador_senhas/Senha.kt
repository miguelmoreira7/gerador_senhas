package com.example.gerador_senhas

import java.text.SimpleDateFormat

class Senha {
    var id: Int
    var description: String
    var senha: String
    var created_at: Long
    var updated_at: Long

    constructor(description: String, senha: String) {
        this.id = -1
        this.description = description
        this.senha = senha
        this.created_at = -1
        this.updated_at = -1
    }
    constructor(id: Int, description: String, senha: String, created_at: Long, updated_at: Long) {
        this.id = id
        this.description = description
        this.senha = senha
        this.created_at = created_at
        this.updated_at = updated_at
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        return "${id} ${description} ${senha} ${sdf.format(created_at)} ${sdf.format(updated_at)}"
    }
}