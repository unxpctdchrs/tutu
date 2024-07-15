package com.tuners.tutu.ui.main_students.home.consult

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import com.tuners.tutu.adapters.OnlineMentorsAdapter
import com.tuners.tutu.data.remote.response.MentorListResponse
import com.tuners.tutu.databinding.FragmentConsultBinding
import com.tuners.tutu.helper.ViewModelFactory

class ConsultFragment : Fragment() {
    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding

    private val consultViewModel by viewModels<ConsultViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.END)
        exitTransition = Slide(Gravity.START)

        consultViewModel.getMentors()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnOrderHistory?.setOnClickListener {
            val toOrderHistory = ConsultFragmentDirections.actionConsultFragmentToOrderHistoryFragment()
            findNavController().navigate(toOrderHistory)
        }

        binding?.svUser?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                consultViewModel.searchMentors(newText.toString())
                return true
            }

        })

        consultViewModel.mentorsLoading.observe(viewLifecycleOwner) { isLoading ->
            isLoading(isLoading)

            if (!isLoading) {
                consultViewModel.mentors.observe(viewLifecycleOwner) { data ->
                    loadMentors(data)
                }
            }
        }

        binding?.btnBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadMentors(mentors: MentorListResponse) {
        val adapter = OnlineMentorsAdapter(false)
        adapter.submitList(mentors.result)
        binding?.rvMentors?.adapter = adapter
        binding?.rvMentors?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun isLoading(b: Boolean) {
        if (b) binding?.loader?.visibility = View.VISIBLE else binding?.loader?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}