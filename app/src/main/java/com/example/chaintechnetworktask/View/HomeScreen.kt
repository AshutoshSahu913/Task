package com.example.chaintechnetworktask.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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
import com.example.chaintechnetworktask.decrypt
import com.example.chaintechnetworktask.generateAESKey
import java.util.concurrent.Executor

class HomeScreen : AppCompatActivity() {
    private val binding: ActivityHomeScreenBinding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var passwordAdapter: PasswordAdapter

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private var selectedAccountDetails: AccountDetailsModel? = null

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

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this, executor, BiometricCallback()
        )
        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Password for my app")
            .setSubtitle("check password using your biometric credentials")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        getSavedPassword()

        binding.addDetails.setOnClickListener {
            val bottomSheetFragment = AddAccountFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSavedPassword() {
        viewModel.getSavedPassword().observe(this) { savedPass ->
            val passwordList = arrayListOf<AccountDetailsModel>()
            for (i in savedPass) {
                val key = generateAESKey(accountName = i.accountName.toString())
                val decryptPassword = decrypt(i.password.toString(), key = key)
                val savedPassword = AccountDetailsModel(
                    accountName = i.accountName,
                    password = decryptPassword,
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
            passwordAdapter.notifyDataSetChanged()
        }
    }

    private fun onClickItem(accountDetailsModel: AccountDetailsModel?) {
        if (biometricsAvailable()) {
            selectedAccountDetails = accountDetailsModel
            biometricPrompt.authenticate(promptInfo)
        } else {
            openAccountDetailsFragment(accountDetailsModel)
        }
    }

    private fun openAccountDetailsFragment(accountDetailsModel: AccountDetailsModel?) {
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

    private fun biometricsAvailable(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Toast.makeText(
                    this@HomeScreen, "App can authenticate using biometrics.", Toast.LENGTH_SHORT
                ).show()
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(
                    this@HomeScreen,
                    "No biometrics features available on this device.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(
                    this@HomeScreen,
                    "Biometrics features are currently unavailable.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> false
        }
    }

    inner class BiometricCallback : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            selectedAccountDetails?.let { openAccountDetailsFragment(it) }
        }
    }
}
