package com.dk.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.dk.login.databinding.FragmentLoginBinding
import com.dk.login.utils.PhoneValidator
import com.dk.login.utils.Validator
import com.dk.login.utils.ValidatorType
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

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

    fun buttonEnabledValidator() {
        with(binding) {
            val firstName = etFirstName.text.toString()
            val secondName = etSecondName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            btnLogin.isEnabled = Validator().check(firstName, ValidatorType.NAME) &&
                    Validator().check(secondName, ValidatorType.NAME) &&
                    Validator().check(phoneNumber, ValidatorType.PHONE)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}