package com.example.baseproject.utils.extensions

import android.util.Log


fun log(string: String?) {
    /* if (BuildConfig.DEBUG) {

     }*/
    Log.e("BaseActivity", string!!)

}