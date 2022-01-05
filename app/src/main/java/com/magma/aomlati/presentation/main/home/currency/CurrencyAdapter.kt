package com.magma.aomlati.presentation.main.home.currency

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.magma.aomlati.databinding.ItemCollapseCurrencyBinding
import com.magma.aomlati.databinding.ItemExpandCurrencyBinding
import com.magma.aomlati.model.Currency
import com.magma.aomlati.utils.Const
import com.magma.aomlati.utils.listeners.RecyclerItemListener

class CurrencyAdapter :
    ListAdapter<Currency, RecyclerView.ViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    //var dynamicView = false

    private lateinit var listener: RecyclerItemListener<Currency>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return if (viewType == Const.TYPE_CURRENCY_EXPAND) {
            val binding = ItemExpandCurrencyBinding.inflate(layoutInflater, parent, false)
            MyViewHolder(binding)
        } else {
            val binding = ItemCollapseCurrencyBinding.inflate(layoutInflater, parent, false)
            MyCollapseViewHolder(binding)
        }
    }

    fun setListener(listener: RecyclerItemListener<Currency>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (getItemViewType(position) == Const.TYPE_CURRENCY_EXPAND) {
            val expandHolder = holder as CurrencyAdapter.MyViewHolder
            expandHolder.bind(item)
        } else {
            val collapseHolder = holder as CurrencyAdapter.MyCollapseViewHolder
            collapseHolder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isFavorite)
            Const.TYPE_CURRENCY_EXPAND
        else Const.TYPE_CURRENCY_COLLAPSE
    }

    inner class MyViewHolder(val binding: ItemExpandCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Currency) {
            binding.item = item

            val values = arrayListOf<Double>()
            item.latest5?.let {
                for (value in item.latest5!!){
                    value.value?.let {
                        values.add(value.value!!)
                    }
                }
            }
            val aaChartView = binding.chartView
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Line)
                .axesTextColor("#81FF8A")
                .colorsTheme(arrayOf("#81FF8A"))
                .dataLabelsEnabled(false)
                .xAxisLabelsEnabled(false)
                .yAxisLabelsEnabled(false)
                .yAxisVisible(false)
                .legendEnabled(false)
                .touchEventEnabled(true)
                .backgroundColor("#4F709C")
                .series(arrayOf(
                    AASeriesElement()
                        .data(values.toArray()),
                ))
            //The chart view object calls the instance object of AAChartModel and draws the final graphic
            aaChartView.aa_drawChartWithChartModel(aaChartModel)

            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(getItem(bindingAdapterPosition), bindingAdapterPosition)
        }
    }

    inner class MyCollapseViewHolder(val binding: ItemCollapseCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Currency) {
            binding.item = item

            val values = arrayListOf<Double>()
            item.latest5?.let {
                for (value in item.latest5!!){
                    value.value?.let {
                        values.add(value.value!!)
                    }
                }
            }
            val aaChartView = binding.chartView
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Line)
                .axesTextColor("#81FF8A")
                .colorsTheme(arrayOf("#81FF8A"))
                .dataLabelsEnabled(false)
                .xAxisLabelsEnabled(false)
                .yAxisLabelsEnabled(false)
                .yAxisVisible(false)
                .legendEnabled(false)
                .touchEventEnabled(true)
                .backgroundColor("#4F709C")
                .series(arrayOf(
                    AASeriesElement()
                        .data(values.toArray()),
                ))
            //The chart view object calls the instance object of AAChartModel and draws the final graphic
            aaChartView.aa_drawChartWithChartModel(aaChartModel)

            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
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

