package com.dolphhincapie.sesionroom.model2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert
    fun crearUsuario(user: User)

    @Query("SELECT * FROM tabla_usuarios WHERE correo LIKE :correo")
    fun buscarCorreo(correo: String): User

}