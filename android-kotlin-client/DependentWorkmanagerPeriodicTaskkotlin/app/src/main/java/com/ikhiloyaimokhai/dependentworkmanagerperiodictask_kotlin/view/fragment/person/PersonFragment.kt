package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.view.fragment.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.R
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.adapter.PersonAdapter
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency.Injector
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel.EmployeeViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PersonFragment : Fragment(), View.OnClickListener, PersonAdapter.ListItemClickListener {
    private lateinit var people: MutableList<Person>
    private lateinit var personAdapter: PersonAdapter
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        employeeViewModel = ViewModelProviders.of(
            requireActivity(),
            Injector.provideViewModelFactory(requireActivity())
        )[EmployeeViewModel::class.java]
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val personRecycler: RecyclerView = requireView().findViewById(R.id.personRecycler)
        val addFab: FloatingActionButton = requireView().findViewById(R.id.addFab)
        personRecycler.setHasFixedSize(true)
        personRecycler.layoutManager = LinearLayoutManager(context)
        people = mutableListOf()
        personAdapter =
            PersonAdapter(
                people,
                this
            )
        personRecycler.adapter = personAdapter
        addFab.setOnClickListener(this)
        getPeople()
    }

    private fun getPeople() {
        employeeViewModel.people.observe(viewLifecycleOwner, Observer { data ->
            people.clear()
            people.addAll(data)
            personAdapter.notifyDataSetChanged()
            Timber.i("Data fetched locally%s", people.toString())
        })
    }


    override fun onListItemClick(person: Person, adapterPosition: Int) {
        Timber.i("person%s", person.toString())
        var personId: String? = null
        when {
            null != person.id -> personId = person.id.toString()
        }
        Timber.i("personId%s", personId)
        Timber.i("personLocalId%s", person.localId!!)
        Navigation.findNavController(requireView()).navigate(
            PersonFragmentDirections.actionPersonFragmentToPersonDetailFragment(
                personId,
                person.firstName,
                person.lastName,
                person.localId
            )
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addFab -> {
                Navigation.findNavController(requireView()).navigate(
                    PersonFragmentDirections.actionPersonFragmentToAddPersonFragment()
                )
            }
        }
    }
}

