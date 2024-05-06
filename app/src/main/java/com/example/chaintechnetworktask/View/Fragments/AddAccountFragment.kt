package com.example.chaintechnetworktask.View.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordEntity
import com.example.chaintechnetworktask.ViewModel.MainViewModel
import com.example.chaintechnetworktask.databinding.FragmentAddAccountBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddAccountFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddAccountBinding

    private lateinit var viewModel: MainViewModel

    var savedPassword = SavedPasswordEntity()


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentAddAccountBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.addAccount.setOnClickListener {

            val accountName = binding.edAccountName.text.toString().trim()
            val userNameEmail = binding.edAccountUserNameEmail.text.toString().trim()
            val password = binding.edAccountPassword.text.toString().trim()

            if (accountName.isNotEmpty() && userNameEmail.isNotEmpty() && password.isNotEmpty()) {
                // All fields are filled, proceed with saving
                savedPassword.accountName = accountName
                savedPassword.userName_Email = userNameEmail
                savedPassword.password = password

                binding.addAccount.visibility = View.GONE
                binding.loaderAddAcount.visibility = View.VISIBLE
//                val savedPassword = SavedPassword(accountName, userNameEmail, password)
                savedPasswordDetails(savedPassword)
            } else {
                if (accountName.isEmpty()) {
                    binding.edAccountName.error = "Please fill Account Name"
                }
                if (userNameEmail.isEmpty()) {
                    binding.edAccountUserNameEmail.error = "Please fill Username/Email"
                }
                if (password.isEmpty()) {
                    binding.edAccountPassword.error = "Please fill Password"
                }
            }
        }
        return binding.root
    }

    private fun savedPasswordDetails(
        savedPassword: SavedPasswordEntity
    ) {
        lifecycleScope.launch {
            viewModel.insertPassword(savedPassword = savedPassword)
            binding.loaderAddAcount.visibility = View.GONE
            Toast.makeText(requireContext(), "Saved Password Details", Toast.LENGTH_SHORT).show()
            dismiss()
            recreate(requireActivity())
        }

    }

}