package com.example.mysubscribers.ui.SubscriberList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mysubscribers.R
import com.example.mysubscribers.data.db.AppDataBase
import com.example.mysubscribers.data.db.dao.SubscriberDAO
import com.example.mysubscribers.data.db.entity.SubscriberEntity
import com.example.mysubscribers.extensions.navigateWithAnimations
import com.example.mysubscribers.repository.DataBaseDataSource
import com.example.mysubscribers.repository.SubscriberRepository
import com.example.mysubscribers.ui.subscriber.SubscriberFragment
import com.example.mysubscribers.ui.subscriber.SubscriberFragmentArgs
import com.example.mysubscribers.ui.subscriber.SubscriberViewModel
import kotlinx.android.synthetic.main.subscriber_fragment.*
import kotlinx.android.synthetic.main.subscriber_list_fragment.*

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {

    private val viewModel: SubscriberListViewModel by viewModels {
        //dependencias do viewModel
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO = AppDataBase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberListViewModel(repository) as T

            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        configureViewListeners()
        observeViewModelEvents()
    }


    private fun observeViewModelEvents() {

        viewModel.allSubscriberEvent.observe(viewLifecycleOwner){allSAubscribers ->

            val subscriberListAdapter = SubscriberListAdapter(allSAubscribers).apply {
                onItemClick = { subscriber ->
                val directions = SubscriberListFragmentDirections.actionSubscriberListFragmentToSubscriberFragment(subscriber)

                   findNavController().navigateWithAnimations(directions)
                }
            }

            with(recycler_subscribers) {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscribers()
    }

    private fun configureViewListeners(){
        button_add_subscriber.setOnClickListener{
            findNavController().navigateWithAnimations(R.id.action_subscriberListFragment_to_subscriberFragment)
        }
    }
}