package com.faanghut.reflection.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.faanghut.reflection.R
import com.faanghut.reflection.ReflectionApplication
import com.faanghut.reflection.repository.database.AppDatabase
import com.faanghut.reflection.databinding.FragmentHomeBinding
import com.faanghut.reflection.models.Note
import java.time.LocalDate
import java.time.LocalTime

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as ReflectionApplication).noteRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.allNotes.observe(viewLifecycleOwner) {
            Log.i("Notes Updated", it.toString())
        }

        clickListeners()

    }

    private fun clickListeners() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_editNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}