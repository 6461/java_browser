package com.mma.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    EditText uri = null;
    History history = null;
    WebView web = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uri = findViewById(R.id.editURI);
        web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        history = new History();
    }

    public void goAction(View view) {
        String uri = this.uri.getText().toString().trim();

        if (uri != null) {
            if (uri.equals("index.html")) {
                web.loadUrl("file:///android_asset/index.html");
            } else {
                loadPage(uri);
            }
        }
    }

    public void previousAction(View view) {
        URL url = history.getPrevious();

        if (url != null) {
            loadHistory(url.toString());
        }
    }

    public void nextAction(View view) {
        URL url = history.getNext();

        if (url != null) {
            loadHistory(url.toString());
        }
    }

    public void refreshAction(View view) {
        web.reload();
    }

    public void initializeAction(View view) {
        web.evaluateJavascript("javascript:initialize()", null);
    }

    public void shoutAction(View view) {
        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    private void loadPage(String url) {
        url = getURL(url).toString();

        if (url != null) {
            web.loadUrl(url);
            try {
                history.addURL(new URL(url));
            } catch (MalformedURLException ex) {
                Log.e("MalformedURLException", "Virhe");
            }
            System.out.println("Index: " + history.getIndex());
            this.uri.setText(url);
        }
    }

    private void loadHistory(String url) {
        url = getURL(url).toString();

        if (url != null) {
            web.loadUrl(url);
            System.out.println("Index: " + history.getIndex());
            this.uri.setText(url);
        }
    }

    private URL getURL(String url) {
        URL ret = null;

        try {
            ret = new URL(url);
        } catch (MalformedURLException ex) {
            Log.e("MalformedURLException", "Virhe");

            try {
                ret = new URL("http://" + url);
            } catch (MalformedURLException ex1) {
                Log.e("MalformedURLException", "Virhe");
            }
        }

        return ret;
    }
}
