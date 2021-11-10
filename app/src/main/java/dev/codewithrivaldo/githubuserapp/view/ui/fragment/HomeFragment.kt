package dev.codewithrivaldo.githubuserapp.view.ui.fragment

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.codewithrivaldo.githubuserapp.R
import dev.codewithrivaldo.githubuserapp.databinding.FragmentHomeBinding
import dev.codewithrivaldo.githubuserapp.model.data.remote.ItemsItem
import dev.codewithrivaldo.githubuserapp.view.adapter.UserAdapter
import dev.codewithrivaldo.githubuserapp.view.ui.activity.DetailActivity
import dev.codewithrivaldo.githubuserapp.viewmodel.SearchViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private val viewModel by viewModels<SearchViewModel>()

    private var progressBar: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        searchUser()
        showRecyclerView()
        getUser()

        adapter.setOnItemClickCallback(
            object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(itemsItem: ItemsItem) {
                    showSelectedUser(itemsItem)
                }
            })
    }

    private fun searchUser() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = activity?.findViewById(R.id.searchView) as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgress()
                viewModel.findUser(query)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun getUser() {
        viewModel.items.observe(requireActivity(), {
            if (it != null) {
                adapter.setData(it)
                dismissProgress()
            }
        })
    }

    private fun showRecyclerView() {
        binding.apply {
            adapter = UserAdapter()
            adapter.notifyDataSetChanged()

            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    private fun showSelectedUser(data: ItemsItem) {
        val mIntent = Intent(context, DetailActivity::class.java)
        mIntent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(mIntent)
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
}