package com.tuners.tutu.ui.main_mentors.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.tuners.tutu.R
import com.tuners.tutu.databinding.FragmentMentorHomeBinding
import com.tuners.tutu.helper.Formats.withCurrencyFormat
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_mentors.MainMentorViewModel

class MentorHomeFragment : Fragment() {
    private var _binding: FragmentMentorHomeBinding? = null
    private val binding get() = _binding

    private val mainMentorViewModel by activityViewModels<MainMentorViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMentorHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainMentorViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding?.tvWelcome?.text = getString(R.string.welcome, user.name)
            binding?.tvBalance?.text = getString(R.string.balance, user.balance.toString().withCurrencyFormat())
        }

        binding?.btnIncomingOrders?.setOnClickListener {
            showToast()
        }

        binding?.btnOrdersHistory?.setOnClickListener {
            showToast()
        }

        binding?.btnWithdraw?.setOnClickListener {
            showToast()
        }

        binding?.btnSeeAllTransactions?.setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Sedang dalam pengerjaan..", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}