package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.beneficiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.adapter.BeneficiaryAdapter
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency.Injector
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel.EmployeeViewModel
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class BeneficiaryFragment : Fragment(), View.OnClickListener,
    BeneficiaryAdapter.ListItemClickListener {
    private lateinit var beneficiaries: MutableList<Beneficiary>
    private lateinit var beneficiaryAdapter: BeneficiaryAdapter
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_beneficiary, container, false)
        employeeViewModel = ViewModelProviders.of(
            requireActivity(),
            Injector.provideViewModelFactory(requireActivity())
        )[EmployeeViewModel::class.java]
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        assert(arguments != null)
        val beneficiaryRecycler: RecyclerView = requireView().findViewById(R.id.beneficiaryRecycler)
        val addFab: FloatingActionButton = requireView().findViewById(R.id.addFab)
        val fullNameTxt: TextView = requireView().findViewById(R.id.fullName)
        val args = BeneficiaryFragmentArgs.fromBundle(requireArguments())
        val fullName = args.fullName
        val personLocalId = args.personLocalId
        Timber.i("personLocalId %s", personLocalId)

        fullNameTxt.text = fullName
        beneficiaryRecycler.setHasFixedSize(true)
        beneficiaryRecycler.layoutManager = LinearLayoutManager(context)
        beneficiaries = ArrayList()
        beneficiaryAdapter = BeneficiaryAdapter(beneficiaries, this)
        beneficiaryRecycler.adapter = beneficiaryAdapter
        addFab.setOnClickListener(this)

        getBeneficiaries(personLocalId)
    }

    private fun getBeneficiaries(staffLocalId: Int) {
        employeeViewModel.getBeneficiariesByStaff(staffLocalId)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer { data ->
                beneficiaries.clear()
                beneficiaries.addAll(data)
                beneficiaryAdapter.notifyDataSetChanged()
                Timber.i("Data fetched locally %s", beneficiaries.toString())
            })
    }

    override fun onListItemClick(beneficiarys: Beneficiary?, adapterPosition: Int) {
        Navigation.findNavController(requireView()).navigate(
            BeneficiaryFragmentDirections.actionBeneficiaryFragmentToBeneficiaryDetailFragment(
                beneficiarys!!.firstName,
                beneficiarys.lastName,
                beneficiarys.relationship
            )
        )
    }

    override fun onClick(v: View?) {
        val args = BeneficiaryFragmentArgs.fromBundle(requireArguments())
        val personLocalID = args.personLocalId
        Navigation.findNavController(requireView()).navigate(
            BeneficiaryFragmentDirections.actionBeneficiaryFragmentToAddBeneficiaryFragment(
                personLocalID
            )
        )
    }


}
