package dev.codewithrivaldo.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.codewithrivaldo.githubuserapp.model.api.ApiConfig
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel(){

    private val _detailUser = MutableLiveData<ItemsItem>()
    val detailUser: LiveData<ItemsItem> = _detailUser

    fun getUserDetail(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(call: Call<ItemsItem>, response: Response<ItemsItem>) {
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}