package com.basecamp.turbolinks

import android.os.Bundle
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

abstract class TurbolinksFragment : Fragment(), TurbolinksFragmentCallback {
    private lateinit var viewModel: TurbolinksSharedViewModel
    private lateinit var delegate: TurbolinksFragmentDelegate

    val webView: WebView? get() = delegate.webView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TurbolinksSharedViewModel.get(requireActivity())
        delegate = TurbolinksFragmentDelegate(this, this)

        val location = arguments?.getString("location") ?:
                throw IllegalArgumentException("A location argument must be provided")
        delegate.onCreate(location)
    }

    override fun onStart() {
        super.onStart()

        val activity = context as? TurbolinksActivity ?:
                throw RuntimeException("The fragment Activity must implement TurbolinksActivity")
        delegate.onStart(activity)
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    override fun onProvideDelegate(): TurbolinksFragmentDelegate {
        return delegate
    }

    override fun onSetModalResult(result: TurbolinksModalResult) {
        viewModel.modalResult = result
    }

    override fun onGetModalResult(): TurbolinksModalResult? {
        return viewModel.modalResult
    }

    override fun onSetupToolbar() {
        onProvideToolbar()?.let {
            NavigationUI.setupWithNavController(it, findNavController())
            it.setNavigationOnClickListener {
                delegate.navigateUp()
            }
        }
    }

    override fun onTitleChanged(title: String) {
        onProvideToolbar()?.title = title
    }

    fun navigate(location: String, action: String = "advance") {
        delegate.navigate(location, action)
    }
}
