package com.tuners.tutu.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import com.google.android.material.button.MaterialButton
import com.tuners.tutu.R
import com.tuners.tutu.databinding.FragmentProfileBinding
import com.tuners.tutu.helper.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding?.edtName?.setHint(user.name)
            binding?.edtBirth?.setHint(user.birthDatePlace)
            binding?.edtEmail?.setHint(user.email)
            binding?.edtPassword?.setHint(user.password)
            binding?.edtPhonenumber?.setHint(user.phoneNumber)

            if (user.jenjangPendidikan == "Mahasiswa") {
                binding?.mahasiswaBtn?.isChecked = true
            } else if (user.jenjangPendidikan == "Siswa") {
                binding?.siswaBtn?.isChecked = true
            }

            USER_ID = user.userId
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            setLoading(isLoading)
        }

        binding?.btnLogout?.setOnClickListener {
            val customLayout = layoutInflater.inflate(R.layout.exit_dialog, null)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                .setView(customLayout)

            val yesBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_yes)
            val noBtn = customLayout.findViewById<MaterialButton>(R.id.btn_logout_no)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            yesBtn.setOnClickListener {
                profileViewModel.logout()
                alertDialog.dismiss()
            }

            noBtn.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        var selectedPendidikan = ""
        var siswaIsChecked = false
        var mahasiswaIsChecked = false

        binding?.siswaBtn?.setOnCheckedChangeListener { _, isChecked ->
            siswaIsChecked = isChecked
        }

        binding?.mahasiswaBtn?.setOnCheckedChangeListener { _, isChecked ->
            mahasiswaIsChecked = isChecked
        }

        binding?.btnUpdateUser?.setOnClickListener {
            if (siswaIsChecked) {
                selectedPendidikan = "Siswa"
            } else if (mahasiswaIsChecked) {
                selectedPendidikan = "Mahasiswa"
            }

            profileViewModel.getSession().observe(viewLifecycleOwner) { user ->
                val username = if (binding?.edtName?.text?.isEmpty()!!) user.name else binding?.edtName?.text?.toString()?.trim()
                val password = if (binding?.edtPassword?.text?.isEmpty()!!) user.password else binding?.edtPassword?.text?.toString()?.trim()
                val birthDatePlace = if (binding?.edtBirth?.text?.isEmpty()!!) user.birthDatePlace else binding?.edtPassword?.text?.toString()?.trim()
                val email = if (binding?.edtEmail?.text?.isEmpty()!!) user.email else binding?.edtEmail?.text?.toString()?.trim()
                val phoneNumber = if (binding?.edtPhonenumber?.text?.isEmpty()!!) user.phoneNumber else binding?.edtPhonenumber?.text?.toString()?.trim()

                profileViewModel.updateUser(
                    user.userId,
                    username!!,
                    password!!,
                    birthDatePlace!!,
                    email!!,
                    phoneNumber!!,
                    selectedPendidikan
                )
            }
        }

        profileViewModel.userUpdate.observe(viewLifecycleOwner) { result ->
            if (!result.error) Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.tvSave?.text = ""
            binding?.loader?.visibility = View.VISIBLE
        } else {
            binding?.tvSave?.text = getString(R.string.save)
            binding?.loader?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ProfileFragment"
        private var USER_ID = "userId"
    }
}