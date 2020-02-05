package com.nik.todolist.ui.note


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_note.*
import org.jetbrains.anko.support.v4.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class NoteFragment : BaseFragment<NoteViewState.Data, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteFragment::class.java.name + "extra.note"
    }

    private var note: Note? = null

    private val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    }

    override val model: NoteViewModel by viewModel()

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
        val noteId = arguments?.getString("id")
        noteId?.let {
            model.loadNote(it)
        }
        view.findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveNote()
        }
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            deleteNote()
        }
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        android.R.id.home -> {
            activity?.onBackPressed()
            true
        } else -> super.onOptionsItemSelected(item)
    }

    override fun renderData(data: NoteViewState.Data) {
        if(data.isDeleted) {
            view?.findNavController()?.navigate(R.id.action_note_to_home)
            return
        }
        this.note = data.note
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

    private fun saveNote() {
        if(et_time.text == null || et_time.text!!.isEmpty()) return
        note = note?.copy(
            time = et_time.text.toString(),
            text = et_body.text.toString()
        ) ?: Note(UUID.randomUUID().toString(), et_time.text.toString(), et_body.text.toString())
        note?.let { model.save(it) }
    }

    private fun togglePallete() {

    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.note_delete_message
            negativeButton(R.string.note_delete_cancel) { dialog -> dialog.dismiss()}
            positiveButton(R.string.note_delete_ok) { model.deleteNote()}
        }
    }
}