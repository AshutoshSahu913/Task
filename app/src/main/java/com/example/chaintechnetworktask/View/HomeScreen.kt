package com.example.chaintechnetworktask.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chaintechnetworktask.DataSource.Models.AccountDetailsModel
import com.example.chaintechnetworktask.R
import com.example.chaintechnetworktask.View.Adapter.PasswordAdapter
import com.example.chaintechnetworktask.View.Fragments.AccountDetailsFragment
import com.example.chaintechnetworktask.View.Fragments.AddAccountFragment
import com.example.chaintechnetworktask.ViewModel.MainViewModel
import com.example.chaintechnetworktask.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {
    private val binding: ActivityHomeScreenBinding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var passwordAdapter: PasswordAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //is this function to get data from database
        getSavedPassword()

        binding.addDetails.setOnClickListener {
            val bottomSheetFragment = AddAccountFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }


    private fun getSavedPassword() {
        viewModel.getSavedPassword().observeForever { savedPass ->
            val passwordList = arrayListOf<AccountDetailsModel>()
            for (i in savedPass) {
                val savedPassword = AccountDetailsModel(
                    accountName = i.accountName,
                    password = i.password,
                    userName_Email = i.userName_Email,
                    id = i.id!!.toInt()
                )
                passwordList.add(savedPassword)
            }
            binding.rvSavePassword.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            passwordAdapter = PasswordAdapter(
                onClickItem = ::onClickItem,
                this@HomeScreen,
            )

            if (passwordList.isEmpty()) {
                binding.emptyTxt.visibility = View.VISIBLE
            }
            binding.rvSavePassword.adapter = passwordAdapter
            passwordAdapter.differ.submitList(passwordList)
        }
    }

    private fun onClickItem(accountDetailsModel: AccountDetailsModel?) {
        val bundle = Bundle()
        if (accountDetailsModel != null) {
            bundle.putInt("id", accountDetailsModel.id)
            bundle.putString("name", accountDetailsModel.accountName)
            bundle.putString("user_name", accountDetailsModel.userName_Email)
            bundle.putString("password", accountDetailsModel.password)

            val bottomSheet = AccountDetailsFragment()
            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

}