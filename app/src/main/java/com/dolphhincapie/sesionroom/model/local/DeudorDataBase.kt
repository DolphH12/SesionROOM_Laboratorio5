package com.dolphhincapie.sesionroom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Deudor::class), version = 1)
abstract class DeudorDataBase: RoomDatabase() {

    abstract fun DeudorDAO() : DeudorDAO

}