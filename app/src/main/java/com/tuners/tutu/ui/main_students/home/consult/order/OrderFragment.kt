package com.tuners.tutu.ui.main_students.home.consult.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import com.tuners.tutu.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = OrderFragmentArgs.fromBundle(arguments as Bundle)

        binding?.tvName?.text = data.username
        binding?.tvRating?.text = data.ratings.toString()

        binding?.btnCancel?.setOnClickListener {
            findNavController().popBackStack()
        }

        var jenisPemesanan = ""
        var viaChatIsChecked = false
        var viaChatZoomIsChecked = false

        binding?.radChat?.setOnCheckedChangeListener { _, isChecked ->
            viaChatIsChecked = isChecked
        }

        binding?.radZoomchat?.setOnCheckedChangeListener { _, isChecked ->
            viaChatZoomIsChecked = isChecked
        }

        var duration = ""
        var fifteenIsChecked = false
        var thirtyIsChecked = false
        var oneIsChecked = false
        var twoIsChecked = false

        binding?.rad15?.setOnCheckedChangeListener { _, isChecked ->
            fifteenIsChecked = isChecked
        }

        binding?.rad30?.setOnCheckedChangeListener { _, isChecked ->
            thirtyIsChecked = isChecked
        }

        binding?.rad1?.setOnCheckedChangeListener { _, isChecked ->
            oneIsChecked = isChecked
        }

        binding?.rad2?.setOnCheckedChangeListener { _, isChecked ->
            twoIsChecked = isChecked
        }

        binding?.btnNext?.setOnClickListener {
            if (viaChatIsChecked) jenisPemesanan = "Via Chat"
            else if (viaChatZoomIsChecked) jenisPemesanan = "Via Zoom + Chat"

            if (fifteenIsChecked) duration = "15 menit"
            else if (thirtyIsChecked) duration = "30 menit"
            else if (oneIsChecked) duration = "1 jam"
            else if (twoIsChecked) duration = "2 jam"

            if (!binding?.radChat?.isChecked!! && !binding?.radZoomchat?.isChecked!!) {
                showMessage("Silahkan Pilih Metode Konsultasi Terlebih Dahulu!")
            } else if (
                !binding?.rad15?.isChecked!! &&
                !binding?.rad30?.isChecked!! &&
                !binding?.rad1?.isChecked!! &&
                !binding?.rad2?.isChecked!!
            ) {
                showMessage("Silahkan Pilih Durasi Konsultasi Terlebih Dahulu!")
            } else {
                var consultationFee = 0

                when (jenisPemesanan) {
                    "Via Chat" -> consultationFee = 5000
                    "Via Zoom + Chat" -> consultationFee = 10000
                }

                when (duration) {
                    "15 menit" -> consultationFee += 2000
                    "30 menit" -> consultationFee += 4000
                    "1 jam" -> consultationFee += 6000
                    "2 jam" -> consultationFee += 8000
                }

                val total = consultationFee + APPPLICATION_FEE
                Log.d("TOTAL", total.toString())

                val toOrder2 = OrderFragmentDirections.actionOrderFragmentToOrderFragment2()
                toOrder2.total = total
                toOrder2.username = data.username
                toOrder2.ratings = data.ratings
                toOrder2.method = jenisPemesanan
                toOrder2.duration = duration
                toOrder2.mentorId = data.mentorId
                findNavController().navigate(toOrder2)
            }
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val APPPLICATION_FEE = 5000
    }
}