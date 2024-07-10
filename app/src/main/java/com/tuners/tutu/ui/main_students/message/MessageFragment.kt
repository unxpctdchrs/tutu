package com.tuners.tutu.ui.main_students.message

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.tuners.tutu.adapters.UserChatAdapter
import com.tuners.tutu.data.remote.response.ChatsResponse
import com.tuners.tutu.databinding.FragmentMessageBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_students.MainViewModel

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding

    private val messageViewModel by viewModels<MessageViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

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

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            messageViewModel.getChats(user.userId)
        }

        messageViewModel.chats.observe(viewLifecycleOwner) { chats ->
            loadUsers(chats)
        }
    }

    private fun loadUsers(users: ChatsResponse) {
        val adapter = UserChatAdapter()
        adapter.submitList(users.chats)
        binding?.rvUsers?.adapter = adapter
        binding?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}