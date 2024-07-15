package com.tuners.tutu.ui.main_students.home.consult.order

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.databinding.FragmentOrder2Binding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_students.MainViewModel
import com.tuners.tutu.ui.main_students.home.consult.ConsultViewModel

class OrderFragment2 : Fragment() {
    private var _binding: FragmentOrder2Binding? = null
    private val binding get() = _binding

    private val consultViewModel by viewModels<ConsultViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrder2Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mentorData = OrderFragment2Args.fromBundle(arguments as Bundle)

        binding?.tvName?.text = mentorData.username
        binding?.tvRating?.text = mentorData.ratings.toString()
        binding?.tvOrderDetails?.text = getString(R.string.s_order_details, mentorData.method, mentorData.duration)
        binding?.tvTotal?.text = getString(R.string.d_total, mentorData.total)

        binding?.btnCancel?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.btnNext?.setOnClickListener {
            mainViewModel.getSession().observe(viewLifecycleOwner) { currentUser ->
                consultViewModel.placeOrder(
                    mentorUsername = mentorData.username,
                    userId = currentUser.userId,
                    consultationType = mentorData.method,
                    consultationDuration = mentorData.duration,
                    total = mentorData.total
                )
            }
        }

        consultViewModel.orderLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)

            if (!isLoading) {
                val customLayout = layoutInflater.inflate(R.layout.order_checkout_dialog, null)
                val alertDialogBuilder = AlertDialog.Builder(requireContext()).setView(customLayout)
                val startAChatBtn = customLayout.findViewById<MaterialButton>(R.id.btn_start_chat)
                val loader = customLayout.findViewById<ProgressBar>(R.id.loader)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                startAChatBtn.setOnClickListener {
                    mainViewModel.getSession().observe(viewLifecycleOwner) { currentUser ->
                        consultViewModel.createChat(
                            mentorUsername = mentorData.username,
                            mentorId = mentorData.mentorId,
                            studentUsername = currentUser.name,
                            userId = currentUser.userId,
                        )
                    }

                    fun setLoading(b: Boolean) {
                        if (b) {
                            startAChatBtn.text = ""
                            loader.visibility = View.VISIBLE
                        } else {
                            startAChatBtn.text = getString(R.string.start_a_chat_with_mentor)
                            loader.visibility = View.GONE
                        }
                    }

                    consultViewModel.createChatLoading.observe(viewLifecycleOwner) { isLoading ->
                        setLoading(isLoading)

                        if (!isLoading) {
                            consultViewModel.createChatResponse.observe(viewLifecycleOwner) { response ->
                                if (response.message == "chat alr created") {
                                    val toChat = OrderFragment2Directions.actionOrderFragment2ToChatFragment()
                                    toChat.username = mentorData.username
                                    toChat.roomId = response.chatroomId!!
                                    findNavController().navigate(toChat)
                                } else {
                                    val toChat = OrderFragment2Directions.actionOrderFragment2ToChatFragment()
                                    toChat.username = mentorData.username
                                    toChat.roomId = response.chatroomId!!
                                    findNavController().navigate(toChat)
                                }
                            }
                            alertDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading (b: Boolean) {
        if (b) {
            binding?.btnNext?.text = ""
            binding?.loader?.visibility = View.VISIBLE
        } else {
            binding?.btnNext?.text = getString(R.string.next)
            binding?.loader?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}