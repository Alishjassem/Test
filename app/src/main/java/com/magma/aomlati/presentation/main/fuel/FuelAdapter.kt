package com.magma.aomlati.presentation.main.fuel

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.magma.aomlati.databinding.ItemFuelBinding
import com.magma.aomlati.model.Fuel
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class FuelAdapter :
    ListAdapter<Fuel, FuelAdapter.MyViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    //var dynamicView = false

    private lateinit var listener: RecyclerItemListener<Fuel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemFuelBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    fun setListener(listener: RecyclerItemListener<Fuel>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(val binding: ItemFuelBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Fuel) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(getItem(bindingAdapterPosition), bindingAdapterPosition)
        }
    }

    class NewsDiffCallBacks : DiffUtil.ItemCallback<Fuel>() {
        override fun areItemsTheSame(
            oldItem: Fuel,
            newItem: Fuel
        ): Boolean {
            return oldItem._id == newItem._id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Fuel,
            newItem: Fuel
        ): Boolean {
            return newItem.name == oldItem.name
                    && newItem.symbol == oldItem.symbol
        }
    }


    override fun getItemCount() = currentList.size

}

