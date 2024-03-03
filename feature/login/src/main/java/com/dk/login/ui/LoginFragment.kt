package com.dk.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dk.core.app.MainViewModel
import com.dk.login.databinding.FragmentLoginBinding
import com.dk.login.utils.PhoneValidator
import com.dk.login.utils.Validator
import com.dk.login.utils.ValidatorType
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            etFirstName.doOnTextChanged { text, _, _, _ ->
                showError(text, ValidatorType.NAME, tilFirstName)
                buttonEnabledValidator()
            }
            etSecondName.doOnTextChanged { text, _, _, _ ->
                showError(text, ValidatorType.NAME, tilSecondName)
                buttonEnabledValidator()
            }
            etPhoneNumber.doOnTextChanged { text, _, _, _ ->
                showError(text, ValidatorType.PHONE, tilPhoneNumber)
                buttonEnabledValidator()
            }
            etPhoneNumber.addTextChangedListener(PhoneValidator(etPhoneNumber))

            btnLogin.setOnClickListener {
                val firstName = etFirstName.text.toString()
                val secondName = etSecondName.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()
                viewModel.saveProfile(firstName, secondName, phoneNumber)
                val request = NavDeepLinkRequest.Builder
                    .fromUri(resources.getString(com.dk.core.R.string.to_main_fragment).toUri())
                    .build()
                findNavController().navigate(
                    request,
                    NavOptions.Builder().setLaunchSingleTop(true).build()
                )

            }
        }
    }

    private fun showError(
        text: CharSequence?, validatorType: ValidatorType, textInputLayout: TextInputLayout
    ) {
        textInputLayout.boxBackgroundColor =
            if (Validator().check(text.toString(), validatorType)) {
                resources.getColor(com.dk.core.R.color.background_gray)
            } else {
                resources.getColor(com.dk.core.R.color.background_light_pink)
            }
    }

    private fun buttonEnabledValidator() {
        with(binding) {
            val firstName = etFirstName.text.toString()
            val secondName = etSecondName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            btnLogin.isEnabled =
                Validator().check(firstName, ValidatorType.NAME) && Validator().check(
                    secondName,
                    ValidatorType.NAME
                ) && Validator().check(phoneNumber, ValidatorType.PHONE)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}