package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.dependency.Injector
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.AppExecutor
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * A generic class that defines the contract for synchronization of local data stored in {@link androidx.room.Room} database with a remote data source.
 * The contract shows the various conditions that must be met before an [Entity] can be synchronized with the remote.
 * <p>
 * It extends the {@link Worker} class that performs work synchronously on a background thread provided.
 * The aim is to leverage the {@link Worker} to perform Periodic task of synchronization of local data with the remote data source
 *
 * @param <ResultType>   This is the Generic Entity present in the local database
 * @param <ResponseType> This is the Generic Response Entity from the remote
 */
abstract class WorkerContract<ResultType, ResponseType>(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private var appExecutor: AppExecutor = Injector.provideExecutors()

    override fun doWork(): Result {
        Timber.i("++++++++++++++Executing doWork() ++++++++++++")
        return try {
            val pendingItems: MutableList<ResultType> = loadPendingItemsFromDb()
            when {
                pendingItems.isNotEmpty() -> {
                    //loop through and check if conditions for posting are met
                    for (resultType in pendingItems) {
                        //checks if the Entity has a parent relationship
                        if (hasParentRelationship(resultType)) {
                            if (checkParentStatus(resultType))  //checks if the Parent Entity state is [SYNCED] or [PENDING] before posting
                                postToRemote(resultType) //posts the data to the remote if and only if all posting conditions have been met
                        } else {
                            //Entity does not have a Parent relationship, most likely it is a Parent to other Entities
                            postToRemote(resultType) //posts the data to the remote if and only if all posting conditions have been met
                        }
                    }
                    Result.success()
                }
                else -> {
                    //No pending items were found, so we return Result.retry() so it should be retried with backoff specified.
                    Timber.i("Pending items is empty for entity, retrying worker.....")
                    Result.retry()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we return FAILURE
            Timber.e(ex, "Error executing worker ")
            Result.failure()
        }
    }

    @Throws(java.lang.Exception::class)
    private fun postToRemote(resultType: ResultType) {
        val apiResponse: Response<ResponseType> = createCall(resultType).execute()
        if (apiResponse.isSuccessful) {
            appExecutor.diskIO().execute {
                //process response if necessary
                val processedResponse: ResponseType = processResponse(apiResponse)
                //saves the result/data from network to db, this is done in a background thread
                saveCallResult(processedResponse, resultType)
            }
        } else {
            //posting was not successful, so we return failure
            onPostFailed(resultType)
        }
    }

    /**
     * This queries the local database for items that are in the [pending] state
     * It is these items that are eligible for posting
     *
     * @return List of pending items  from the local database to be posted.
     * The entities must be in the [pending] state
     */
    protected abstract fun loadPendingItemsFromDb(): MutableList<ResultType>

    /**
     * This checks whether the Entity is a Parent or Child.
     * It presupposes that the developer knows which Entity is a Parent or Child
     * as such the concrete class returns the appropriate boolean
     *
     * @param resultType Entity to be checked
     * @return [true] or [false] if the Entity has a child of a parent Entity or not
     */
    protected abstract fun hasParentRelationship(resultType: ResultType): Boolean

    /**
     * This checks the Parent Entity to ascertain if has been synchronized with the remote.
     * For Posting to occur, the Parent Entity must be in the [synced] state
     *
     * @param resultType Entity to be checked
     * @return [true] or [false] if the Parent Entity is [synced] or not
     */
    protected abstract fun checkParentStatus(resultType: ResultType): Boolean

    /**
     * This creates a call to post the local data to the remote
     *
     * @param resultType local data to be posted
     * @return ResponseType from the remote
     */
    protected abstract fun createCall(resultType: ResultType): Call<ResponseType>

    /**
     * This saves the result from the network operation to the database.
     * It basically updates the existing local data state
     *
     * @param responseData the response data from the remote source
     * @param localData    the existing local data
     */
    protected abstract fun saveCallResult(responseData: ResponseType, localData: ResultType)

    protected open fun processResponse(response: Response<ResponseType>): ResponseType {
        return response.body()!!
    }

    /**
     * Called if you need to inform the user of the failure of the operation e.g to make a notification
     *
     * @param resultType
     */
    protected open fun onPostFailed(resultType: ResultType) {}
}