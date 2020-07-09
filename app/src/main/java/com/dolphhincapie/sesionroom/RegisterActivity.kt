package com.dolphhincapie.sesionroom

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.dolphhincapie.sesionroom.model2.User
import com.dolphhincapie.sesionroom.model2.UserDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.Types.NULL


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        bt_guardar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val correo = et_correo.text.toString()
            val contrasena = et_contrasena.text.toString()
            val repContra = et_repitacontrasena.text.toString()
            val ciudadNacimiento = s_ciudades.selectedItem.toString()

            if(nombre.isEmpty()){
                et_nombre.error = "Campo nombre vacio"
                //Toast.makeText(this, "Campo Nombre Vacio", Toast.LENGTH_LONG).show()
            }
            else if (correo.isEmpty() || "@" !in correo){
                et_correo.error = "Campo correo vacio"
                //Toast.makeText(this, "Campo Correo Invalido", Toast.LENGTH_LONG).show()
            }
            else if (contrasena.isEmpty()){
                et_contrasena.error = "Campo contraseña vacio"
                //Toast.makeText(this, "Campo Contraseña Vacio", Toast.LENGTH_LONG).show()
            }
            else if (repContra.isEmpty()){
                et_repitacontrasena.error = "Campo de repetir contraseña vacio"
                //Toast.makeText(this, "Campo Rep Contraseña Vacio", Toast.LENGTH_LONG).show()
            }
            else {
                if (contrasena == repContra){

                    mAuth.createUserWithEmailAndPassword(correo, contrasena)
                        .addOnCompleteListener(
                            this
                        ) { task ->
                            if (task.isSuccessful) {
//                                crearUsuarioeEnBaseDeDatos()
                                Toast.makeText(
                                    this, "Registro Exítoso",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onBackPressed()
                            } else {
                                Toast.makeText(
                                    this, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                            }

                            // ...
                        }

                }
                else{
                    et_contrasena.error
                    et_repitacontrasena.error
                    Toast.makeText(this, "Contraseñas diferentes", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

/*    private fun crearUsuarioeEnBaseDeDatos() {
        TODO("Not yet implemented")
    }*/
}