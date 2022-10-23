package com.yellowstar.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;


import static com.yellowstar.activity.DashBoard.bmp;
import static com.yellowstar.activity.DashBoard.BITMAP1;
import static com.yellowstar.activity.DashBoard.BITMAP2;
import static com.yellowstar.activity.DashBoard.BITMAP3;
import static com.yellowstar.activity.DashBoard.txtLocationn;
import static com.yellowstar.activity.DashBoard.DATA1;
import static com.yellowstar.activity.DashBoard.DATA2;
import static com.yellowstar.activity.DashBoard.DATA3;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yellowstar.GPSTracker;
import com.yellowstar.database.Shaved_shared_preferences;

import com.yellowstar.R;

public class HomeScreen extends BaseActivity
{
    LinearLayout relativeLayout1,relativeLayout2,relativeLayout3;
    TextView txtdetail;
    ImageView backBtn;
    Shaved_shared_preferences sharedPref;
    String LocationInfo ="";
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static Boolean boll = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

     //   Utils utils = new Utils();
     //   utils.setStatusBarGradiant(HomeScreen.this);

        sharedPref = new Shaved_shared_preferences(HomeScreen.this);

        txtdetail = (TextView) findViewById(R.id.txtdetail);
        relativeLayout1 = (LinearLayout) findViewById(R.id.r1);
        relativeLayout2 = (LinearLayout) findViewById(R.id.r2);
        relativeLayout3 = (LinearLayout) findViewById(R.id.r3);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        txtdetail.setText(sharedPref.get_Location());

        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    private void Open(int val) {
        if (val == 1) {

          //  Log.e("chwckkckckckckc",LocationInfo+"-----"+checkPermission()+"----"+turnGPSOn());

            if (checkPermission()) {
                if(turnGPSOn()) {

                    bmp = null;
                    BITMAP1 = null;
                    BITMAP2 = null;
                    BITMAP3 = null;
                    txtLocationn = "";

                    DATA1 = "";
                    DATA2 = "";
                    DATA3 = "";

                    Intent dash_intent = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                }
            } else {
                requestPermission();
            }


        } else if (val == 2) {
            Intent dash_intent = new Intent(getApplicationContext(), SetLocationActivity.class);
            startActivity(dash_intent);
            overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
            finish();
        } else {
            Intent dash_intent = new Intent(getApplicationContext(), Performance.class);
            startActivity(dash_intent);
            overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
        }
    }


        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.backBtn:
                    CustomDialogClass cdd=new CustomDialogClass(HomeScreen.this);
                    cdd.show();
                    break;
                case R.id.r1:
                    Open(1);
                    break;
                case R.id.r2:
                    Open(2);
                    break;
                case R.id.r3:
                    Open(3);
                    break;

            }
        }

    public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_logout);
            yes = (Button) findViewById(R.id.btn_yes);
            no = (Button) findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:
                    dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            sharedPref.set_Login(0);
                          //  sharedPref.set_Dev("");
                            finish();
                            finishAffinity();
                        }
                    },500);

                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("ppapapaapp",requestCode+"===="+permissions+"----"+grantResults[0]+"----"+grantResults[1]);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (!locationAccepted) {//Toast.makeText(getApplicationContext(), "Need to Grant Location Permission", Toast.LENGTH_LONG).show();
                        CustomDialogClassP cdd=new CustomDialogClassP(HomeScreen.this,"Location");
                        cdd.show();
                    }
                    else  if (!cameraAccepted) {//Toast.makeText(getApplicationContext(), "Need to Grant Camera Permission", Toast.LENGTH_LONG).show();
                        CustomDialogClassP cdd=new CustomDialogClassP(HomeScreen.this,"Camera");
                        cdd.show();}
                    else {

                        if(turnGPSOn())
                        {
                            bmp = null;
                            BITMAP1 = null;
                            BITMAP2 = null;
                            BITMAP3 = null;
                            txtLocationn = "";

                            DATA1 = "";
                            DATA2 = "";
                            DATA3 = "";

                            Intent dash_intent = new Intent(getApplicationContext(), DashBoard.class);
                            startActivity(dash_intent);
                            overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                        }
                    }
                }
                break;
        }
    }

    public class CustomDialogClassP extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;
        TextView txt_dia;
        String text;

        public CustomDialogClassP(Activity a, String text) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.text = text;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog);
            txt_dia = (TextView) findViewById(R.id.txt_dia);
            yes = (Button) findViewById(R.id.btn_yes);
            no = (Button) findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
            txt_dia.setText("Please Turn on "+text+" permissions.");
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:
                    dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    },700);

                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }


    public boolean turnGPSOn() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            GPSTracker mGPS = new GPSTracker(this);
            String lat  =  ""+mGPS.getLatitude();
            String lng  =  ""+mGPS.getLongitude();
            LocationInfo =  lat.substring(0,9) + " | " + lng.substring(0,9) ;

            if(mGPS.getLatitude()==0 || mGPS.getLongitude()==0)
            {
                return false;
            }
            else
            {
                sharedPref.set_LocationLatLng(""+LocationInfo);
                return true;
            }
        }
        else
        {
            CustomDialogClassGPS cdd=new CustomDialogClassGPS(HomeScreen.this,"Your GPS seems to be disabled, do you want to enable it?");
            cdd.show();
        }
        return false;
    }


    public class CustomDialogClassGPS extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;
        TextView txt_dia;
        String text;

        public CustomDialogClassGPS(Activity a, String text) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.text = text;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialoggps);
            txt_dia = (TextView) findViewById(R.id.txt_dia);
            yes = (Button) findViewById(R.id.btn_yes);
            no = (Button) findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
            txt_dia.setText(text);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:
                    dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    },700);

                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}