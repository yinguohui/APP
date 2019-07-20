package com.example.ygh.app.pro.attention.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ygh.app.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private TextView mTitle;
    private ImageButton mBackBtn;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();
        initView();
    }

    public void initView() {
        mWebView =  findViewById(R.id.web_webview);
        mWebView.loadUrl("https://www.baidu.com");
        mWebView.requestFocusFromTouch();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                if(url.startsWith("https:://")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });
        /**判断加载过程*/
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成

                } else {
                    // 加载中

                }
            }
        });
        initListener();
    }

    public void initListener() {
        /**打开页面时， 自适应屏幕*/
        WebSettings webSettings =  mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(true);

    }

}