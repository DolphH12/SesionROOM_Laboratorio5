package com.dolphhincapie.sesionroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var email = ""
    var  password = ""
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onStart(){
        super.onStart()
        val user = mAuth.currentUser
        if (user != null)
            goToMainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_passregister.setOnClickListener {
            goToResgistro()
            finish()
        }

        bt_ingresar.setOnClickListener {
            val correo = te_usuario.text.toString()
            val contrasena = te_contrasena.text.toString()

            mAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        goToMainActivity()
                    } else {
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                    }


                }

        }
    }

    private fun goToResgistro() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}