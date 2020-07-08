package com.dolphhincapie.sesionroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dolphhincapie.sesionroom.model2.UserDAO
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    var email = ""
    var  password = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_passregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        bt_ingresar.setOnClickListener {
            val correo = te_usuario.text.toString()
            val contrasena = te_contrasena.text.toString()

            val userDAO: UserDAO = sesionROOM.dataBase2.UserDAO()
            val user = userDAO.buscarCorreo(correo)

            if (user != null){
                email = user.correo
                password = user.contrasena
                if (password == contrasena){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Correo o Contraseña incorrecta", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Correo o Contraseña incorrecta", Toast.LENGTH_LONG).show()
            }

        }
    }

}