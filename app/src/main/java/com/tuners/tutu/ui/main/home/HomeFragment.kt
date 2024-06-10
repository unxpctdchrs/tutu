package com.tuners.tutu.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tuners.tutu.R
import com.tuners.tutu.adapters.HomeRVAdapter
import com.tuners.tutu.data.local.HomeRVData
import com.tuners.tutu.databinding.FragmentHomeBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main.MainViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding?.tvWelcome?.text = requireContext().getString(R.string.welcome, user.name)
        }

        val videos = arrayListOf(
            HomeRVData("https://img.youtube.com/vi/d982EK-lC0o/maxresdefault.jpg", "https://youtu.be/d982EK-lC0o?si=02eCmDx8buATuByZ"),
            HomeRVData("https://img.youtube.com/vi/jQ2vOo_lPdA/maxresdefault.jpg", "https://youtu.be/jQ2vOo_lPdA?si=PHZ-2aodToZthhSd"),
            HomeRVData("https://img.youtube.com/vi/N4qBD8zbpx8/maxresdefault.jpg", "https://youtu.be/N4qBD8zbpx8?si=7Secbcohy9GDKNm1"),
            HomeRVData("https://img.youtube.com/vi/reerW4fBrmU/maxresdefault.jpg", "https://youtu.be/reerW4fBrmU?si=IdnDPq4N_N4xEqmf"),
        )

        val adapter = HomeRVAdapter(videos)
        binding?.rvHomeVideos?.adapter = adapter
        binding?.rvHomeVideos?.layoutManager = GridLayoutManager(requireContext(), 2)

        binding?.btnVideo?.setOnClickListener {
            showToast()
        }
        binding?.btnLatihan?.setOnClickListener {
            showToast()
        }
        binding?.btnKonsultasi?.setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "On Progress..", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}