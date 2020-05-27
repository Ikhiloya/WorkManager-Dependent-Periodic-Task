package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin

import android.app.Application
import androidx.work.*
import com.google.gson.GsonBuilder
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDao
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db.CommunityDatabase
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.service.CommunityService
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.worker.BeneficiaryWorker
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.worker.PersonWorker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit

open class CommunityApp : Application() {
    private val baseUrl = BuildConfig.BASE_URL

    companion object {
        lateinit var instance: CommunityApp
        lateinit var communityService: CommunityService
        lateinit var communityDao: CommunityDao
        lateinit var workManager: WorkManager
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Gson Builder
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
        val gson = gsonBuilder.create()
        Timber.plant(DebugTree())

        // HttpLoggingInterceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message: String? -> Timber.i(message) }
        )
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //   OkHttpClient
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        //Retrofit
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        communityService = retrofit.create(CommunityService::class.java)
        communityDao = CommunityDatabase.getDatabase(applicationContext).employeeDao()
        workManager = WorkManager.getInstance(this)
    }

    fun startWorkers() {
        startPersonWorker()
        startBeneficiaryWorker()
    }


    fun getEmployeeService(): CommunityService {
        return communityService
    }

    fun getEmployeeDao(): CommunityDao {
        return communityDao
    }

    private fun getWorkManager(): WorkManager? {
        return workManager
    }


    private fun startPersonWorker() {
        Timber.i("starting Person worker............")
        // Create Network constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(PersonWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag(Constant.TAG_SYNC_PERSON)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                ).build()
        getWorkManager()!!.enqueueUniquePeriodicWork(
            Constant.SYNC_PERSON_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }

    private fun startBeneficiaryWorker() {
        Timber.i("starting Beneficiary worker............")
        // Create Network constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork = PeriodicWorkRequest.Builder(BeneficiaryWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag(Constant.TAG_SYNC_BENEFICIARY)
            .setConstraints(constraints) // setting a backoff on case the work needs to retry
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        getWorkManager()!!.enqueueUniquePeriodicWork(
            Constant.SYNC_BENEFICIARY_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }
}