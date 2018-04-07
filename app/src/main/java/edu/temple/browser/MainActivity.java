package edu.temple.browser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/*

Browser as the name refers lets to access websites.



 */

public class MainActivity extends AppCompatActivity implements BrowserFragment.updateURL {

    EditText editText;
    Button button;
    BrowserFragment browserFragment;
    ArrayList<BrowserFragment> browserFragmentArrayList = new ArrayList<BrowserFragment>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText) findViewById(R.id.URL);
        button = (Button) findViewById(R.id.GO);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                browserFragment = new BrowserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                browserFragment.setArguments(bundle);
                loadFragment(browserFragment);
                browserFragmentArrayList.add(browserFragment);
                index++;

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.New:
                editText.setText("Search or type web address");
                browserFragment = new BrowserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", "");
                browserFragment.setArguments(bundle);
                loadFragment(browserFragment);

                return true;
            case R.id.Prev:

                if (browserFragment.webView.canGoBack()) {
                    browserFragment.webView.goBack();

                    return true;
                }

                if (index > 0) {
                    loadFragment(browserFragmentArrayList.get(--index));
                    return true;
                }

                return true;
            case R.id.Next:
                if (browserFragment.webView.canGoForward()) {
                    browserFragment.webView.goForward();
                    return true;
                }
                if (browserFragmentArrayList.size() > (index + 1)) {
                    loadFragment(browserFragmentArrayList.get(++index));
                    return true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (browserFragment.webView.canGoBack()) {
                browserFragment.webView.goBack();
                return true;
            }

            if (index > 0) {
                loadFragment(browserFragmentArrayList.get(--index));
                return true;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void currentURL(String url) {
        editText.setText(url);
    }

    public void loadFragment(BrowserFragment browserFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.PAGE, browserFragment)
                .commit();
    }


}
