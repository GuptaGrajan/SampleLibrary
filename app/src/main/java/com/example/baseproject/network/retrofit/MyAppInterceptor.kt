package com.example.baseproject.network.retrofit



import com.example.baseproject.utils.Const
import com.example.baseproject.utils.Prefs
import com.example.baseproject.utils.extensions.log
import okhttp3.Interceptor
import okhttp3.Response


class MyAppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//        log("token ${Prefs.with(appContext)!!.getString(Const.USER_TOKEN, "")}")
        var request = chain.request()
        val headers = request.headers.newBuilder()
            .add("Content-Type", "application/json")
            .add("Accept", "application/json")
            .add(
                "Authorization",
                "Bearer ${""}"
            )
            .build()

        request = request.newBuilder().headers(headers).build()

        return chain.proceed(request)
    }
}