package com.nik.todolist.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity(){
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let{
            setContentView(it)
        }
        viewModel.getViewState().observe(this, Observer<S>{
            it ?: return@Observer
            it.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(it.data)
        })
    }

    abstract fun renderData(data: T)

    private fun renderError(error: Throwable) = error?.let {
        it.message?.let{message ->
            showError(message)
        }
    }

    private fun showError(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}