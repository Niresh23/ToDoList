package com.nik.todolist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.todolist.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val qq = ""
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: NotesRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotesRVAdapter()
        rv_notes.layoutManager = GridLayoutManager(activity, 2)
        rv_notes.adapter = adapter
        homeViewModel.viewState().observe(this, Observer{
                viewState -> viewState?.let{adapter.notes = it.notes}
        })
    }
}