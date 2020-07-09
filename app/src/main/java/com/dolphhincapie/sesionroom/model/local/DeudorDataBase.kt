package com.dolphhincapie.sesionroom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dolphhincapie.sesionroom.model.local.Deudor

@Database(entities = arrayOf(Deudor::class), version = 1)
abstract class DeudorDataBase: RoomDatabase() {

    abstract fun DeudorDAO() : DeudorDAO

}