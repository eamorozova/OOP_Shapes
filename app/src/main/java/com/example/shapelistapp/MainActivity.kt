package com.example.shapelistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shapelistapp.shapes.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.circle_dialog.view.*
import kotlinx.android.synthetic.main.rectangle_dialog.view.*
import kotlinx.android.synthetic.main.square_dialog.view.*
import kotlinx.android.synthetic.main.triangle_dialog.view.*
import java.io.*
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private lateinit var shapeRecyclerViewAdapter: ShapeRecyclerViewAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val gson: Gson = GsonBuilder().registerTypeAdapter(Shape::class.java, ShapeAdapter()).create()
    private val type: Type = object : TypeToken<List<Shape>>() {}.type
    private val path = "shapes.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        readJson()
        initPopupMenu()
    }

    override fun onDestroy() {
        super.onPause()
        saveToJson()
    }

    override fun onPause() {
        super.onPause()
        saveToJson()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            shapeRecyclerViewAdapter = ShapeRecyclerViewAdapter()
            adapter = shapeRecyclerViewAdapter
        }
        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(shapeRecyclerViewAdapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    private fun readJson() {
        val jsonString = ShapeReader().readFromFile(this, path)
        val data: MutableList<Shape> = gson.fromJson(jsonString, type)
        shapeRecyclerViewAdapter.submitList(data)
    }

    private fun saveToJson() {
        val data: MutableList<Shape> = shapeRecyclerViewAdapter.getList()
        val jsonString = gson.toJson(data, type)
        ShapeReader().writeToFile(this, path, jsonString)
    }

    private fun initPopupMenu() {
        val popupMenu = PopupMenu(this, fab)

        popupMenu.menu.add(Menu.NONE, 0, 0, "Circle")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Rectangle")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Square")
        popupMenu.menu.add(Menu.NONE, 3, 3, "Triangle")

        var shape: Shape
        popupMenu.setOnMenuItemClickListener {
            val builder = AlertDialog.Builder(this)
            when (it.itemId) {
                0 -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.circle_dialog, null)
                    builder.setView(dialogView).setTitle("Set circle radius")
                    val alertDialog = builder.show()
                    dialogView.circle_done_button.setOnClickListener {
                        alertDialog.dismiss()
                        val radius = dialogView.radius.text.toString().toDouble()
                        shape = Circle(radius)
                        shapeRecyclerViewAdapter.addShape(shape)
                        saveToJson()
                    }
                    dialogView.circle_cancel_button.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    true
                }
                1 -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.rectangle_dialog, null)
                    builder.setView(dialogView).setTitle("Set rectangle edges")
                    val alertDialog = builder.show()
                    dialogView.rectangle_done_button.setOnClickListener {
                        alertDialog.dismiss()
                        val a = dialogView.rectangle_edge_a.text.toString().toDouble()
                        val b = dialogView.rectangle_edge_b.text.toString().toDouble()
                        shape = Rectangle(a, b)
                        shapeRecyclerViewAdapter.addShape(shape)
                        saveToJson()
                    }
                    dialogView.rectangle_cancel_button.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    true
                }
                2 -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.square_dialog, null)
                    builder.setView(dialogView).setTitle("Set square edge")
                    val alertDialog = builder.show()
                    dialogView.square_done_button.setOnClickListener {
                        alertDialog.dismiss()
                        val a = dialogView.edge.text.toString().toDouble()
                        shape = Square(a)
                        shapeRecyclerViewAdapter.addShape(shape)
                        saveToJson()
                    }
                    dialogView.square_cancel_button.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    true
                }
                3 -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.triangle_dialog, null)
                    builder.setView(dialogView).setTitle("Set triangle edges")
                    val alertDialog = builder.show()
                    dialogView.triangle_done_button.setOnClickListener {
                        val a = dialogView.edge_a.text.toString().toDouble()
                        val b = dialogView.edge_b.text.toString().toDouble()
                        val c = dialogView.edge_c.text.toString().toDouble()
                        if (a + b > c && a + c > b && b + c > a) {
                            shape = Triangle(a, b, c)
                            shapeRecyclerViewAdapter.addShape(shape)
                            saveToJson()
                        } else {
                            Toast.makeText(this, "Incorrect triangle edges", Toast.LENGTH_SHORT).show()
                        }
                        alertDialog.dismiss()
                    }
                    dialogView.triangle_cancel_button.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    true
                }
                else -> false
            }
        }
        fab.setOnClickListener {
            popupMenu.show()
        }
    }
}



