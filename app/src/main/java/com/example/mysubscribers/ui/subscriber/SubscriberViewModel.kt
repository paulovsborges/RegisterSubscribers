package com.example.mysubscribers.ui.subscriber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysubscribers.R
import com.example.mysubscribers.repository.SubscriberRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
passo 6 do room -> viewModel vai chamar a camada de repositorio,
repositorio chama a camada de dados, porém a ViewModel n pode conhecer a implementação concreta da interface do repositorio,
 somente o contrato.

*/

class SubscriberViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    private val _subscriberStateEventData = MutableLiveData<SubscriberState>()
    val subscriberStateEventData : LiveData<SubscriberState>
        get( ) = _subscriberStateEventData      //função do LiveData para referenciar o subscriberStateEvent que vai escutar oq o _subscriberStateEventData fizer

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData : LiveData<Int>
        get( ) = _messageEventData



    fun addOrUpdateSubscriber(name: String, email: String, id: Long = 0){

        if(id > 0) {
            updateSubscriber(id, name, email)
        }else {
            insertSubscriber(name, email)
        }


    }

    private fun updateSubscriber(id: Long, name: String, email: String) = viewModelScope.launch {

        try {
            repository.updateSubscriber(id, name, email)

                _subscriberStateEventData.value = SubscriberState.Updated
                _messageEventData.value = R.string.subscriber_updated_successfully

        }catch (ex : Exception){
            _messageEventData.value = R.string.subscriber_error_insert
            Log.e(TAG, ex.toString())
        }
    }


    private fun insertSubscriber(name: String, email: String) = viewModelScope.launch {
        try {

            val id = repository.insertSubscriber(name, email)
            if (id > 0 ) {
                _subscriberStateEventData.value = SubscriberState.Inserted
                _messageEventData.value = R.string.subscriber_inserted_successfully
            }

        }catch (ex : Exception){
            _messageEventData.value = R.string.subscriber_error_insert
            Log.e(TAG, ex.toString())   // mostra para nós se houver algum erro em alguma coisa
        }
    }


    fun deleteSubscriber(id: Long) = viewModelScope.launch {
        try {
            repository.deleteSubscriber(id)
            if(id > 0){
                _subscriberStateEventData.value = SubscriberState.Deleted
                _messageEventData.value = R.string.subscriber_deleted_successfully
            }

        }catch (ex: Exception){
            _messageEventData.value = R.string.subscriber_error_delete
            Log.e(TAG, ex.toString())
        }
    }

    //padrao MVI

    sealed class SubscriberState{
        object Inserted : SubscriberState()
        object Updated : SubscriberState()
        object Deleted : SubscriberState()
    }

    companion object{
        private val TAG  = SubscriberViewModel::class.java.simpleName
    }


}