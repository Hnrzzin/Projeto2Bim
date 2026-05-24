package com.hnrzzin.projeto2b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val cantinaRepo = CantinaRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        findViewById<Button>(R.id.btnIrCadastroAluno).setOnClickListener {
            startActivity(Intent(this, CadastroAlunoActivity::class.java))
        }

        findViewById<Button>(R.id.btnSair).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarListaDeAlunos()
    }

    private fun carregarListaDeAlunos() {
        cantinaRepo.buscarMeusAlunos(
            onSuccess = { listaDeAlunos ->
                val rvAlunos = findViewById<RecyclerView>(R.id.rvAlunos)
                rvAlunos.layoutManager = LinearLayoutManager(this)

                // Aqui está a parte crucial: o clique que abre a Cantina
                rvAlunos.adapter = AlunoAdapter(listaDeAlunos) { alunoClicado ->
                    val intent = Intent(this, CantinaActivity::class.java)
                    intent.putExtra("ALUNO_ID", alunoClicado.id)
                    startActivity(intent)
                }
            },
            onError = { erro ->
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
            }
        )
    }
}