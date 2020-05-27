package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.beneficiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.beneficiary.BeneficiaryDetailFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class BeneficiaryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beneficiary_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val firstNameTxt: TextView = requireView().findViewById(R.id.firstName)
        val lastNameTxt: TextView = requireView().findViewById(R.id.lastName)
        val relationshipTxt: TextView = requireView().findViewById(R.id.relationship)
        val args = BeneficiaryDetailFragmentArgs.fromBundle(requireArguments())
        val firstName = args.firstName
        val lastName = args.lastName
        val relationship = args.relationship

        //set views
        firstNameTxt.text = firstName
        lastNameTxt.text = lastName
        relationshipTxt.text = relationship
    }
}
