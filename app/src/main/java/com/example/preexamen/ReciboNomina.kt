package com.example.preexamen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable
import kotlin.random.Random

class ReciboNomina : Serializable {

    var numRecibo :  Int = 0
    var Nombre : String = ""
    var horasTrabNormal: Float = 0.0f
    var horasTrabExtras: Float = 0.0f
    var puesto: Int = 0
    var pago: Int = 200
    var impuestoPorc: Float = 16.0f  // 16% de impuesto

    constructor(){
        this.numRecibo = 0
        this.Nombre = ""
        this.horasTrabNormal = 0.0f
        this.horasTrabExtras = 0.0f
        this.puesto = 0
        this.pago = 200
        this.impuestoPorc = 16.0f
    }

    fun generarRecibo(): Int {
        return Random.nextInt(0,9999)
    }

    public fun calcularSubtotal(): Float {
        val pagoBase: Float = 200.0f
        var pagoAdicional: Float = pagoBase

        when (puesto) {
            1 -> pagoAdicional = pagoBase * 1.20f
            2 -> pagoAdicional = pagoBase * 1.50f
            3 -> pagoAdicional = pagoBase * 2.00f
            else -> println("Puesto no disponible")
        }

        val subtotal = (pagoAdicional * horasTrabNormal) + (horasTrabExtras * pagoAdicional * 2)
        return subtotal
    }


    fun calcularImpuestos(): Float {
        val subtotal = calcularSubtotal()
        return (subtotal * impuestoPorc) / 100
    }

    fun calcularTotal(): Float {
        val subtotal = calcularSubtotal()
        val impuesto = calcularImpuestos()
        return subtotal - impuesto
    }

}
