package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.person

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency.Injector
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Helper
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel.EmployeeViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddPersonFragment : Fragment(), View.OnClickListener {
    private lateinit var firstNameInput: TextInputLayout
    private lateinit var lastNameInput: TextInputLayout
    private lateinit var employeeViewModel: EmployeeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_add_person, container, false)
        employeeViewModel = ViewModelProviders.of(
            requireActivity(),
            Injector.provideViewModelFactory(requireActivity())
        )[EmployeeViewModel::class.java]
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        firstNameInput = requireView().findViewById(R.id.firstName)
        lastNameInput = requireView().findViewById(R.id.lastName)
        val saveButton: Button = requireView().findViewById(R.id.saveButton)
        saveButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        validateFields()
    }

    private fun validateFields() {
        val firstName: String = Helper.getString(firstNameInput)
        val lastName: String = Helper.getString(lastNameInput)
        when {
            TextUtils.isEmpty(firstName) -> {
                firstNameInput.editText?.error = resources.getString(R.string.first_name_required)
            }
            TextUtils.isEmpty(lastName) -> {
                lastNameInput.editText?.error = resources.getString(R.string.last_name_required)
            }
            else -> {
                savePerson(
                    Person(
                        firstName,
                        lastName,
                        Constant.PENDING
                    )
                )
            }
        }
    }

    private fun savePerson(person: Person) {
        Injector.provideExecutors().diskIO().execute { employeeViewModel.savePerson(person) }
        Navigation.findNavController(requireView()).navigateUp()
    }
}

