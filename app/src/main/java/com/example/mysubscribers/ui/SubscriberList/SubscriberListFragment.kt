package com.example.mysubscribers.ui.SubscriberList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mysubscribers.R
import com.example.mysubscribers.extensions.navigateWithAnimations
import kotlinx.android.synthetic.main.subscriber_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriberListFragment : Fragment(R.layout.subscriber_list_fragment) {


    private val viewModel: SubscriberListViewModel by viewModel()


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