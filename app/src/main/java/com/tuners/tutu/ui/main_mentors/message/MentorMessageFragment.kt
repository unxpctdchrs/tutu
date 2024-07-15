package com.tuners.tutu.ui.main_mentors.message

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.tuners.tutu.adapters.UserChatAdapter
import com.tuners.tutu.data.remote.response.ChatsResponse
import com.tuners.tutu.databinding.FragmentMentorMessageBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_students.MainViewModel

class MentorMessageFragment : Fragment() {
    private var _binding: FragmentMentorMessageBinding? = null
    private val binding get() = _binding

    private val mentorMessageViewModel by viewModels<MentorMessageViewModel> {
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMentorMessageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getSession().observe(viewLifecycleOwner) { mentor ->
            mentorMessageViewModel.getChatsMentor(mentor.userId)
        }

        mentorMessageViewModel.chatLoading.observe(viewLifecycleOwner) { isLoading ->
            setLoading(isLoading)

            if (!isLoading) {
                mentorMessageViewModel.chats.observe(viewLifecycleOwner) { chats ->
                    loadUsers(chats)
                }
            }
        }

        binding?.btnBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadUsers(users: ChatsResponse) {
        val adapter = UserChatAdapter(true)
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