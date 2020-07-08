package com.dolphhincapie.sesionroom.model2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tabla_usuarios")
class User (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "contraseña") val contrasena: String,
    @ColumnInfo(name = "Ciudad") val ciudad: String
)