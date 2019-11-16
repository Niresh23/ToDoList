package com.nik.todolist.ui.spalsh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import com.nik.todolist.ui.base.BaseViewModel

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {

    override val viewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        viewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {

        }
    }
}