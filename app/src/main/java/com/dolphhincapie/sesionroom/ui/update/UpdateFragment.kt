package com.dolphhincapie.sesionroom.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dolphhincapie.sesionroom.R
import com.dolphhincapie.sesionroom.model.Deudor
import com.dolphhincapie.sesionroom.model.DeudorDAO
import com.dolphhincapie.sesionroom.sesionROOM
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.et_nombre

class UpdateFragment : Fragment() {

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
        et_telefono.visibility = View.GONE
        et_cantidad.visibility = View.GONE
        bt_actualizar.visibility = View.GONE
        var idDeudor = 0
        val deudorDAO : DeudorDAO = sesionROOM.dataBase.DeudorDAO()

        bt_nombre.setOnClickListener {
            val nombre = et_nombre.text.toString()


            val deudor = deudorDAO.buscarDeudor(nombre)

            if (deudor != null){
                idDeudor = deudor.id
                et_telefono.visibility = View.VISIBLE
                et_cantidad.visibility = View.VISIBLE
                et_telefono.setText(deudor.telefono)
                et_cantidad.setText(deudor.cantidad.toString())
                bt_nombre.visibility = View.GONE
                bt_actualizar.visibility = View.VISIBLE
            }else{
                Toast.makeText(context, "Dedudor no encontrado",Toast.LENGTH_LONG).show()
            }
        }

        bt_actualizar.setOnClickListener {
            val deudor = Deudor(idDeudor,
                et_nombre.text.toString(),
                et_telefono.text.toString(),
                et_cantidad.text.toString().toLong()
            )
            deudorDAO.actualizarDeudor(deudor)
            et_telefono.visibility = View.GONE
            et_cantidad.visibility = View.GONE
            bt_actualizar.visibility = View.GONE
            bt_nombre.visibility = View.VISIBLE

        }

    }

}