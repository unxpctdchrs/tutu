package com.tuners.tutu.ui.main_students.message

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        binding?.svUser?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("OnQueryTextChange", newText.toString())
                return true
            }

        })

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            messageViewModel.getChats(user.userId)
        }

        messageViewModel.chatLoading.observe(viewLifecycleOwner) { isLoading ->
            setLoading(isLoading)

            if (!isLoading) {
                messageViewModel.chats.observe(viewLifecycleOwner) { chats ->
                    loadUsers(chats)
                }
            }
        }

        binding?.btnBack?.setOnClickListener {
            val toHome = MessageFragmentDirections.actionNavigationMessageToNavigationHome()
            findNavController().navigate(toHome)
        }
    }

    private fun loadUsers(users: ChatsResponse) {
        val adapter = UserChatAdapter(false)
        adapter.submitList(users.chats)
        binding?.rvUsers?.adapter = adapter
        binding?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setLoading(b: Boolean) {
        binding?.loader?.visibility = if (b) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}