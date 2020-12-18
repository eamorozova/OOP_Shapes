package com.example.shapelistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shapelistapp.shapes.Shape
import kotlinx.android.synthetic.main.shape_item.view.*
import java.util.*


class ShapeRecyclerViewAdapter: RecyclerView.Adapter<ShapeRecyclerViewAdapter.ShapesViewHolder>(), ItemTouchHelperAdapter {

    private var items: MutableList<Shape> = mutableListOf()

    class ShapesViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val shapeInfo: TextView = itemView.shape_text

        fun bind(shape: Shape) {
            shapeInfo.text = shape.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShapesViewHolder {
        return ShapesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shape_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShapesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onItemDelete(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun submitList(shapesList: MutableList<Shape>) {
        items = shapesList
    }

    fun addShape(shape: Shape) {
        items.add(shape)
    }

    fun getList(): MutableList<Shape> {
        return items
    }
}