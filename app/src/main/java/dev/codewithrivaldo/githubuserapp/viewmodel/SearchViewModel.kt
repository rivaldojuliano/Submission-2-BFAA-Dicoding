package dev.codewithrivaldo.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.model.data.remote.UserResponse
import dev.codewithrivaldo.githubuserapp.model.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private val _items = MutableLiveData<ArrayList<ItemsItem>>()
    val items: LiveData<ArrayList<ItemsItem>> = _items

     fun findUser(q: String) {
        val client = ApiConfig.getApiService().getSearchUser(q)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _items.value = response.body()?.items
                } else {
                    Log.d(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}