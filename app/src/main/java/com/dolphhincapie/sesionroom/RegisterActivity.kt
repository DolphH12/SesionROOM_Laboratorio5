package com.dolphhincapie.sesionroom

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dolphhincapie.sesionroom.model2.User
import com.dolphhincapie.sesionroom.model2.UserDAO
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.Types.NULL


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


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
                    val user = User(NULL, nombre, correo, contrasena, ciudadNacimiento)

                    val userDAO : UserDAO = sesionROOM.dataBase2.UserDAO()

                    userDAO.crearUsuario(user)

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    et_contrasena.error
                    et_repitacontrasena.error
                    Toast.makeText(this, "Contraseñas diferentes", Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
    }

}