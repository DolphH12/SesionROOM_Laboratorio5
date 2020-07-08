package com.dolphhincapie.sesionroom.model2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDataBase : RoomDatabase(){

    abstract fun UserDAO() : UserDAO

}