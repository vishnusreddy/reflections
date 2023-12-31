package com.faanghut.reflection.ui.notes.viewNote

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.faanghut.reflection.R
import com.faanghut.reflection.ReflectionApplication
import com.faanghut.reflection.databinding.FragmentViewNoteBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.utils.getDateString
import com.faanghut.reflection.utils.to12HourFormat
import com.faanghut.reflection.utils.to24HourFormat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ViewNoteFragment : Fragment() {
    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!

    private val args: ViewNoteFragmentArgs by navArgs()
    lateinit var page: Page

    private val viewModel: ViewNoteViewModel by viewModels {
        ViewNoteViewModelFactory((activity?.application as ReflectionApplication).pageRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setupClickListeners()
        setupObservers()
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
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteNote -> {
                    showDeleteConfirmationDialog()
                    true
                }

                R.id.editNote -> {
                    findNavController().navigate(
                        ViewNoteFragmentDirections.actionViewNoteFragmentToEditNoteFragment(
                            page
                        )
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun setupObservers() {
        viewModel.pageDeletedFromDb.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun showDeleteConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.are_you_sure))
            .setMessage("You are about to delete this page. This action is irreversible, please proceed with caution.")
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.setNegativeButton(resources.getString(R.string.yes_delete)) { dialog, _ ->
                dialog.dismiss()
                viewModel.delete(page)
            }.show()
    }

}