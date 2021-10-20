package dev.codewithrivaldo.githubuserapp.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.codewithrivaldo.githubuserapp.databinding.ItemRowUserBinding
import dev.codewithrivaldo.githubuserapp.model.data.ItemsItem

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<ItemsItem>()

    fun setData(items: ArrayList<ItemsItem>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(itemsItem: ItemsItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(itemsItem.avatarUrl)
                    .apply(RequestOptions().override(100, 100))
                    .into(civImage)

                tvUsername.text = itemsItem.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

}