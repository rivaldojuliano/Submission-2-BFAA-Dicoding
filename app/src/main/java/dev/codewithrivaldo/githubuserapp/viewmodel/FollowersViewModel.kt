package dev.codewithrivaldo.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.codewithrivaldo.githubuserapp.model.data.source.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.model.data.source.remote.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private val _followers = MutableLiveData<ArrayList<ItemsItem>>()
    val followers: LiveData<ArrayList<ItemsItem>> = _followers

    fun getFollowers(username: String) {
        val client = ApiConfig.getApiService().getFollowers(username)

        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowersViewModel"
    }
}