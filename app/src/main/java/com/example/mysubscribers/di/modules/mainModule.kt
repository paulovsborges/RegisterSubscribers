package com.example.mysubscribers.di.modules

import com.example.mysubscribers.data.db.provideDao
import com.example.mysubscribers.data.db.provideDataBase
import com.example.mysubscribers.repository.DataBaseDataSource
import com.example.mysubscribers.repository.SubscriberRepository
import com.example.mysubscribers.ui.SubscriberList.SubscriberListViewModel
import com.example.mysubscribers.ui.subscriber.SubscriberViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SubscriberViewModel(repository = get()) }
    viewModel { SubscriberListViewModel(repository = get()) }
}

val dataBaseModule = module {

    single { provideDataBase(get()) }
    single { provideDao(get()) }
}

val repositoryModule = module {

    single<SubscriberRepository> { DataBaseDataSource(get()) }
}