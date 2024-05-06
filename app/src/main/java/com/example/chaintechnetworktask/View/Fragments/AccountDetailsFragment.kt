package com.example.chaintechnetworktask.View.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chaintechnetworktask.DataSource.Room.SavedPasswordEntity
import com.example.chaintechnetworktask.R
import com.example.chaintechnetworktask.ViewModel.MainViewModel
import com.example.chaintechnetworktask.databinding.FragmentAccountDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountDetailsFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAccountDetailsBinding
    lateinit var viewModel: MainViewModel
    var passwordId: Int = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountDetailsBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.edAccountPassword.setOnTouchListener { _, event ->

            val drawableRight = 2 // Assuming the eye icon is set to the end drawable
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.edAccountPassword.right - binding.edAccountPassword.compoundDrawables[drawableRight].bounds.width())) {
                    // Toggle password visibility
                    if (binding.edAccountPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        binding.edAccountPassword.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        binding.edAccountPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.eye_svgrepo_com,
                            0
                        )
                    } else {
                        binding.edAccountPassword.transformationMethod =
                            PasswordTransformationMethod.getInstance()
                        binding.edAccountPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.eye_off_svgrepo_com,
                            0
                        )
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }


        getData()

        binding.deleteBtn.setOnClickListener {
            deleteSavedPasswords(passwordId)
        }

        binding.editBtn.setOnClickListener {
            val name = binding.edAccountType.text.toString().trim()
            val username = binding.edAccountUserNameEmail.text.toString().trim()
            val password = binding.edAccountPassword.text.toString().trim()


            if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                updateSavedPasswords(
                    accountName = name,
                    usernameEmail = username,
                    password = password
                )
            } else {
                if (binding.edAccountType.text.isEmpty()) {
                    binding.edAccountType.error = "Please fill Account Name"
                }
                if (binding.edAccountUserNameEmail.text.isEmpty()) {
                    binding.edAccountUserNameEmail.error = "Please fill Username/Email"
                }
                if (binding.edAccountPassword.text.isEmpty()) {
                    binding.edAccountPassword.error = "Please fill Password"
                }

            }
        }
        return binding.root
    }

    //
    private fun updateSavedPasswords(
        accountName: String,
        usernameEmail: String,
        password: String
    ) {
        lifecycleScope.launch {
            val updatedPassword = SavedPasswordEntity()
            updatedPassword.accountName = accountName
            updatedPassword.userName_Email = usernameEmail
            updatedPassword.password = password
            updatedPassword.id = passwordId
            lifecycleScope.launch {
                viewModel.updateSavedPassword(
                    updatedPassword
                )
                Toast.makeText(requireContext(), "Update Details", Toast.LENGTH_SHORT).show()
                dismiss()
                withContext(Dispatchers.Main) {
                    recreate(requireActivity())
                }
            }
        }
    }

    private fun deleteSavedPasswords(id: Int) {
        lifecycleScope.launch {
            viewModel.deleteSavedPassword(id)
            Toast.makeText(requireContext(), "Deleted Successfully !", Toast.LENGTH_SHORT).show()

            dismiss()
            recreate(requireActivity())
        }
    }

    private fun getData() {
        val bundle = arguments
        if (bundle != null) {
            passwordId = bundle.getInt("id")
            val name = bundle.getString("name")
            val userName = bundle.getString("user_name")
            val password = bundle.getString("password")
            binding.edAccountType.setText(name)
            binding.edAccountUserNameEmail.setText(userName)
            binding.edAccountPassword.setText(password)
        } else {
            Toast.makeText(requireContext(), "No Data Fetch", Toast.LENGTH_SHORT).show()
        }
    }

}