package binar.finalproject.binair.admin.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant.initApp
import binar.finalproject.binair.admin.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding
    private lateinit var sharedPref : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        sharedPref = requireActivity().getSharedPreferences(initApp, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        val popIn = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.pop_up)
        binding.mainlogo.startAnimation(popIn)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.myLooper()!!).postDelayed({
            if(sharedPref.getBoolean("firstRun",true)) {
                editor.putBoolean("firstRun", false)
                editor.apply()
                findNavController().navigate(R.id.action_splashScreenFragment_to_carouselFragment)
            }else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }
        },500)
    }


}