package com.dolphhincapie.sesionroom

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        bt_guardar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val email = et_correo.text.toString()
            val password = et_contrasena.text.toString()
            val repContra = et_repitacontrasena.text.toString()
            val ciudadNacimiento = s_ciudades.selectedItem.toString()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        //crearUsuarioEnBaseDeDatos()
                        Toast.makeText(
                            this,
                            "Registro exitoso",
                            Toast.LENGTH_SHORT
                        ).show()
                        onBackPressed()
                    } else {

                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                    }

                    // ...
                }
        }
    }

    private fun crearUsuarioEnBaseDeDatos() {
        TODO("Not yet implemented")
    }
}