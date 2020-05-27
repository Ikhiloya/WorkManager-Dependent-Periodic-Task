package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.worker

import android.content.Context
import androidx.work.WorkerParameters
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.CommunityApp
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDao
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.service.CommunityService
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.WorkerUtils
import retrofit2.Call
import timber.log.Timber

class PersonWorker(context: Context, workerParams: WorkerParameters) :
    WorkerContract<Person, Person>(context, workerParams) {
    private val tag: String = PersonWorker::class.java.simpleName
    private var communityService: CommunityService
    private var communityDao: CommunityDao

    init {
        val communityApp: CommunityApp = CommunityApp.instance
        communityService = communityApp.getEmployeeService()
        communityDao = communityApp.getEmployeeDao()
    }

    override fun loadPendingItemsFromDb(): MutableList<Person> {
        Timber.i("loading pending People from db..........")
        return communityDao.findPendingPeople()
    }

    override fun hasParentRelationship(resultType: Person): Boolean {
        //return false since this is the parent Entity

        return false
    }

    override fun checkParentStatus(resultType: Person): Boolean {
        //return false since this is the parent Entity
        return false
    }

    override fun createCall(resultType: Person): Call<Person> {
        Timber.i("%s: creating call...", tag)
        return communityService.createPerson(resultType)
    }

    /* updates the local data with the remote ID as well as the state to [SYNCED] **/
    override fun saveCallResult(responseData: Person, localData: Person) {
        localData.id = responseData.id
        localData.state = Constant.SYNCED
        communityDao.savePerson(localData)
        WorkerUtils.makeStatusNotification(
            """${localData.firstName} ${localData.lastName} synced successfully""",
            applicationContext
        )
    }

    override fun onPostFailed(resultType: Person) {
        WorkerUtils.makeStatusNotification(
            """failed to save ${resultType.firstName} ${resultType.lastName}""",
            applicationContext
        )
    }
}