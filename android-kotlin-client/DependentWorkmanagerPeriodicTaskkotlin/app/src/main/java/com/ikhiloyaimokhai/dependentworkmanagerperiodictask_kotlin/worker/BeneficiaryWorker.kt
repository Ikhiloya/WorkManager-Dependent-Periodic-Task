package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.CommunityApp
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDao
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.service.CommunityService
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.WorkerUtils
import retrofit2.Call
import timber.log.Timber

class BeneficiaryWorker(context: Context, workerParams: WorkerParameters) :
    WorkerContract<Beneficiary, Beneficiary>(context, workerParams) {
    private val tag: String = BeneficiaryWorker::class.java.simpleName
    private var communityService: CommunityService
    private var communityDao: CommunityDao

    init {
        val communityApp: CommunityApp = CommunityApp.instance
        communityService = communityApp.getEmployeeService()
        communityDao = communityApp.getEmployeeDao()
    }

    override fun loadPendingItemsFromDb(): MutableList<Beneficiary> {
        Timber.i("loading pending Beneficiaries from db..........")
        return communityDao.findPendingBeneficiaries()
    }

    override fun hasParentRelationship(resultType: Beneficiary): Boolean {
        //return true since it is a child Entity
        return true
    }

    override fun checkParentStatus(resultType: Beneficiary): Boolean {
        Timber.i("%s: in checkParentStatus.........", tag)
        val person: Person = communityDao.findPeopleByLocalId(resultType.personLocalId!!)
        Timber.i(
            "in check Parent Status for Beneficiary.........%s",
            person.state.equals(Constant.SYNCED, ignoreCase = true)
        )
        return person.state.equals(Constant.SYNCED, ignoreCase = true)
    }

    override fun createCall(resultType: Beneficiary): Call<Beneficiary> {
        Timber.i("%s: creating call...", tag)
        //get the remote id of the parent entity
        val person = communityDao.findPeopleByLocalId(resultType.personLocalId!!)
        resultType.personId = person.id
        return communityService.createBeneficiary(resultType)
    }

    /* updates the local data with the remote ID as well as the state to [SYNCED] **/
    override fun saveCallResult(responseData: Beneficiary, localData: Beneficiary) {
        localData.id = responseData.id
        localData.personId = responseData.personId
        localData.state = Constant.SYNCED
        communityDao.saveBeneficiary(localData)
        WorkerUtils.makeStatusNotification(
            """${localData.firstName} ${localData.lastName} synced successfully""",
            applicationContext
        )
    }

    override fun onPostFailed(resultType: Beneficiary) {
        WorkerUtils.makeStatusNotification(
            """failed to save ${resultType.firstName} ${resultType.lastName}""",
            applicationContext
        )
    }
}