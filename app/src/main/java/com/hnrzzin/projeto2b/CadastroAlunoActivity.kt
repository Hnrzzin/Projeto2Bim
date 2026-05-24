package com.hnrzzin.projeto2b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastroAlunoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_aluno)

        val repo = CantinaRepository()
        val etNome = findViewById<EditText>(R.id.etNomeAluno)
        val etTurma = findViewById<EditText>(R.id.etTurmaAluno)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAluno)

        btnSalvar.setOnClickListener {
            repo.cadastrarAluno(
                etNome.text.toString(),
                etTurma.text.toString(),
                onSuccess = {
                    Toast.makeText(this, "Aluno salvo!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela e volta para a anterior
                },
                onError = { erro -> Toast.makeText(this, erro, Toast.LENGTH_LONG).show() }
            )
        }
    }
}