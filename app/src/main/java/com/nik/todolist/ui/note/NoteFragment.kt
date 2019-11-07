package com.nik.todolist.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.R
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private var note: Note? = null
    private val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    }

    private fun saveNote() {
        if(et_time.text == null || et_time.text!!.isEmpty()) return

        note = note?.copy(
            time = et_time.text.toString(),
            text = et_body.text.toString()
        ) ?: Note(et_time.text.toString(), et_body.text.toString())
        note?.let{ noteViewModel.save(it) }
    }

    companion object {
        private val EXTRA_NOTE = NoteFragment::class.java.name + "extra.note"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        fun start (context: Context, note: Note? = null) {
            val intent = Intent(context, NoteFragment::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        noteViewModel =
            ViewModelProviders.of(this).get(NoteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_note, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        et_time.removeTextChangedListener(textChangedListener)
        et_body.removeTextChangedListener(textChangedListener)

        if(note != null) {
            et_time.setText(note?.time ?: "")
            et_body.setText(note?.text ?: "")
        }

        et_time.addTextChangedListener(textChangedListener)
        et_body.addTextChangedListener(textChangedListener)
    }

}