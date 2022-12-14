@file:Suppress("RedundantNullableReturnType", "RedundantSamConstructor", "RedundantSemicolon",
    "RedundantSemicolon"
)

package binar.finalproject.binair.admin.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.model.News
import binar.finalproject.binair.admin.databinding.FragmentCarouselBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.HeadlineViewPager
import binar.finalproject.binair.admin.viewmodel.NewsViewModel

@Suppress("RedundantSemicolon", "RedundantSemicolon")
class CarouselFragment : Fragment() {
    private lateinit var binding : FragmentCarouselBinding
    private lateinit var newsVM : NewsViewModel
    private lateinit var viewPagerAdapter: HeadlineViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarouselBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsVM = ViewModelProvider(this)[NewsViewModel::class.java]
        newsVM.getHeadlinesData()
        newsVM.headlineListLiveData.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.setHeadlineNewsData(it as ArrayList<News>)
        })
        carouselset()
        setSwipeUpListener()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.bottomNavContainer.visibility = View.GONE
    }

    private fun carouselset(){
        viewPagerAdapter = HeadlineViewPager(ArrayList())
        binding.vpHeadline.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpHeadline.offscreenPageLimit = 3
        binding.vpHeadline.adapter = viewPagerAdapter
        binding.vpHeadline.setPageTransformer(MarginPageTransformer(50))
        binding.vpHeadline.clipToPadding = false;
        binding.vpHeadline.setPadding(10,10,10,0);
        binding.dotsIndicator.attachTo(binding.vpHeadline)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSwipeUpListener(){
        binding.containerSwipeUp.setOnTouchListener { view, event ->
            when(event.action){
                MotionEvent.ACTION_UP -> {
                    findNavController().navigate(R.id.action_carouselFragment_to_loginFragment)
                }
            }
//            view.performClick()
            return@setOnTouchListener true
        }
    }
}