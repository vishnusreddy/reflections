package com.faanghut.reflection.ui.notes.editNote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.faanghut.reflection.R
import com.faanghut.reflection.databinding.FragmentEditNoteBinding
import com.faanghut.reflection.databinding.FragmentNewNoteBinding
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.ui.notes.viewNote.ViewNoteFragmentArgs
import com.faanghut.reflection.utils.showKeyboard
import com.faanghut.reflection.utils.to12HourFormat
import com.faanghut.reflection.utils.to24HourFormat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val args: EditNoteFragmentArgs by navArgs()
    lateinit var page: Page

    private lateinit var localDate: LocalDate
    private lateinit var localTime: LocalTime
    private var lastSelectedDate: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setupClickListeners()
    }

    private fun initUI() {
        page = args.page

        binding.etTitle.setText(page.title)
        binding.etBody.setText(page.body)

        setDateString(page.date)
        setTimeString(page.createdTimestamp)

        lastSelectedDate = page.date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        localDate = page.date
        localTime = page.createdTimestamp
    }

    private fun setupClickListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            // TODO - Check Changes and Ask
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.deleteNote -> {
                    // TODO - Delete Op
                    true
                }

                R.id.saveNote -> {
                    // TODO - Update Op
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

    private fun setDateString(localDate: LocalDate) {
        val date = localDate.dayOfMonth.toString()
        val day =
            localDate.dayOfWeek.toString().lowercase().take(3).replaceFirstChar(Char::uppercase)
        val month = localDate.month.toString().lowercase().take(3).replaceFirstChar(Char::uppercase)
        val dateString = "$day, $month $date"
        binding.tvDate.text = dateString
    }

    private fun setTimeString(localTime: LocalTime) {
        if (DateFormat.is24HourFormat(requireContext())) {
            binding.tvTime.text = localTime.to24HourFormat()
        } else {
            binding.tvTime.text = localTime.to12HourFormat()
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string.select_date))
                .setSelection(lastSelectedDate).build()
        datePicker.show(childFragmentManager, "datePicker")

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = datePicker.selection
            selectedDate?.let {
                val instant = Instant.ofEpochMilli(selectedDate).atZone(ZoneId.systemDefault())
                lastSelectedDate = selectedDate
                localDate = instant.toLocalDate()
                setDateString(localDate)
            }
        }
    }

    private fun showTimePicker() {
        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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

}