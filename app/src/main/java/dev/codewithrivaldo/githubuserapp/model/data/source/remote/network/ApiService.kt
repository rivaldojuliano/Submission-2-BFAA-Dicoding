package dev.codewithrivaldo.githubuserapp.model.data.source.remote.network

import dev.codewithrivaldo.githubuserapp.BuildConfig
import dev.codewithrivaldo.githubuserapp.model.data.source.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.model.data.source.remote.UserResponse
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

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}