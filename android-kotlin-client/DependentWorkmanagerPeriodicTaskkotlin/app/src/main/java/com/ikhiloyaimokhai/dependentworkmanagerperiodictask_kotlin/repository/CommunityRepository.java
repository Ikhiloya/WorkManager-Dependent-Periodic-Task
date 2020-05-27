package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDao;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDatabase;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;

import java.util.List;

public class CommunityRepository {
    private static String LOG_TAG = CommunityRepository.class.getSimpleName();
    private final Application application;
    private static CommunityDao communityDao;


    public CommunityRepository(Application application) {
        this.application = application;
        CommunityDatabase db = CommunityDatabase.getDatabase(application);
        communityDao = db.employeeDao();
    }

    public LiveData<List<Person>> getPeople() {
        return communityDao.findPerson();
    }

    public void savePeople(Person person) {
        communityDao.savePerson(person);
    }

    public LiveData<List<Beneficiary>> getBeneficiariesByStaff(int staffRoomId) {
        return communityDao.findBeneficiariesByPersonLocalId(staffRoomId);
    }

    public void saveBeneficiary(Beneficiary beneficiary) {
        communityDao.saveBeneficiary(beneficiary);
    }

    public Application getApplication() {
        return application;
    }

}
