package com.example.moneymanager.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneymanager.Models.BalanceEntity
import com.example.moneymanager.Models.ExpenseEntity
import com.example.moneymanager.Models.IncomeEntity

@Database(entities = [ExpenseEntity::class, IncomeEntity::class, BalanceEntity::class], version = 1)
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