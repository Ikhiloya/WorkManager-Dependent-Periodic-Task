package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.factory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.repository.CommunityRepository;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.viewmodel.EmployeeViewModel;

public class CommunityViewModelFactory implements ViewModelProvider.Factory {
    private final CommunityRepository communityRepository;

    public CommunityViewModelFactory(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(EmployeeViewModel.class))
            return (T) new EmployeeViewModel(communityRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}

