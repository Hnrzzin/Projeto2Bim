package com.hnrzzin.projeto2b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val authRepo = AuthRepository()

        // 1. Vinculando as Views do XML (Confirme se os IDs estão iguais ao seu XML)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Se o seu botão de ir para cadastro for um TextView, troque 'Button' por 'TextView'
        val btnIrCadastro = findViewById<Button>(R.id.btnIrParaCadastro)

        // ==========================================
        // AÇÃO 1: Botão de ir para a Tela de Cadastro
        // ==========================================
        btnIrCadastro.setOnClickListener {
            // O Intent é o "carteiro" que te leva de uma tela para outra
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // ==========================================
        // AÇÃO 2: Botão de Fazer Login
        // ==========================================
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                authRepo.loginEmailSenha(
                    email, senha,
                    onSuccess = {
                        // Deu certo no Firebase! Vamos para a MainActivity
                        Toast.makeText(this, "Login com sucesso!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Fecha a tela de login para o usuário não voltar nela
                    },
                    onError = { erro ->
                        Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
                    }
                )
            } else {
                Toast.makeText(this, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}