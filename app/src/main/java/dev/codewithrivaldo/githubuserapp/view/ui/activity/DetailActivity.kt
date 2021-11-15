package dev.codewithrivaldo.githubuserapp.view.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.codewithrivaldo.githubuserapp.R
import dev.codewithrivaldo.githubuserapp.databinding.ActivityDetailBinding
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.view.adapter.SectionPagerAdapter
import dev.codewithrivaldo.githubuserapp.viewmodel.UserDetailViewModel
import kotlin.math.abs

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCollapsingToolbar()
        getDetailUser()
        setTabLayout()
    }

    private fun setCollapsingToolbar() {
        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.toolbarLayout.title = user.login
            } else {
                binding.toolbarLayout.title = ""
            }
        })
    }

    private fun getDetailUser() {
        val user = intent.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

        user.login?.let { viewModel.getUserDetail(it) }

        viewModel.detailUser.observe(this, {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .apply(RequestOptions().override(100,100))
                        .into(civImage)

                    tvUsername.text = getString(R.string.login, it.login)
                    tvName.text = getString(R.string.name, it.name ?: "-")
                    tvLocation.text = getString(R.string.location, it.location ?: "-")
                    tvCompany.text = getString(R.string.company, it.company ?: "-")
                    tvRepository.text = getString(R.string.public_repository, it.publicRepository.toString())
                    tvFollower.text = getString(R.string.follower, it.follower.toString())
                    tvFollowing.text = getString(R.string.following, it.following.toString())
                }
            }
        })
    }

    private fun setTabLayout() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}