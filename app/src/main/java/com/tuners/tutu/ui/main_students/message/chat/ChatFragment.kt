package com.tuners.tutu.ui.main_students.message.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuners.tutu.R
import com.tuners.tutu.adapters.MessageAdapter
import com.tuners.tutu.data.remote.response.Messages
import com.tuners.tutu.databinding.FragmentChatBinding
import com.tuners.tutu.helper.ViewModelFactory
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

    val supabase = createSupabaseClient(
        supabaseUrl = "https://ipxppqajryhicuiigiaa.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlweHBwcWFqcnloaWN1aWlnaWFhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjAwODUyMDgsImV4cCI6MjAzNTY2MTIwOH0.9oGZMzp-QKx8xx6QZEcIoxSdrQAj6VQ2HqJT8YNYyHo"
    ) {
        install(Postgrest)
        install(Realtime)
        defaultSerializer = JacksonSerializer()
    }

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
            messageViewModel.postMessage(data.roomId, binding?.edtChat?.text.toString().trim())
            binding?.edtChat?.setText("")
        }

        val flow: Flow<List<Messages>> = supabase.from("messages").selectAsFlow(
            Messages::messageId,
            filter = FilterOperation("chatroomId", FilterOperator.EQ, data.roomId)
        )

        lifecycleScope.launch {
            flow.collect {
                for (message in it) {
                    Log.d("MESSAGE", message.message)
                }
                populateChats(it)
            }
        }
    }

    private fun populateChats(msg: List<Messages>) {
        val adapter = MessageAdapter()
        adapter.submitList(msg)
        binding?.rvChat?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}