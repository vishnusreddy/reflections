package com.faanghut.reflection.ui.notes

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faanghut.reflection.R
import com.faanghut.reflection.ReflectionApplication
import com.faanghut.reflection.databinding.FragmentNewNoteBinding
import com.faanghut.reflection.models.Note
import com.faanghut.reflection.utils.showKeyboard
import com.faanghut.reflection.utils.to12HourFormat
import com.faanghut.reflection.utils.to24HourFormat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewNoteViewModel by viewModels {
        NewNoteViewModelFactory((activity?.application as ReflectionApplication).noteRepository)
    }

    private lateinit var localDate: LocalDate
    private lateinit var localTime: LocalTime
    private var lastSelectedDate: Long = MaterialDatePicker.todayInUtcMilliseconds()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitGracefully()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.noteInsertedToDb.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Note Created", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupViews() {
        localDate = LocalDate.now()
        localTime = LocalTime.now()

        setDateString(localDate)
        setTimeString(localTime)

        binding.etBody.showKeyboard()
    }

    private fun setupClickListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            exitGracefully()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteNote -> {
                    if (isContentPresent()) {
                        showConfirmationDialog()
                    } else {
                        findNavController().popBackStack()
                    }
                    true
                }

                R.id.saveNote -> {
                    if (isContentPresent()) {
                        storeContentToDBAndPopBack()
                    } else {
                        Toast.makeText(requireContext(), "Page not created ☹️", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    true
                }

                else -> false
            }
        }

        binding.ivEditDate.setOnClickListener {
            showDatePicker()
        }

        binding.tvDate.setOnClickListener {
            showDatePicker()
        }

        binding.ivEditTime.setOnClickListener {
            showTimePicker()
        }

        binding.tvTime.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.are_you_sure))
            .setMessage("You are about to delete this page. This action is irreversible, please proceed with caution.")
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.yes_delete)) { dialog, _ ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }

    private fun exitGracefully() {
        if (isContentPresent()) {
            storeContentToDBAndPopBack()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun storeContentToDBAndPopBack() {
        val title = binding.etTitle.text.toString()
        val body = binding.etTitle.text.toString()
        val note = Note(
            title = title,
            body = body,
            date = localDate,
            createdTimestamp = localTime,
            lastEditedTimestamp = localTime,
        )
        viewModel.insert(note)
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string.select_date))
            .setSelection(lastSelectedDate).build()
        datePicker.show(childFragmentManager, "datePicker")

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = datePicker.selection
            selectedDate?.let {
                val instant = Instant.ofEpochMilli(selectedDate)
                    .atZone(ZoneId.systemDefault())
                lastSelectedDate = selectedDate
                localDate = instant.toLocalDate()
                setDateString(localDate)
            }
        }
    }

    private fun showTimePicker() {
        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val timePicker =
            MaterialTimePicker.Builder().setTimeFormat(clockFormat).setHour(localTime.hour)
                .setMinute(localTime.minute).setTitleText(getString(R.string.select_time)).build()
        timePicker.show(childFragmentManager, "timePicker")

        timePicker.addOnPositiveButtonClickListener {
            localTime = LocalTime.of(timePicker.hour, timePicker.minute)
            setTimeString(localTime)
        }
    }

    private fun setDateString(localDate: LocalDate) {
        val date = localDate.dayOfMonth.toString()
        val day = localDate.dayOfWeek.toString().lowercase().take(3).replaceFirstChar(Char::uppercase)
        val month = localDate.month.toString().lowercase().take(3).replaceFirstChar(Char::uppercase)
        val dateString = "$day, $month $date"
        binding.tvDate.text = dateString
    }

    private fun setTimeString(localTime: LocalTime) {
        if (is24HourFormat(requireContext())) {
            binding.tvTime.text = localTime.to24HourFormat()
        } else {
            binding.tvTime.text = localTime.to12HourFormat()
        }
    }

    private fun isContentPresent(): Boolean {
        return !(binding.etBody.text.isNullOrEmpty() && binding.etTitle.text.isNullOrEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}