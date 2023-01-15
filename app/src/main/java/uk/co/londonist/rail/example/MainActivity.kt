package uk.co.londonist.rail.example

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import uk.co.londonist.rail.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Grab the WebView
        val jpWebView: WebView = findViewById(R.id.webview)

        // The journey planner and rest of the rail site is a Vue.js app:
        jpWebView.settings.javaScriptEnabled = true

        // This handles opening the search results within the WebView and not a browser app
        jpWebView.webViewClient = TrainSplitResultRewritingWebViewClient()

        // Need to be able to accept 3rd party cookies as we're embedding the journey planner
        // from a different Origin. The cookie stores the search parameters
        CookieManager.getInstance().setAcceptThirdPartyCookies(jpWebView, true);

        // In reality, you'll probably load a real page that embeds the journey planner
        // This is just an example:
        jpWebView.loadUrl("file:///android_asset/index.html")

        // If you wanted to, you could load the embeddable directly instead here
        // For example, if your navigation was rendered natively in the app
        // jpWebView.loadUrl("https://rail.londonist.co.uk/embeddable")
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Properly handle the user hitting the <Back> button
        val jpWebView: WebView = findViewById(R.id.webview)
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (jpWebView.canGoBack()) {
                        jpWebView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
