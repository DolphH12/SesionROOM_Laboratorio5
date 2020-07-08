package com.dolphhincapie.sesionroom

import android.app.Application
import androidx.room.Room
import com.dolphhincapie.sesionroom.model.DeudorDataBase
import com.dolphhincapie.sesionroom.model2.UserDataBase

class sesionROOM : Application() {

    companion object{
        lateinit var dataBase: DeudorDataBase
        lateinit var dataBase2: UserDataBase
    }

    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            this,
            DeudorDataBase::class.java,
            "misdeudores_db"
        ).allowMainThreadQueries()
            .build()

        dataBase2 = Room.databaseBuilder(
            this,
            UserDataBase::class.java,
            "usuarios_db"
        ).allowMainThreadQueries()
            .build()

    }

}