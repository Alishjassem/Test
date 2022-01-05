package com.magma.aomlati.utils.listeners

interface RecyclerItemListener<T> {

    fun onItemClicked(item : T, index : Int)
}