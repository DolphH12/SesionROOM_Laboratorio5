package com.dolphhincapie.sesionroom.ui.read

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dolphhincapie.sesionroom.R
import com.dolphhincapie.sesionroom.model.local.DeudorDAO
import com.dolphhincapie.sesionroom.model.remote.DeudorRemote
import com.dolphhincapie.sesionroom.sesionROOM
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_read.*

class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_read, container, false)
        return root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_buscar.setOnClickListener {
            val nombre = et_nombre.text.toString()




            buscarEnFirebase(nombre)
            //buscarEnRoom(nombre)

        }
    }


    private fun buscarEnFirebase(nombre: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var deudorExiste = false

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                    if (deudor?.nombre == nombre) {
                        deudorExiste = true
                        mostrarDeudor(deudor.nombre, deudor.telefono, deudor.cantidad)
                    }
                }
                if (!deudorExiste) {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                }
            }
        }
        myRef.addValueEventListener(postListener)
    }

    private fun buscarEnRoom(nombre: String) {
        val deudorDAO: DeudorDAO = sesionROOM.dataBase.DeudorDAO()
        val deudor = deudorDAO.buscarDeudor(nombre)

        if (deudor != null) {
            mostrarDeudor(deudor.nombre, deudor.telefono, deudor.cantidad)
        }
    }

    private fun mostrarDeudor(nombre: String, telefono: String, cantidad: Long) {

        tv_resultado.text =
            "\nNombre: ${nombre}\n" +
                    "Telefono: ${telefono}\n" +
                    "Cantidad: ${cantidad}"
    }

}