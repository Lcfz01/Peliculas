package com.example.peliculas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        var login = findViewById<Button>(R.id.btnLogin)
        var crearCuenta = findViewById<Button>(R.id.btnRecupera)
        var recuperarContra = findViewById<Button>(R.id.btnCrearC)
        var email = findViewById<EditText>(R.id.email)
        var password = findViewById<EditText>(R.id.password)

        login.setOnClickListener {
            if (email.text.toString() != "" && password.text.toString() != ""){
                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                    task ->
                    if (task.isSuccessful){
                        startActivity(Intent(this,MenuPrincipal::class.java).putExtra("Saludos","Menú principal"))
                    }else{
                        Toast.makeText(this,"Error: "+task.exception!!.message.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                Toast.makeText(this,"Campos vacíos",Toast.LENGTH_LONG).show()

            }
        }
        crearCuenta.setOnClickListener {
            startActivity(Intent(this,CrearCuenta::class.java).putExtra("saludo","Crear cuenta"))

        }
        recuperarContra.setOnClickListener {
            startActivity(Intent(this,RecuperarContra::class.java).putExtra("saludo","Recuperar contraseña"))

        }
        }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null){
            sequenceOf(Toast.makeText(this,"No hay usuarios Autenticados",Toast.LENGTH_LONG).show())
        }
        else{
            startActivity(Intent(this,MenuPrincipal::class.java))
            finish()
        }
    }
    }
