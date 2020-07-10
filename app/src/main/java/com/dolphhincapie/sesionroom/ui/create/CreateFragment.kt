package com.dolphhincapie.sesionroom.ui.create

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.io.ByteArrayOutputStream
import java.sql.Types.NULL

class CreateFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1234


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarMensajeBienvenida()

        //guardarEnFirebase(nombre, telefono, cantidad)

        iv_foto.setOnClickListener{
            dispatchTakePictureIntent()
        }

        bt_guardar.setOnClickListener {
            val nombre = et_nombre.text.toString()
            val telefono = et_telefono.text.toString()
            val cantidad = et_cantidad.text.toString().toLong()

            guardarEnRoomExtra(nombre, telefono, cantidad)

            guardarEnFirebase(nombre, telefono, cantidad)

            cleanEditText()


        }
    }

    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            iv_foto.setImageBitmap(imageBitmap)
        }
    }

    private fun guardarEnFirebase(
        nombre: String,
        telefono: String,
        cantidad: Long
    ) {

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("deudores")
        val id: String? = myRef.push().key

        val mStorage= FirebaseStorage.getInstance()
        val photoRef = mStorage.reference.child(id!!)

        iv_foto.isDrawingCacheEnabled = true
        iv_foto.buildDrawingCache()
        val bitmap = (iv_foto.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var urlPhoto : String

        var uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                urlPhoto = task.result.toString()
                val deudor = DeudorRemote(
                    id,
                    nombre,
                    telefono,
                    cantidad,
                    urlPhoto
                )
                myRef.child(id!!).setValue(deudor)
            } else {
                // Handle failures
                // ...
            }
        }

    }

    private fun guardarEnRoomExtra(
        nombre: String,
        telefono: String,
        cantidad: Long
    ) {
        val deudor = Deudor(NULL, nombre, telefono, cantidad)
        guardarEbRomm(deudor)
    }

    private fun mostrarMensajeBienvenida() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = mAuth.currentUser
        val correo = user?.email

        Toast.makeText(requireContext(), "Bienvenido $correo", Toast.LENGTH_LONG).show()
    }

    private fun cleanEditText() {
        et_nombre.setText("")
        et_telefono.setText("")
        et_cantidad.setText("")
    }

    private fun guardarEbRomm(deudor: Deudor) {
        val deudorDAO: DeudorDAO = sesionROOM.dataBase.DeudorDAO()

        deudorDAO.crearDeudor(deudor)
    }

}