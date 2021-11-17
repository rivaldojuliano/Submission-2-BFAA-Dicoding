package dev.codewithrivaldo.githubuserapp.view.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.codewithrivaldo.githubuserapp.R
import dev.codewithrivaldo.githubuserapp.databinding.FragmentFollowerBinding
import dev.codewithrivaldo.githubuserapp.model.data.source.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.view.adapter.FollowAdapter
import dev.codewithrivaldo.githubuserapp.viewmodel.FollowersViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private val viewModel by viewModels<FollowersViewModel>()

    private var progressBar: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        showFollowerRecyclerView()
        getFollowers()
    }

    private fun showFollowerRecyclerView() {
        binding.apply {
            rvFollower.setHasFixedSize(true)
            adapter = FollowAdapter()

            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.adapter = adapter
        }
    }

    private fun getFollowers() {
        val followers = activity?.intent?.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

        followers.login?.let {
            showProgress()
            viewModel.getFollowers(it)
        }

        viewModel.followers.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.rvFollower.visibility = View.GONE
                showMessage(true)
                dismissProgress()
            } else {
                binding.rvFollower.visibility = View.VISIBLE
                showMessage(false)
                adapter.setData(it)
                dismissProgress()
            }
        })
    }

    private fun initView() {
        progressBar = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.progress_bar, null)

        progressBar?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(R.color.transparent)
        }
    }


    private fun showProgress() {
        progressBar?.show()
    }

    private fun dismissProgress() {
        progressBar?.dismiss()
    }

    private fun showMessage(visible: Boolean) {
        if (visible) {
            binding.tvNotHaveFollowers.visibility = View.VISIBLE
            binding.tvNotHaveFollowers.text = resources.getString(R.string.not_have_followers)
        } else {
            binding.tvNotHaveFollowers.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_USER = "extra_user"
    }
}