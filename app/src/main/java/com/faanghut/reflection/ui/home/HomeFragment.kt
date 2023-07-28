package com.faanghut.reflection.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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

        var i = 0

        binding.ivBtnDelete.setOnClickListener {
            homeViewModel.insert(
                Note(
                    id = i,
                    title = "Some Title $i",
                    body = "Some Body $i",
                    date = LocalDate.now(),
                    createdTimestamp = LocalTime.now(),
                    lastEditedTimestamp = LocalTime.now()
                )
            )

            i++
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}