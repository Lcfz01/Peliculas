package com.example.peliculas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class PeliAdapter (private val context: Activity, private val arrayList: ArrayList<Pelicula>):
ArrayAdapter<Pelicula>(context,R.layout.item,arrayList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item,null)

        view.findViewById<TextView>(R.id.nombrePeli).text = arrayList[position].nombre
        view.findViewById<TextView>(R.id.generoPeli).text = arrayList[position].genero
        view.findViewById<TextView>(R.id.anioPeli).text = arrayList[position].anio
        if(arrayList[position].genero == "Terror"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.fear))

        }
        if(arrayList[position].genero == "Aventura"){
            view.findViewById<ImageView>(R.id.imagenPeli).setImageDrawable(ContextCompat.getDrawable(context,R.drawable.adventurer))
        }
    return view
    }
}