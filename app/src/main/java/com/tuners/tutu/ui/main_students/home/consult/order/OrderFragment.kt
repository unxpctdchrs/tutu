package com.tuners.tutu.ui.main_students.home.consult.order

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.tuners.tutu.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Slide(Gravity.END)
        exitTransition = Slide(Gravity.START)
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
            val toPrev = OrderFragmentDirections.actionOrderFragmentToConsultFragment()
            findNavController().navigate(toPrev)
        }

        binding?.btnNext?.setOnClickListener {
            val toOrder2 = OrderFragmentDirections.actionOrderFragmentToOrderFragment2()
            findNavController().navigate(toOrder2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}