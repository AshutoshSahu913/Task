package com.example.chaintechnetworktask.View.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.chaintechnetworktask.R
import com.example.chaintechnetworktask.databinding.FragmentAccountDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountDetailsFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAccountDetailsBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountDetailsBinding.inflate(layoutInflater)

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

        return binding.root
    }

    private fun getData() {
        val bundle = arguments
        val name = bundle?.getString("name")
        val userName = bundle?.getString("user_name")
        val password = bundle?.getString("password")
        binding.edAccountType.setText(name)
        binding.edAccountUserNameEmail.setText(userName)
        binding.edAccountPassword.setText(password)
    }

}