package com.rajkumar.datamaticsapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rajkumar.datamaticsapp.databinding.RowItemDonutListBinding
import com.rajkumar.datamaticsapp.model.Donut
import com.rajkumar.datamaticsapp.model.Topping

class DonutListAdapter(private val listener: IRecyclerClickListener<Array<Topping>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var donuts: ArrayList<Donut>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowItemDonutListBinding.inflate(layoutInflater)
        return MoveListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val donut = donuts?.get(i)
        donut?.let { (holder as MoveListViewHolder).bind(it) }
        (holder as MoveListViewHolder).binding.executePendingBindings()
    }


    override fun getItemCount(): Int {
        return if (donuts != null) {
            donuts!!.size
        } else 0
    }


    fun setDonuts(donuts: ArrayList<Donut>?) {
        this.donuts = donuts
        notifyDataSetChanged()
    }


    inner class MoveListViewHolder(val binding: RowItemDonutListBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(donut: Donut) {
            binding.model = donut
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            donuts?.get(adapterPosition)?.let { listener.onClick(it.name,it.topping.toTypedArray()) }
        }
    }


}
