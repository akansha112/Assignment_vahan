package com.example.assignment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.webview)
        progressBar = findViewById(R.id.web_progress)
        // Get the URL from the Intent
        val websiteUrl = intent.getStringExtra("WEBSITE_URL")

        // Load the website URL
        if (websiteUrl != null) {
            Log.d("WebViewActivity", "Received URL: $websiteUrl")

            webView.loadUrl(websiteUrl)
        }
             val webSettings=webView.settings
            webSettings.javaScriptEnabled=true
            webView.webViewClient=object :WebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    Log.d("WebViewActivity", "Page started loading: $url")

                    progressBar.visibility= View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.d("WebViewActivity", "Page not started loading: $url")

                    progressBar.visibility=View.GONE
         super.onPageFinished(view, url)
                }

            }

        }

            }


