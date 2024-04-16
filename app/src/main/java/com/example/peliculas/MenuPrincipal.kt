package com.example.peliculas

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MenuPrincipal : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    val myRef = database.getReference("peliculas")
    lateinit var peliculas: ArrayList<Pelicula>

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.perfil -> true
                    R.id.logout -> {
                        auth.signOut()
                        startActivity(Intent(this@MenuPrincipal,Login::class.java))
                        finish()
                        true
                    }
                    else -> false
                }
            }
            
        })
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                 peliculas = ArrayList<Pelicula>()
                val value = snapshot.value.toString()
                Log.d(TAG, "Value is: " + value)
                snapshot.children.forEach{
                    item ->
                    var pelicula = Pelicula(item.child("nombre").value.toString(),
                        item.child("genero").value.toString(),
                        item.child("anio").value.toString(),
                        item.key.toString())
                    peliculas.add(pelicula)
                }
                llenalista()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
        val lista = findViewById<ListView>(R.id.lista)
        lista.setOnItemClickListener() {
            parent, view, position, id ->
            Toast.makeText(this@MenuPrincipal,peliculas[position].nombre,Toast.LENGTH_LONG).show()
            startActivity(Intent(this,EditaPelicula::class.java)
                .putExtra("nombre",peliculas[position].nombre.toString())
                .putExtra("genero",peliculas[position].genero.toString())
                .putExtra("anio",peliculas[position].anio.toString())
                .putExtra("ids",peliculas[position].ids))


        }
        val btnAgregarPeli = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnAnadirPeli)
        btnAgregarPeli.setOnClickListener {
            var peliVacia = PeliCampos("EditarNombre","EditarGenero","EditarAño")
            myRef.push().setValue(peliVacia)
        }
    }


    fun llenalista() {
        var adaptador = PeliAdapter(this,peliculas)
        var lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adaptador;

    }
}