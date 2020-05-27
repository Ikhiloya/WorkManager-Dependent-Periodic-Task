package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.CommunityApp
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.factory.CommunityViewModelFactory
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.repository.CommunityRepository
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.service.CommunityService
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.AppExecutor

object Injector {
    /**
     * Creates an instance of  [CommunityService]
     */
    fun provideEmployeeService(): CommunityService {
        return CommunityApp.instance.getEmployeeService()
    }

    /**
     * Creates an instance of [CommunityRepository]
     */
    private fun provideEmployeeRepository(activity: Activity): CommunityRepository {
        return CommunityRepository(
            activity.application
        )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(activity: Activity): ViewModelProvider.Factory {
        return CommunityViewModelFactory(
            provideEmployeeRepository(activity)
        )
    }

    /**
     * Provides the [AppExecutor] that is then used to get a reference to an Executor
     *
     * @return [AppExecutor]
     */
    fun provideExecutors(): AppExecutor {
        return AppExecutor()
    }
}
