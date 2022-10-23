package com.yellowstar.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.yellowstar.R;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public LayoutInflater inflater;
    private InputMethodManager inputMethodManager;
    private Toast toast;
    public PermissionCallback permCallback;
    private static final String TAG = BaseActivity.class.getName();
    public Dialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // (BaseActivity.this).overridePendingTransition(R.anim.inside,R.anim.inside_);
        inputMethodManager = (InputMethodManager) this.getSystemService(BaseActivity.INPUT_METHOD_SERVICE);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

      //  Utils utils = new Utils();
     //   utils.setStatusBarGradiant(BaseActivity.this);

        initializeProgressDialog();
    }


    private void initializeProgressDialog()
    {
        progressDialog = new Dialog(this, R.style.transparent_dialog_borderless);
        View view = View.inflate(this, R.layout.progress_dialog, null);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(view);


        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // txtMsgTV = (TextView) view.findViewById(R.id.txtMsgTV);
        progressDialog.setCancelable(true);
        //  progressDialog.setCancelable(false);
    }

    public boolean hideSoftKeyboard() {
        try {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showToast(String msg) {
        toast.setText(msg);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },400);
    }

    public void LOG(String TAG,String response)
    {
        Log.e(TAG,""+response);
    }


    @Override
    public void onClick(View v) {

    }

    public void checkSelfPermission(String[] perms, PermissionCallback permCallback) {
        this.permCallback = permCallback;
        ActivityCompat.requestPermissions(this, perms, 99);
    }



    public interface PermissionCallback {
        void permGranted();

        void permDenied();
    }

    public void startProgressDialog()
    {
        if (progressDialog != null && !progressDialog.isShowing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void stopProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

  /*  public  boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }*/

    public static boolean isInternetConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}
