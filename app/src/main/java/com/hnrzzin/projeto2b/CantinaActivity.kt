package com.hnrzzin.projeto2b

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CantinaActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cantina)

        val alunoId = intent.getStringExtra("ALUNO_ID") ?: return // Pega o ID enviado
        val repo = CantinaRepository()
        val etValor = findViewById<EditText>(R.id.etValorRecarga)

        findViewById<Button>(R.id.btnRecarregar).setOnClickListener {
            val valor = etValor.text.toString().toDoubleOrNull() ?: 0.0
            if (valor > 0) {
                repo.adicionarSaldo(alunoId, valor) {
                    val textoSaldo =  findViewById<TextView>(R.id.tvSaldo)
                    textoSaldo.text = valor.toString()
                    Toast.makeText(this, "Recarga concluída!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        findViewById<Button>(R.id.btnComprarCoxinha).setOnClickListener {
            repo.realizarCompra(alunoId, "Coxinha", 5.50,
                onSuccess = { Toast.makeText(this, "Compra realizada!", Toast.LENGTH_SHORT).show() },
                onError = { erro -> Toast.makeText(this, erro, Toast.LENGTH_LONG).show() }
            )
        }


    }
}