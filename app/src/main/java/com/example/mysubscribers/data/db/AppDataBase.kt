package com.example.mysubscribers.data.db

import android.app.Application
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysubscribers.data.db.dao.SubscriberDAO
import com.example.mysubscribers.data.db.entity.SubscriberEntity
//passo 3 do room
//entities(Tabelas)
@Database(entities = [SubscriberEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract val subscriberDAO : SubscriberDAO
}

internal fun provideDataBase(application: Application): AppDataBase{
    return Room.databaseBuilder(application, AppDataBase::class.java, "app_database").build()
}

internal fun provideDao(appDB: AppDataBase): SubscriberDAO{
    return appDB.subscriberDAO
}