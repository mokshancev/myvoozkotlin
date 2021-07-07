package com.example.myvoozkotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvoozkotlin.models.news.News
import com.example.myvoozkotlin.databinding.FragmentHomeBinding
import com.example.myvoozkotlin.helpers.Status
import com.example.myvoozkotlin.helpers.hide
import com.example.myvoozkotlin.helpers.show
import com.example.myvoozkotlin.home.adapters.NewsAdapter
import com.example.myvoozkotlin.home.viewModels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.loadNews(10)
        configureViews()
        initObservers()
    }

    private fun configureViews() {

    }

    private fun initNewsAdapter(news: List<News>) {
        if (binding.rvStory.adapter == null) {
            binding.rvStory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvStory.adapter = NewsAdapter(news)
        } else {
            (binding.rvStory.adapter as? NewsAdapter)?.update(news)
        }
    }

    private fun initObservers(){
        observeOnNewsResponse()
    }

    private fun observeOnNewsResponse(){
        newsViewModel.newsResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    binding.shimmerViewContainer.hide()
                    binding.rvStory.show()
                    if(it.data == null){

                    }
                    else{
                        initNewsAdapter(it.data)
                    }
                }
                Status.ERROR -> {

                }
            }
        })
    }
}