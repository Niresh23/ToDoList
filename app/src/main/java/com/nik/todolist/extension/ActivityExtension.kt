package com.nik.todolist.extension

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun<T: Fragment> AppCompatActivity.replaceFragmentSafely(
    fragment: T,
    tag: String,
    @IdRes containerViewId: Int,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0,
    commitNow: Boolean = false,
    addToBackStack: Boolean = false
): T {
    val fm = supportFragmentManager.beginTransaction()
    fm.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
    fm.replace(containerViewId, fragment, tag)
    if(addToBackStack) {
        fm.addToBackStack(tag)
    }
    if(supportFragmentManager.isStateSaved) {
        if(commitNow) fm.commitNowAllowingStateLoss()
        else fm.commitAllowingStateLoss()
    } else {
        if(commitNow) fm.commitNow()
        else fm.commit()
    }
    return fragment
}
