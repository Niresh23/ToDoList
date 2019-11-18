package com.nik.todolist.ui.spalsh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.nik.todolist.R
import com.nik.todolist.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<Boolean?, SplashViewState>() {

    override val model: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        model.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            view?.findNavController()!!.navigate(R.id.action_home_to_note)
        }
    }
}