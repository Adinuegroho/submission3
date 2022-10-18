package com.example.submission3aplikasigithubuser.ui.detail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3aplikasigithubuser.databinding.FragmentFollowingBinding
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import com.example.submission3aplikasigithubuser.model.ModelUser


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = activity?.intent?.getParcelableExtra<ModelUser>(EXTRA_DATA) as ModelUser
        val login = data.login

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.findFollowing(login)
        viewModel.items.observe(viewLifecycleOwner) { items ->
            showRecyclerList(items)
        }

        binding.rvFollowing.setHasFixedSize(true)
    }

    private fun showRecyclerList(items: List<FollowResponseItem>) {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.layoutManager = layoutManager
        val followingAdapter = FollowingAdapter(items)
        binding.rvFollowing.adapter = followingAdapter
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)
    }

    companion object {
        const val EXTRA_DATA = "Extra_Data"
    }

}