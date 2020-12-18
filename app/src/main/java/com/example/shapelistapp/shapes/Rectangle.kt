package com.example.shapelistapp.shapes

import kotlin.IllegalArgumentException

open class Rectangle(val height: Double, val width: Double): Shape {
    init {
        if (height <= 0 || width <= 0)
            throw IllegalArgumentException("Error. Parameters are incorrect.")
    }

    override fun calcArea(): Double = height * width

    override fun calcPerimeter(): Double = 2 * (height + width)

    override fun toString(): String = "Rectangle (a = $height, b = $width)"
}