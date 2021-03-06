package com.rkpsx7.moneymanager7.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ExpenseEntity::class, IncomeEntity::class, UserEntity::class],
    version = 1
)
@TypeConverters(RoomTypeConvertors::class)
abstract class MainRoomDb : RoomDatabase() {

    abstract fun getDao(): DAO

    companion object {

        private var Instance: MainRoomDb? = null

        fun getMainRoomDb(context: Context): MainRoomDb {
            if (Instance == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDb::class.java,
                    "MoneyManagerDB"
                )
                builder.fallbackToDestructiveMigration()
                Instance = builder.build()
                return Instance!!
            } else return Instance!!
        }
    }


}