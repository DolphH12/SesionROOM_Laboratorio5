package com.dolphhincapie.sesionroom.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dolphhincapie.sesionroom.R
import com.dolphhincapie.sesionroom.model.local.Deudor
import com.dolphhincapie.sesionroom.model.local.DeudorDAO
import com.dolphhincapie.sesionroom.model.remote.DeudorRemote
import com.dolphhincapie.sesionroom.sesionROOM
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateFragment : Fragment() {

    var idDeudorFirebase : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_update, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideDeudorDatosET()

        var idDeudor = 0
        val deudorDAO : DeudorDAO = sesionROOM.dataBase.DeudorDAO()

        bt_nombre.setOnClickListener {
            val nombre = et_nombre.text.toString()

            //buscarEnRoom(deudorDAO, nombre, idDeudor)

            buscarEnFirebase(nombre)

        }

        bt_actualizar.setOnClickListener {
            //actualizarEnRoom(idDeudor, deudorDAO)
            actualizarEnFirebase()
            hideDeudorDatosET()


        }

    }

    private fun actualizarEnFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")

        val childUpdate = HashMap<String, Any>()
        childUpdate["nombre"] = et_nombre.text.toString()
        childUpdate["telefono"] = et_telefono.text.toString()
        childUpdate["cantidad"] = et_cantidad.text.toString().toLong()
        myRef.child(idDeudorFirebase!!).updateChildren(childUpdate)

    }

    private fun actualizarEnRoom(
        idDeudor: Int,
        deudorDAO: DeudorDAO
    ) {
        val deudor = Deudor(
            idDeudor,
            et_nombre.text.toString(),
            et_telefono.text.toString(),
            et_cantidad.text.toString().toLong()
        )
        deudorDAO.actualizarDeudor(deudor)
        hideDeudorDatosET()
        bt_nombre.visibility = View.VISIBLE
    }

    private fun buscarEnFirebase(nombre: String) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")
            var deudorExiste = false

            val postListener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (datasnapshot: DataSnapshot in snapshot.children){
                        val deudor = datasnapshot.getValue(DeudorRemote::class.java)
                        if (deudor?.nombre == nombre){
                            deudorExiste = true
                            habilitarWidgetsActualizar()
                            et_telefono.setText(deudor.telefono)
                            et_cantidad.setText(deudor.cantidad.toString())
                            idDeudorFirebase = deudor.id
                        }
                    }
                    if (!deudorExiste)
                        Toast.makeText(requireContext(),"Deudor no existe", Toast.LENGTH_LONG).show()
                }

            }
            myRef.addListenerForSingleValueEvent(postListener)
    }

    private fun buscarEnRoom(
        deudorDAO: DeudorDAO,
        nombre: String,
        idDeudor: Int
    ) {
        var idDeudor1 = idDeudor
        val deudor = deudorDAO.buscarDeudor(nombre)

        if (deudor != null) {
            idDeudor1 = deudor.id
            habilitarWidgetsActualizar()
            et_telefono.setText(deudor.telefono)
            et_cantidad.setText(deudor.cantidad.toString())
        } else {
            Toast.makeText(context, "Dedudor no encontrado", Toast.LENGTH_LONG).show()
        }
    }

    private fun habilitarWidgetsActualizar() {
        et_telefono.visibility = View.VISIBLE
        et_cantidad.visibility = View.VISIBLE
        bt_nombre.visibility = View.GONE
        bt_actualizar.visibility = View.VISIBLE
    }

    private fun hideDeudorDatosET() {
        et_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        bt_actualizar.visibility = View.GONE
    }

}