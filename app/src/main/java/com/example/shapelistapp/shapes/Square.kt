package com.example.shapelistapp.shapes

class Square(val edge: Double): Rectangle(edge, edge) {
    override fun toString(): String = "Square (a = $edge)"
}