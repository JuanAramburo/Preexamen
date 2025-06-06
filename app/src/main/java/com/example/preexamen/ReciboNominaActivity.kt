package com.example.preexamen

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.abs

class ReciboNominaActivity : AppCompatActivity() {

    private lateinit var txtEmpleado : TextView
    private lateinit var lblRecibo : TextView
    private lateinit var horasT : EditText
    private lateinit var horasE : EditText
    private lateinit var Pue1 : RadioButton
    private lateinit var Pue2 : RadioButton
    private lateinit var Pue3 : RadioButton
    private lateinit var subTot : TextView
    private lateinit var impuesto : TextView
    private lateinit var totalPag : TextView
    private lateinit var btnCalcular : Button
    private lateinit var btnLimpiar : Button
    private lateinit var btnRegresar : Button

    public fun iniciarComponentes(){
        txtEmpleado = findViewById(R.id.txtEmpleado) as TextView
        lblRecibo = findViewById(R.id.lblRecibo) as TextView
        horasT = findViewById(R.id.horasT) as EditText
        horasE = findViewById(R.id.horasE) as EditText
        Pue1 = findViewById(R.id.Pue1) as RadioButton
        Pue2 = findViewById(R.id.Pue2) as RadioButton
        Pue3 = findViewById(R.id.Pue3) as RadioButton
        subTot = findViewById(R.id.subTot) as TextView
        impuesto = findViewById(R.id.impuesto) as TextView
        totalPag = findViewById(R.id.totalPag) as TextView

        btnCalcular = findViewById(R.id.btnCalcular) as Button
        btnLimpiar = findViewById(R.id.btnLimpiar) as Button
        btnRegresar = findViewById(R.id.btnRegresar) as Button

        var strEmpleado: String = intent.getStringExtra("Cliente").toString()
        txtEmpleado.text = strEmpleado.toString()

        var recibo: Int = abs(ReciboNomina().generarRecibo())
        lblRecibo.text = "Recibo: " + recibo.toString()
    }

    public fun eventosClic(){
        btnCalcular.setOnClickListener(View.OnClickListener {
            var reciboNomina = ReciboNomina()
            // validar
            if (horasT.text.toString().isEmpty() || horasE.text.toString().isEmpty()){
                Toast.makeText(this,"Falto capturar las horas trabajadas", Toast.LENGTH_SHORT).show()
            } else{
                reciboNomina.Nombre = txtEmpleado.text.toString()
                reciboNomina.horasTrabNormal = horasT.text.toString().toFloat()
                reciboNomina.horasTrabExtras = horasE.text.toString().toFloat()

                reciboNomina.puesto = when {
                    Pue1.isChecked -> 1
                    Pue2.isChecked -> 2
                    Pue3.isChecked -> 3
                    else -> 0
                }


                subTot.text = "Subtotal: %.2f".format(reciboNomina.calcularSubtotal())
                impuesto.text = "Impuesto: %.2f".format(reciboNomina.calcularImpuestos())
                totalPag.text = "Total a pagar: %.2f".format(reciboNomina.calcularTotal())
            }
        })

        btnLimpiar.setOnClickListener(View.OnClickListener {
            txtEmpleado.text = txtEmpleado.text
            subTot.text = "Subtotal"
            impuesto.text = "Impuesto"
            totalPag.text = "Total a pagar"

            horasT.setText("")
            horasE.setText("")

            Pue1.isChecked = true

        })
        btnRegresar.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Nomina")
            builder.setMessage("¿Deseas regresar al menu?")

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
        setContentView(R.layout.activity_recibo_nomina)

        iniciarComponentes()
        eventosClic()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}