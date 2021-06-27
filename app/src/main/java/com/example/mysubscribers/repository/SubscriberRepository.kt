package com.example.mysubscribers.repository

import androidx.lifecycle.LiveData
import com.example.mysubscribers.data.db.entity.SubscriberEntity

//passo 4 do room
interface SubscriberRepository {

    //todas essas funções com o suspend no inicio, farão parte do processo de Coroutines

    suspend fun insertSubscriber(name: String, email: String): Long

    suspend fun updateSubscriber(id: Long, name: String, email: String)

    suspend fun deleteSubscriber(id: Long)

    suspend fun deleteAllSubscriber()

    suspend fun getAllSubscriber(): List<SubscriberEntity>



}