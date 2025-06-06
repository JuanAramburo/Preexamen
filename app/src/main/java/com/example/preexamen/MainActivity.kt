package com.example.preexamen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {

    private lateinit var txtEmpleado : EditText
    private lateinit var btnEntrar : Button
    private lateinit var btnSalir : Button

    fun iniciarComponentes(){
        txtEmpleado = findViewById(R.id.txtEmpleado) as EditText
        btnEntrar = findViewById(R.id.btnEntrar) as Button
        btnSalir = findViewById(R.id.btnSalir) as Button
    }

    fun eventosBotones(){
        btnEntrar.setOnClickListener(View.OnClickListener {
            if (txtEmpleado.text.toString().isEmpty()){
                Toast.makeText(this,"Falto capturar el nombre",Toast.LENGTH_SHORT).show()
            } else{
                val intent = Intent(this,ReciboNominaActivity::class.java)
                intent.putExtra("Cliente",txtEmpleado.text.toString())
                startActivity(intent)
            }
        })
        btnSalir.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Nomina")
            builder.setMessage("¿Deseas salir de la aplicacion")

            builder.setPositiveButton("Aceptar"){dialog, which ->
                finish()
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    "Quiza", Toast.LENGTH_SHORT).show()
            }
            builder.show()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        iniciarComponentes()
        eventosBotones()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}