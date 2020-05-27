package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.service;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.BuildConfig;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommunityService {
    @POST(BuildConfig.PERSON)
    Call<Person> createPerson(@Body Person person);

    @POST(BuildConfig.PERSON)
    Call<List<Person>> getPeople();

    @POST(BuildConfig.BENEFICIARY)
    Call<Beneficiary> createBeneficiary(@Body Beneficiary beneficiary);

    @POST(BuildConfig.BENEFICIARY)
    Call<List<Beneficiary>> getBeneficiaries();
}
