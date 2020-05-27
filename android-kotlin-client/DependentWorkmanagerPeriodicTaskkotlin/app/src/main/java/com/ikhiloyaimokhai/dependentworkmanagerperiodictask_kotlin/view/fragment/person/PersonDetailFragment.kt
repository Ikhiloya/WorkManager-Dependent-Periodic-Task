package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PersonDetailFragment : Fragment(), View.OnClickListener {
    private var fullName: String? = null
    private var personLocalId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val personIdTxt: TextView = requireView().findViewById(R.id.personId)
        val firstNameTxt: TextView = requireView().findViewById(R.id.firstName)
        val lastNameTxt: TextView = requireView().findViewById(R.id.lastName)
        val addFab: FloatingActionButton = requireView().findViewById(R.id.addFab)
        val blank = "--"
        val args = PersonDetailFragmentArgs.fromBundle(requireArguments())
        Timber.i(" args.personId() %s", args.personId)
        val personId =
            (if (args.personId == null || args.personId.equals("null")) blank else args.personId!!)
        val firstName = args.firstName
        val lastName = args.lastName
        fullName = "$firstName $lastName"
        personLocalId = args.personLocalId
        Timber.i(" args.personLocalId() %s", args.personLocalId)

        //set views
        personIdTxt.text = personId
        firstNameTxt.text = firstName
        lastNameTxt.text = lastName
        addFab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Navigation.findNavController(requireView()).navigate(
            PersonDetailFragmentDirections
                .actionPersonDetailFragmentToBeneficiaryFragment(personLocalId!!, fullName!!)
        )
    }
}
