package com.basecamp.turbolinks

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.navOptions

interface TurbolinksDestination {
    val fragment: Fragment
        get() = this as Fragment

    val navHostFragment: TurbolinksNavHostFragment
        get() = fragment.parentFragment as TurbolinksNavHostFragment

    val location: String
        get() = delegate().location

    val previousLocation: String?
        get() = delegate().previousLocation

    val pathConfiguration: PathConfiguration
        get() = delegate().pathConfiguration

    val pathProperties: PathProperties
        get() = delegate().pathProperties

    val sessionName: String
        get() = session.sessionName

    val session: TurbolinksSession
        get() = navHostFragment.session

    val webView: TurbolinksWebView
        get() = session.webView

    val sessionViewModel: TurbolinksSessionViewModel
        get() = delegate().sessionViewModel

    val pageViewModel: TurbolinksFragmentViewModel
        get() = delegate().pageViewModel

    val navigator: TurbolinksNavigator
        get() = delegate().navigator

    val isDialog: Boolean
        get() = fragment is DialogFragment

    fun delegate(): TurbolinksFragmentDelegate

    fun toolbarForNavigation(): Toolbar?

    fun onBeforeNavigation()

    fun navHostForNavigation(newLocation: String): TurbolinksNavHostFragment {
        return navHostFragment
    }

    fun shouldNavigateTo(newLocation: String): Boolean {
        return true
    }

    fun navigate(
        location: String,
        options: VisitOptions = VisitOptions(),
        bundle: Bundle? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        navigator.navigate(location, options, bundle, extras)
    }

    fun getNavigationOptions(
        newLocation: String,
        newPathProperties: PathProperties
    ): NavOptions {
        return navOptions {
            anim {
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }
    }

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun navigateBack() {
        navigator.navigateBack()
    }

    fun clearBackStack() {
        navigator.clearBackStack()
    }

    fun findNavHostFragment(@IdRes navHostFragmentId: Int): TurbolinksNavHostFragment {
        return fragment.parentFragment?.childFragmentManager?.findNavHostFragment(navHostFragmentId)
            ?: fragment.parentFragment?.parentFragment?.childFragmentManager?.findNavHostFragment(navHostFragmentId)
            ?: fragment.requireActivity().supportFragmentManager.findNavHostFragment(navHostFragmentId)
            ?: throw IllegalStateException("No TurbolinksNavHostFragment found with ID: $navHostFragmentId")
    }

    private fun FragmentManager.findNavHostFragment(navHostFragmentId: Int): TurbolinksNavHostFragment? {
        return findFragmentById(navHostFragmentId) as? TurbolinksNavHostFragment
    }
}
