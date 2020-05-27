package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.beneficiary

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency.Injector
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Helper
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel.EmployeeViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddBeneficiaryFragment : Fragment(), View.OnClickListener {
    private lateinit var firstNameInput: TextInputLayout
    private lateinit var lastNameInput: TextInputLayout
    private lateinit var relationshipSpinner: Spinner
    private lateinit var employeeViewModel: EmployeeViewModel
    private var personLocalId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_add_beneficiary, container, false)
        employeeViewModel = ViewModelProviders.of(
            requireActivity(),
            Injector.provideViewModelFactory(requireActivity())
        )[EmployeeViewModel::class.java]
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = AddBeneficiaryFragmentArgs.fromBundle(requireArguments())
        personLocalId = args.personLocalId
        firstNameInput = requireView().findViewById(R.id.firstName)
        lastNameInput = requireView().findViewById(R.id.lastName)
        relationshipSpinner = requireView().findViewById(R.id.relationshipSpinner)
        val saveButton = requireView().findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.saveButton) {
            validateFields()
        }
    }

    private fun validateFields() {
        val firstName: String = Helper.getString(firstNameInput)
        val lastName: String = Helper.getString(lastNameInput)
        val relationship = relationshipSpinner.selectedItem as String
        when {
            TextUtils.isEmpty(firstName) -> {
                firstNameInput.editText?.error = resources.getString(R.string.first_name_required)
            }
            TextUtils.isEmpty(lastName) -> {
                lastNameInput.editText?.error = resources.getString(R.string.last_name_required)
            }
            else -> {
                saveBeneficiary(
                    Beneficiary(
                        personLocalId,
                        firstName,
                        lastName,
                        relationship,
                        Constant.PENDING
                    )
                )
            }
        }
    }

    private fun saveBeneficiary(beneficiary: Beneficiary) {
        Injector.provideExecutors().diskIO().execute { employeeViewModel.saveBeneficiary(beneficiary) }
        Navigation.findNavController(requireView()).navigateUp()
    }
}
