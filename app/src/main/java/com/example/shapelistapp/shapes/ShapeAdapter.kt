package com.example.shapelistapp.shapes

import com.google.gson.*
import java.lang.reflect.Type

class ShapeAdapter : JsonDeserializer<Shape>, JsonSerializer<Shape> {
    private val CLASSNAME = "Shape"
    private val DATA = "Data"

    private fun getObjectClass(className: String): Class<*> {
        try {
            return Class.forName("com.example.shapelistapp.shapes.${className}")
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e.message)
        }
    }

    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): Shape {
        val jsonObject = jsonElement.asJsonObject
        val prim = jsonObject.get(CLASSNAME)
        val className = prim.asString
        val klass = getObjectClass(className)
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass)
    }

    override fun serialize(
        jsonElement: Shape,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty(CLASSNAME, jsonElement.javaClass.simpleName)
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement))
        return jsonObject
    }
}