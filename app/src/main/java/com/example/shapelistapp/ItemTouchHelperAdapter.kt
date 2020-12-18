package com.example.shapelistapp

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition : Int, toPosition: Int)

    fun onItemDelete(position : Int)
}

