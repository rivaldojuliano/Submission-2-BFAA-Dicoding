package dev.codewithrivaldo.githubuserapp.model.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("items")
	val items: ArrayList<ItemsItem>
) : Parcelable

@Parcelize
data class ItemsItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("login")
	val login: String?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("public_repos")
	val publicRepository: Int?,

	@field:SerializedName("followers")
	val follower: Int?,

	@field:SerializedName("following")
	val following: Int?
) : Parcelable