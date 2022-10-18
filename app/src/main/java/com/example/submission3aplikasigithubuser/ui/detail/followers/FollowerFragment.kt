package com.example.submission3aplikasigithubuser.ui.detail.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3aplikasigithubuser.databinding.FragmentFollowerBinding
import com.example.submission3aplikasigithubuser.model.FollowResponseItem
import com.example.submission3aplikasigithubuser.model.ModelUser

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = activity?.intent?.getParcelableExtra<ModelUser>(EXTRA_DATA) as ModelUser
        val login  = data.login

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
        viewModel.findFollower(login)
        viewModel.items.observe(viewLifecycleOwner) { items ->
            showRecyclerList(items)
        }

        binding.rvFollower.setHasFixedSize(true)
    }

    private fun showRecyclerList(items: List<FollowResponseItem>) {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollower.layoutManager = layoutManager
        val followerAdapter = FollowerAdapter(items)
        binding.rvFollower.adapter = followerAdapter
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollower.addItemDecoration(itemDecoration)
    }

    companion object {
        const val EXTRA_DATA = "Extra_Data"
    }
}