package com.example.peliculas

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.database.database

class EditaPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edita_pelicula)

        val database = Firebase.database
        val myRef = database.getReference("peliculas")

        var nombre = findViewById<EditText>(R.id.txtNombre)
        var genero = findViewById<EditText>(R.id.txtGenero)
        var anio = findViewById<EditText>(R.id.txtAnio)

        var editar = findViewById<Button>(R.id.btnEditar)
        var eliminar = findViewById<Button>(R.id.btnEliminar)

        var imagen = findViewById<ImageView>(R.id.imageEditar)

        val parametros = intent.extras
        nombre.setText(parametros?.getCharSequence("nombre").toString())
        genero.setText(parametros?.getCharSequence("genero").toString())
        anio.setText(parametros?.getCharSequence("anio").toString())

       if(parametros?.getCharSequence("genero").toString() == "Terror"){
            imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fear))
        }
       else if(parametros?.getCharSequence("genero").toString() == "Aventura"){
            imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.adventurer))
        }

        val btnEditaPelicula = findViewById<Button>(R.id.btnEditar)

        btnEditaPelicula.setOnClickListener {
            var peliculaEdita = PeliCampos(nombre.text.toString(),genero.text.toString(),anio.text.toString())
            myRef.child(parametros?.getCharSequence("ids").toString()).setValue(peliculaEdita).addOnCompleteListener{
            task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"Pelicula editada correctamente",Toast.LENGTH_LONG).show()
                    if(genero.text.toString() == "Terror"){
                        imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fear))
                    }
                    else if(genero.text.toString() == "Aventura"){
                        imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.adventurer))
                    }
                }
                else{
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
                }
            }
        }

        val btnEliminarPelicula = findViewById<Button>(R.id.btnEliminar)
        btnEliminarPelicula.setOnClickListener {
            val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(this)
            builder.setMessage("¿Está seguro de eliminar esta película?")
                .setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                    dialog,id->
                    myRef.child(parametros?.getCharSequence("id").toString()).removeValue().addOnCompleteListener{
                        task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Pelicula editada correctamente",Toast.LENGTH_LONG).show()
                            if(genero.text.toString() == "Terror"){
                                imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.fear))
                            }
                            else if(genero.text.toString() == "Aventura"){
                                imagen.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.adventurer))
                            }
                        }
                        else{
                            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()

                        }
                    }
                })
                .setNegativeButton("cancelar",DialogInterface.OnClickListener { dialog, id ->  })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
        }
    }
}