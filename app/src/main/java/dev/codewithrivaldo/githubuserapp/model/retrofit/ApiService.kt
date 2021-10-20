package dev.codewithrivaldo.githubuserapp.model.retrofit

import dev.codewithrivaldo.githubuserapp.BuildConfig
import dev.codewithrivaldo.githubuserapp.model.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<UserResponse>
}