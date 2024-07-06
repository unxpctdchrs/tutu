package com.tuners.tutu.ui.main_students.message

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.tuners.tutu.adapters.OnlineMentorsAdapter
import com.tuners.tutu.data.local.pref.UserModel
import com.tuners.tutu.data.remote.response.UserDetailsItem
import com.tuners.tutu.data.remote.response.UserDetailsResponse
import com.tuners.tutu.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.BOTTOM)
        exitTransition = Slide(Gravity.TOP)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyUserModel = UserDetailsResponse(
            false,
            userDetails = listOf( UserDetailsItem(
                "23123",
                "12312312",
                "12312321",
                0,
                "m",
                "adasdasd",
                "adasdasd",
                true,
                "123123",
                "adasdasdasd",
                "Budi",
                0.0f,
            ))
        )
        loadUsers(dummyUserModel)
    }

    private fun loadUsers(users: UserDetailsResponse) {
        val adapter = OnlineMentorsAdapter(false)
        adapter.submitList(users.userDetails)
        binding?.rvUsers?.adapter = adapter
        binding?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}