package com.hnrzzin.projeto2b

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    // Cadastro de usuário
    fun cadastrarUsuario(
        nome: String,
        email: String,
        senha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid ?: return@addOnSuccessListener
                val usuario = mapOf(
                    "nome" to nome,
                    "email" to email,
                    "uid" to uid
                )

                database.getReference("usuarios").child(uid).setValue(usuario)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError(e.message ?: "Erro ao salvar") }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Erro ao cadastrar")
            }
    }

    // Login com e-mail e senha
    fun loginEmailSenha(
        email: String,
        senha: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { erro ->
                onError(erro.message ?: "Erro ao fazer login")
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun usuarioLogadoId(): String? {
        return auth.currentUser?.uid
    }
}