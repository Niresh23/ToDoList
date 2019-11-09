package com.nik.todolist.extension

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun<T : Fragment> Fragment.replaceFragmentSafely(
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
    if(isAdded) {
        val fm = childFragmentManager.beginTransaction()
        fm.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        fm.replace(containerViewId, fragment, tag)
        activity?.let{
            if(addToBackStack) {
                fm.addToBackStack(tag)
            }
            if(childFragmentManager.isStateSaved) {
                if(commitNow) fm.commitNowAllowingStateLoss()
                else fm.commitAllowingStateLoss()
            } else{
                if(commitNow) fm.commitNow()
                else fm.commit()
            }
        }
    }
    return fragment
}