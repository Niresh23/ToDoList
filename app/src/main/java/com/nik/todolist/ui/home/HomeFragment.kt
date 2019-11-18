package com.nik.todolist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<List<Note>?, HomeViewState>(){

    lateinit var adapter: NotesRVAdapter

    override val model: HomeViewModel by viewModel()

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
            view.findNavController().navigate(R.id.action_home_to_note, bundleOf("id" to it.id))
        }
        rv_notes.adapter = adapter
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}