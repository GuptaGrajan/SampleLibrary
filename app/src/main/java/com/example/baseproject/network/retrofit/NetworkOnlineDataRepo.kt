package com.example.baseproject.network.retrofit

import androidx.annotation.MainThread
import com.example.baseproject.network.retrofit.DataResult
import com.example.baseproject.network.HTTPStatus
import com.example.baseproject.utils.extensions.log
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response


abstract class NetworkOnlineDataRepo<RESULT, REQUEST> {


    fun asFlow() = flow {
        emit(DataResult.Loading())
        try {
            val apiResponse = fetchDataFromRemoteSource()
            val data = apiResponse.body()

            if (apiResponse.isSuccessful) {
                log("MainData=> $data \n ${apiResponse.body()}")
                emit(DataResult.Success(data, apiResponse.code()))
            } else {
                when (apiResponse.code()) {
                    HTTPStatus.INVALID_ACCOUNT_STATUS -> {
                        // logout the user
                        emit(DataResult.Logout(HTTPStatus.INVALID_ACCOUNT_STATUS))
                    }

                    else -> {
                        emit(
                            DataResult.Failure(
                                getErrorMsg(apiResponse.errorBody()),
                                apiResponse.code()
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            emit(
                DataResult.Failure(
                    e.message
                )
            )

        }

    }


    private fun getErrorMsg(responseBody: ResponseBody?): String {

        return try {
            if (responseBody != null) {
                val jsonObject = JSONObject(responseBody.string())
                jsonObject.getString("message")
            } else {
                ""
            }

        } catch (e: Exception) {
            e.message!!
        }

    }

    @MainThread
    protected abstract suspend fun fetchDataFromRemoteSource(): Response<REQUEST>


}