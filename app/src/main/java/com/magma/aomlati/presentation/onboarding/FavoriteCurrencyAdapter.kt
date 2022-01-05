package com.magma.aomlati.presentation.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.magma.aomlati.databinding.ItemFavoriteCurrencyBinding
import com.magma.aomlati.databinding.ItemSettingCurrencyBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class FavoriteCurrencyAdapter :
    ListAdapter<Currency, RecyclerView.ViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    private var type = Const.TYPE_CURRENCY_FAVORITE
    //var dynamicView = false

    private lateinit var listener: RecyclerItemListener<Currency>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return if (type == Const.TYPE_CURRENCY_SETTING_FAVORITE) {
            val binding = ItemSettingCurrencyBinding.inflate(layoutInflater, parent, false)
            MySettingViewHolder(binding)
        } else {
            val binding = ItemFavoriteCurrencyBinding.inflate(layoutInflater, parent, false)
            MyViewHolder(binding)
        }
    }

    fun setListener(listener: RecyclerItemListener<Currency>) {
        this.listener = listener
    }

    fun setType(type: Int) {
        this.type = type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (type == Const.TYPE_CURRENCY_SETTING_FAVORITE) {
            val settingHolder = holder as MySettingViewHolder
            settingHolder.bind(item)
        } else {
            val favoriteHolder = holder as MyViewHolder
            favoriteHolder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    inner class MyViewHolder(val binding: ItemFavoriteCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Currency) {
            binding.item = item
            /*val noSpace = binding.root.context.resources.getDimension(R.dimen.no_space).toInt()
            val space1Dp = binding.root.context.resources.getDimension(R.dimen.vertical_1dp).toInt()
            if (item.isFavorite)
                binding.cardParent.strokeWidth = space1Dp
            else binding.cardParent.strokeWidth = noSpace
            binding.checkFavorite.isChecked = item.isFavorite*/
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
            //val noSpace = binding.root.context.resources.getDimension(R.dimen.no_space).toInt()
            //val space1Dp = binding.root.context.resources.getDimension(R.dimen.vertical_1dp).toInt()
            binding.checkFavorite.setOnCheckedChangeListener { _, checked ->
                currentList[bindingAdapterPosition].isFavorite = checked
                /*binding.root.post {
                    notifyDataSetChanged()
                }*/
                /*if (checked)
                    binding.cardParent.strokeWidth = space1Dp
                else binding.cardParent.strokeWidth = noSpace*/
                //notifyItemChanged(bindingAdapterPosition)
            }
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(getItem(bindingAdapterPosition), bindingAdapterPosition)
        }
    }

    inner class MySettingViewHolder(val binding: ItemSettingCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Currency) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
            binding.checkFavorite.setOnCheckedChangeListener { _, checked ->
                currentList[bindingAdapterPosition].isFavorite = checked
            }
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(getItem(bindingAdapterPosition), bindingAdapterPosition)
        }
    }

    class NewsDiffCallBacks : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(
            oldItem: Currency,
            newItem: Currency
        ): Boolean {
            return oldItem._id == newItem._id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Currency,
            newItem: Currency
        ): Boolean {
            return newItem.name == oldItem.name
                    && newItem.symbol == oldItem.symbol
        }
    }


    override fun getItemCount() = currentList.size

}

