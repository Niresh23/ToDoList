package com.nik.todolist.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment(){
    abstract val viewModel: BaseViewModel<T, S>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun showError(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}