package com.magma.aomlati.utils.listeners

interface RecyclerItemFoodListener<T> {

    fun onItemClicked(item : T, index : Int)

    fun onIncClicked(item : T, index : Int)

    fun onDecClicked(item : T, index : Int)

    fun onAddClicked(item : T, index : Int)
}