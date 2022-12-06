package binar.finalproject.binair.admin.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.databinding.FragmentAddNotificationBinding
import binar.finalproject.binair.admin.databinding.FragmentHomeBinding

class AddNotificationFragment : Fragment() {
    lateinit var binding : FragmentAddNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}