package com.faanghut.reflection.ui.notes

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.faanghut.reflection.R
import com.faanghut.reflection.databinding.FragmentEditNoteBinding
import com.faanghut.reflection.models.Note
import com.faanghut.reflection.to12HourFormat
import com.faanghut.reflection.to24HourFormat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val args: EditNoteFragmentArgs by navArgs()

    private var note: Note? = null

    private lateinit var localDate: LocalDate
    private lateinit var localTime: LocalTime

    private var lastSelectedDate: Long = MaterialDatePicker.todayInUtcMilliseconds()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupViews()
    }

    private fun setupViews() {
        if (note != null) {
            setupExistingNoteView()
        } else {
            setupNewNoteView()
        }
    }

    private fun setupNewNoteView() {
        localDate = LocalDate.now()
        localTime = LocalTime.now()

        setDateString(localDate)
        setTimeString(localTime)

        binding.ivEditDate.setOnClickListener {
            showDatePicker()
        }

        binding.ivEditTime.setOnClickListener {
            showTimePicker()
        }
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

    private fun setupExistingNoteView() {
        // TODO - View Existing Note
        // TODO - If not a good idea, let's create another fragment for it if needed
        // Seeing the size of this, maybe its a good idea to keep this fragment just for editing
    }

    private fun init() {
        args.note?.let {
            note = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}