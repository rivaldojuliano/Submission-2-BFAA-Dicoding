package dev.codewithrivaldo.githubuserapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.codewithrivaldo.githubuserapp.databinding.ItemRowUserBinding
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem

class FollowAdapter: RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private val listUser = ArrayList<ItemsItem>()

    fun setData(items: ArrayList<ItemsItem>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}