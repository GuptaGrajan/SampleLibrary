package com.example.baseproject.viewModels.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.baseproject.model.pojo.auth.authentication.UserData
import com.example.baseproject.model.repository.local.UserRepository
import com.example.baseproject.model.repository.remote.AuthorizationRepository
import com.example.baseproject.network.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val mApplication: Application,
    val apiService: ApiService,
    private val dataRepository: AuthorizationRepository,
    private val userRepository: UserRepository
) : AndroidViewModel(mApplication) {

    var isRefresh: Boolean = false

    fun saveUserData(data: UserData?) {
//        userRepository.saveUserData(data)

    }

    fun saveToken(token: String?) {
//        userRepository.saveToken(token)

    }

    fun getToken(): String? {
//        return userRepository.getToken()
        return  ""
    }

    fun getUserData(): UserData? {
//        return userRepository.getUser()
        return  null
    }


}


