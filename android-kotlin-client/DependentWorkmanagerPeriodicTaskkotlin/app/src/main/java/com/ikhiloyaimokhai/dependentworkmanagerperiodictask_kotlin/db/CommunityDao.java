package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;

import java.util.List;

@Dao
public interface CommunityDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePerson(Person person);

    @Transaction
    @Query("SELECT *  FROM Person where localId=:localId")
    Person findPeopleByLocalId(int localId);


    @Transaction
    @Query("SELECT * from Person where state= 'pending'")
    List<Person> findPendingPeople();

    @Transaction
    @Query("SELECT * from Person")
    LiveData<List<Person>> findPerson();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveBeneficiary(Beneficiary beneficiary);

    @Transaction
    @Query("SELECT *  FROM Beneficiary where localId=:localId")
    Beneficiary findBeneficiaryByRLocalId(int localId);

    @Transaction
    @Query("SELECT *  FROM Beneficiary where  personLocalId=:personLocalId")
    Beneficiary findBeneficiaryByPersonLocalId(int personLocalId);

    @Transaction
    @Query("SELECT * from Beneficiary where state= 'pending'")
    List<Beneficiary> findPendingBeneficiaries();

    @Transaction
    @Query("SELECT * from Beneficiary where personLocalId=:personLocalId")
    LiveData<List<Beneficiary>> findBeneficiariesByPersonLocalId(int personLocalId);
}
