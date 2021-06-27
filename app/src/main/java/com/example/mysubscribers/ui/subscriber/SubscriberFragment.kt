package com.example.mysubscribers.ui.subscriber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mysubscribers.R
import com.example.mysubscribers.data.db.AppDataBase
import com.example.mysubscribers.data.db.dao.SubscriberDAO
import com.example.mysubscribers.extensions.hideKeyboard
import com.example.mysubscribers.repository.DataBaseDataSource
import com.example.mysubscribers.repository.SubscriberRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.subscriber_fragment.*

class SubscriberFragment : Fragment(R.layout.subscriber_fragment) {

    private val viewModel: SubscriberViewModel by viewModels {
        //dependencias do viewModel
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDataBase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DataBaseDataSource(subscriberDAO)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    private val args: SubscriberFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.subscriber?.let { subscriber ->
            button_subscriber.text = getString(R.string.subscriber_button_update)
            input_name.setText(subscriber.name)
            input_email.setText(subscriber.email)
            button_delete.visibility = View.VISIBLE
        }

        observeEvents()
        setListeenrs()
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner) { subscriberState ->
            when (subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted,
                is SubscriberViewModel.SubscriberState.Deleted -> {
                    cleaFields()
                    hideKeyboard()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                }

                is SubscriberViewModel.SubscriberState.Updated -> {
                    cleaFields()
                    hideKeyboard()
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG)
                .show() //o SnackBar é um Toast só que com possibilidade de implementar um click ou ação
        }
    }

    private fun cleaFields() {
        input_name.text?.clear()
        input_email.text?.clear()
    }


    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeenrs() {
        button_subscriber.setOnClickListener {
            val name = input_name.text.toString()
            val email = input_email.text.toString()

            viewModel.addOrUpdateSubscriber(name, email, args.subscriber?.id ?: 0)
        }
        button_delete.setOnClickListener {
            viewModel.deleteSubscriber(args.subscriber?.id ?: 0)
        }
    }
}