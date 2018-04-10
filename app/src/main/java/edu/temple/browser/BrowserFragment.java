package edu.temple.browser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;


public class BrowserFragment extends Fragment {


    WebView webView;
    private String currentUrl;
    updateURL updateURL;

    public BrowserFragment() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        webView = (WebView) view.findViewById(R.id.webview);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyBrowser());

        currentUrl = getArguments().getString("url").toString();
        sendURL(currentUrl);

        webView.loadUrl(currentUrl);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        updateURL = (updateURL) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("webViewUrl",url);
            sendURL(url);
            return true;
        }
    }

    public interface updateURL{
        public void currentURL(String url);
    }

    public void sendURL(String url){
        updateURL.currentURL(url);
    }


    @Override
    public void onDestroy() {
        //webView.clearHistory();
        super.onDestroy();
    }
}
