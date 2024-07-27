package com.tuners.tutu.ui.main_students.message.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuners.tutu.R
import com.tuners.tutu.adapters.MessageAdapter
import com.tuners.tutu.data.remote.response.Messages
import com.tuners.tutu.data.remote.supabase.SupabaseConfig
import com.tuners.tutu.databinding.FragmentChatBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_students.MainViewModel
import com.tuners.tutu.ui.main_students.message.MessageViewModel
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.serializer.JacksonSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding

    private val messageViewModel by viewModels<MessageViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val supabase = SupabaseConfig.getSupabaseClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @OptIn(SupabaseExperimental::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = ChatFragmentArgs.fromBundle(arguments as Bundle)
        binding?.tvUser?.text = data.username
        binding?.rvChat?.layoutManager = LinearLayoutManager(requireContext())

        binding?.btnSend?.setOnClickListener {
            val message = binding?.edtChat?.text.toString().trim()

            mainViewModel.getSession().observe(viewLifecycleOwner) { currentUser ->
                messageViewModel.postMessage(data.roomId, message, currentUser.userId)
            }
            binding?.edtChat?.setText("")
        }

        val messages: Flow<List<Messages>> = supabase.from("messages").selectAsFlow(
            Messages::messageId,
            filter = FilterOperation("chatroomId", FilterOperator.EQ, data.roomId)
        )

        lifecycleScope.launch {
            messages.collect {
                for (message in it) {
                    Log.d("MESSAGE", message.message)
                }
                populateChats(it)
            }
        }

        binding?.btnBack?.setOnClickListener {
            val toPrev = ChatFragmentDirections.actionChatFragmentToNavigationMessage()
            findNavController().navigate(toPrev)
        }
    }

    private fun populateChats(msg: List<Messages>) {
        mainViewModel.getSession().observe(viewLifecycleOwner) { currentUser ->
            val adapter = MessageAdapter(currentUser.userId)
            adapter.submitList(msg)
            binding?.rvChat?.adapter = adapter
            binding?.rvChat?.scrollToPosition(msg.size - 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}