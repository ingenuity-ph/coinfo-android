package jeremypacabis.coinfo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.reactivex.disposables.CompositeDisposable;
import jeremypacabis.coinfo.R;

public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable disposable;
    protected Context context;
    protected ProgressDialog progressDialog;

    public BaseActivity() {
        disposable = new CompositeDisposable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();

    }

    protected void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(
                    Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    protected void hideKeyboard(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                    Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    protected View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    };
}
