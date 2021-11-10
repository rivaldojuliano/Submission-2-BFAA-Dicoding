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
import dev.codewithrivaldo.githubuserapp.databinding.FragmentFollowingBinding
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.view.adapter.FollowAdapter
import dev.codewithrivaldo.githubuserapp.viewmodel.FollowingViewHolder

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FollowAdapter
    private val viewModel by viewModels<FollowingViewHolder>()

    private var progressBar: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        showFollowingRecyclerView()
        getFollowing()
    }

    private fun showFollowingRecyclerView() {
        binding.apply {
            rvFollowing.setHasFixedSize(true)
            adapter = FollowAdapter()

            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }
    }

    private fun getFollowing() {
        val following = activity?.intent?.getParcelableExtra<ItemsItem>(EXTRA_USER) as ItemsItem

        following.login?.let {
            showProgress()
            viewModel.getFollowing(it)
        }

        viewModel.following.observe(viewLifecycleOwner, {
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
        const val EXTRA_USER = "extra_user"
    }

}