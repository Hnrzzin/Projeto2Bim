package com.hnrzzin.projeto2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlunoAdapter(
    private val lista: List<Aluno>,
    private val aoClicarNoAluno: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    class AlunoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNomeAluno)
        val tvSaldo: TextView = view.findViewById(R.id.tvSaldoAluno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = lista[position]
        holder.tvNome.text = aluno.nome
        holder.tvSaldo.text = "Saldo: R$ ${aluno.saldo}"

        // Quando clicar neste aluno, dispara a função passando ele
        holder.itemView.setOnClickListener { aoClicarNoAluno(aluno) }
    }

    override fun getItemCount() = lista.size
}