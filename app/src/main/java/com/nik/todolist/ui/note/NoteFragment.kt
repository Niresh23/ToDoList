package com.nik.todolist.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : BaseFragment<Note?, NoteViewState>() {

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
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

        note?.let { viewModel.save(it) }
    }

    companion object {
        private val EXTRA_NOTE = NoteFragment::class.java.name + "extra.note"
        fun start (view: View, context: Context?, noteId: String? = null) = Intent(context, NoteFragment::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            view.findNavController().navigate(R.id.action_home_to_note)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_note, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_save).setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_note_to_home)
            }
        initView()
    }

    override fun renderData(data: Note?) {
        this.note = data
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

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            activity?.onBackPressed()
            true
        } else -> super.onOptionsItemSelected(item)
    }
}