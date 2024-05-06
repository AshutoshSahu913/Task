package com.example.chaintechnetworktask.View.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chaintechnetworktask.databinding.FragmentAddAccountBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddAccountFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddAccountBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentAddAccountBinding.inflate(layoutInflater)




        return binding.root
    }

}