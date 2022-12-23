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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.Constant.initApp
import binar.finalproject.binair.admin.databinding.FragmentSplashScreenBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private lateinit var binding : FragmentSplashScreenBinding
    private lateinit var sharedPref : SharedPreferences
    private lateinit var prefsUser: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        sharedPref = requireActivity().getSharedPreferences(initApp, Context.MODE_PRIVATE)
        prefsUser = requireActivity().getSharedPreferences(Constant.dataUser, Context.MODE_PRIVATE)
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
            }
            checkToken()
        },500)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.bottomNavContainer.visibility = View.GONE
    }

    private fun checkToken() : Boolean{
        val prefs = requireActivity().getSharedPreferences(Constant.dataUser, Context.MODE_PRIVATE)
        val userVM = ViewModelProvider(this).get(UserViewModel::class.java)
        val token = prefs.getString("token", null)
        var result = false
        if(token != null){
            userVM.getUser("Bearer $token").observe(viewLifecycleOwner) {
                if (it != null) {
                    result = true
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment2)
                }else{
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                }
            }
        }
        return result
    }
}