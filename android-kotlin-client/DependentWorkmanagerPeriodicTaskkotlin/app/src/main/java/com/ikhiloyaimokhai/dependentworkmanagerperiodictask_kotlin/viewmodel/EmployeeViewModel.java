package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.repository.CommunityRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private CommunityRepository communityRepository;


    public EmployeeViewModel(CommunityRepository communityRepository) {
        super(communityRepository.getApplication());
        this.communityRepository = communityRepository;
    }

    public void savePerson(Person person) {
        communityRepository.savePeople(person);
    }

    public LiveData<List<Person>> getPeople() {
        return communityRepository.getPeople();
    }

    public void saveBeneficiary(Beneficiary beneficiary) {
        communityRepository.saveBeneficiary(beneficiary);
    }

    public LiveData<List<Beneficiary>> getBeneficiariesByStaff(int staffRoomId) {
        return communityRepository.getBeneficiariesByStaff(staffRoomId);
    }
}
