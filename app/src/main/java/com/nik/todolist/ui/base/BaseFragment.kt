package com.nik.todolist.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.nik.todolist.Data.errors.NoAuthException
import com.nik.todolist.R

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment(){
    abstract val model: BaseViewModel<T, S>

    companion object {
        private const val RC_SIGN_IN = 4242
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getViewState().observe(this, Observer<S>{
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
        when(error) {
            is NoAuthException -> startLogin()
            else -> it.message?.let {message ->
                showError(message)
            }
        }
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.cat_hungry)
            .setTheme(R.style.LoginStyle)
            .setAvailableProviders(providers)
            .build(), RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            activity?.finish()
        }
    }
    private fun showError(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}