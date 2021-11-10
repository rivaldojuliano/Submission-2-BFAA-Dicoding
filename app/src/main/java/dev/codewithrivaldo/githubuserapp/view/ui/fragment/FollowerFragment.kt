package dev.codewithrivaldo.githubuserapp.view.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.codewithrivaldo.githubuserapp.R
import dev.codewithrivaldo.githubuserapp.databinding.FragmentFollowerBinding
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.view.adapter.FollowAdapter
import dev.codewithrivaldo.githubuserapp.viewmodel.FollowersViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private lateinit var viewModel: FollowersViewModel

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
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

        followers.login?.let {
            showProgress()
            viewModel.getFollowers(it)
        }

        viewModel.followers.observe(viewLifecycleOwner, {
            if (it != null) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_USER = "extra_user"
    }
}