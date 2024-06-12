package com.tuners.tutu.ui.main_mentors.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.databinding.FragmentMentorProfileBinding
import com.tuners.tutu.helper.ViewModelFactory
import com.tuners.tutu.ui.main_mentors.MainMentorViewModel

class MentorProfileFragment : Fragment() {

    private var _binding: FragmentMentorProfileBinding? = null
    private val binding get() = _binding

    private val mentorProfileViewModel by viewModels<MentorProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMentorProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.btnLogout?.setOnClickListener {
            val customLayout = layoutInflater.inflate(R.layout.exit_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setView(customLayout)

            val yesBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_yes)
            val noBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_no)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            yesBtn.setOnClickListener {
                mentorProfileViewModel.logout()
                alertDialog.dismiss()
            }

            noBtn.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}