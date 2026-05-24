package com.hnrzzin.projeto2b

data class Aluno(
    val id: String = "",
    val nome: String = "",
    val turma: String = "",
    val responsavelId: String = "",
    val saldo: Double = 0.0
)

data class Produto(
    val id: String = "",
    val nome: String = "",
    val preco: Double = 0.0
)