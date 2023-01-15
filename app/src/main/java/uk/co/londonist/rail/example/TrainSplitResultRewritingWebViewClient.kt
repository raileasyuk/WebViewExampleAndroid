package uk.co.londonist.rail.example

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

private const val RESULTS_URI = "https://rail.londonist.co.uk/results"

class TrainSplitResultRewritingWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request?.url?.equals(Uri.parse(RESULTS_URI)) == true) {
            // We don't want this to open separately in the user's browser
            view?.loadUrl(request.url.toString()) // Tell the WebView to navigate
            return true // we overrode the default loading behaviour
        }
        return false // in all other cases, use normal WebView URL handling
    }
}
