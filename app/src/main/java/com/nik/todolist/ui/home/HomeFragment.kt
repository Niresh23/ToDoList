package com.nik.todolist.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import com.nik.todolist.ui.note.NoteFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<List<Note>?, HomeViewState>() {

    companion object {
        fun start (view: View?) =
            view?.findNavController()?.navigate(R.id.action_home_to_note)
    }
    lateinit var adapter: NotesRVAdapter
    override val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val fab: FloatingActionButton = root.findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_home_to_note)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_notes.layoutManager = GridLayoutManager(activity, 2)
        adapter = NotesRVAdapter {
            NoteFragment.start(view, context, it.id)
        }
        rv_notes.adapter = adapter
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}