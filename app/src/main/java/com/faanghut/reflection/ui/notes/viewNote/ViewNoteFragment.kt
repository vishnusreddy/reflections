package com.faanghut.reflection.ui.notes.viewNote

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.faanghut.reflection.databinding.FragmentViewNoteBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.utils.getDateString
import com.faanghut.reflection.utils.to12HourFormat
import com.faanghut.reflection.utils.to24HourFormat

class ViewNoteFragment : Fragment() {
    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!

    private val args: ViewNoteFragmentArgs by navArgs()
    lateinit var page: Page

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setupClickListeners()
    }

    private fun initUI() {
        page = args.page
        binding.tvDate.text = page.date.getDateString()

        if (DateFormat.is24HourFormat(requireContext())) {
            binding.tvTime.text = page.lastEditedTimestamp.to24HourFormat()
        } else {
            binding.tvTime.text = page.lastEditedTimestamp.to12HourFormat()
        }

        binding.tvTitle.text = page.title
        binding.tvBody.text = page.body
    }

    private fun setupClickListeners() {
        // WIll do later on
    }

}