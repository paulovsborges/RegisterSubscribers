package com.example.mysubscribers.data.db

import android.content.Context
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

    //implementação de um singleton
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this){
                var instance: AppDataBase? = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "app_database"
                    ).build()
                }
                return instance
            }
        }
    }
}