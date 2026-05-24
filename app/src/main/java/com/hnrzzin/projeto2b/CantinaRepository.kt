package com.hnrzzin.projeto2b

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CantinaRepository {

    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // 1. Cadastrar Aluno (Conforme PDF)
    fun cadastrarAluno(nome: String, turma: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val responsavelId = auth.currentUser?.uid ?: return
        val alunoId = database.getReference("alunos").push().key ?: return

        val aluno = Aluno(alunoId, nome, turma, responsavelId, 0.0)

        database.getReference("alunos").child(alunoId).setValue(aluno)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Erro ao cadastrar aluno") }
    }

    // 2. Adicionar Saldo (Recarga)
    fun adicionarSaldo(alunoId: String, valorRecarga: Double, onSuccess: () -> Unit) {
        val alunoRef = database.getReference("alunos").child(alunoId)

        alunoRef.get().addOnSuccessListener { snapshot ->
            val saldoAtual = snapshot.child("saldo").getValue(Double::class.java) ?: 0.0
            val novoSaldo = saldoAtual + valorRecarga

            alunoRef.child("saldo").setValue(novoSaldo).addOnSuccessListener {
                // Salva histórico de recarga
                val recargaId = database.getReference("recargas").push().key ?: ""
                database.getReference("recargas").child(recargaId).setValue(
                    mapOf("alunoId" to alunoId, "valor" to valorRecarga)
                )
                onSuccess()
            }
        }
    }

    // 3. Comprar Produto
    fun realizarCompra(alunoId: String, produtoNome: String, valorProduto: Double, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val alunoRef = database.getReference("alunos").child(alunoId)

        alunoRef.get().addOnSuccessListener { snapshot ->
            val saldoAtual = snapshot.child("saldo").getValue(Double::class.java) ?: 0.0

            if (saldoAtual >= valorProduto) {
                val novoSaldo = saldoAtual - valorProduto
                alunoRef.child("saldo").setValue(novoSaldo).addOnSuccessListener {
                    // Salva histórico de compra
                    val compraId = database.getReference("compras").push().key ?: ""
                    database.getReference("compras").child(compraId).setValue(
                        mapOf("alunoId" to alunoId, "produto" to produtoNome, "valor" to valorProduto)
                    )
                    onSuccess()
                }
            } else {
                onError("Saldo insuficiente")
            }
        }
    }
    // Coloque isso dentro da classe CantinaRepository
    fun buscarMeusAlunos(onSuccess: (List<Aluno>) -> Unit, onError: (String) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        // Busca na tabela "alunos" onde o "responsavelId" seja igual ao UID logado
        database.getReference("alunos")
            .orderByChild("responsavelId")
            .equalTo(uid)
            .get()
            .addOnSuccessListener { snapshot ->
                val listaAlunos = mutableListOf<Aluno>()
                for (alunoSnapshot in snapshot.children) {
                    val aluno = alunoSnapshot.getValue(Aluno::class.java)
                    if (aluno != null) {
                        listaAlunos.add(aluno)
                    }
                }
                onSuccess(listaAlunos)
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Erro ao buscar alunos")
            }
    }
}