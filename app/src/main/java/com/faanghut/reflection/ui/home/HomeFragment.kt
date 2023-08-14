package com.faanghut.reflection.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.faanghut.reflection.ReflectionApplication
import com.faanghut.reflection.databinding.FragmentHomeBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.ui.home.adapters.PageDateAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as ReflectionApplication).pageRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pageDatesAdapter = PageDateAdapter(
            onItemClickAction = ::openSelectedPage
        )

        homeViewModel.allPageWithDates.observe(viewLifecycleOwner) {
            binding.rvPageDates.layoutManager = LinearLayoutManager(context)
            binding.rvPageDates.adapter = pageDatesAdapter
            pageDatesAdapter.updatePageDateWithPages(it)
        }

        clickListeners()

    }

    private fun clickListeners() {
        binding.floatingActionButton.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNewNoteFragment()
            findNavController().navigate(action)
        }
    }

    private fun openSelectedPage(page: Page) {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToViewNoteFragment(
                page
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}