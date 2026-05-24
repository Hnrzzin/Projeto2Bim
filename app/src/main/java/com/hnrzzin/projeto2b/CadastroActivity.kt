package com.hnrzzin.projeto2b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity() {

    private val authRepo = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val etNome = findViewById<EditText>(R.id.etNome)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnCadastro = findViewById<Button>(R.id.btnCadastrar)

        btnCadastro.setOnClickListener {
            val name = etNome.text.toString()
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()

            if (email.isNotEmpty() && name.isNotEmpty() && senha.isNotEmpty()  ) {
                authRepo.cadastrarUsuario(
                    name,
                    email,
                    senha,
                    onSuccess = {
                        Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                        // Aqui você redirecionaria para a tela principal (ex: Intent...)
                    },
                    onError = { mensagem ->
                        Toast.makeText(this, "Erro: $mensagem", Toast.LENGTH_LONG).show()
                    }
                )
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}