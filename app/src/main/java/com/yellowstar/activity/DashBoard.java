package com.yellowstar.activity;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yellowstar.Url.APIClient;
import com.yellowstar.Url.APIInterface;
import com.yellowstar.database.YellowStarDb;
import com.yellowstar.database.Shaved_shared_preferences;

import static com.yellowstar.activity.HomeScreen.boll;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.yellowstar.R;
import com.yellowstar.model.Login;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends BaseActivity
{
    private static final String TAG = DashBoard.class.getName();
    TextView txtdetail, photoBtn, photoSubmit;
    RelativeLayout relative;
    EditText pole_num, code1, code2, code3,location_remark;
    private ImageView backBtn;

    public static String DATA1 = "";
    public static String DATA2 = "";
    public static String DATA3 = "";
    public static String REMARK = "";
    public static String txtLocationn = "";
    public static String DATE_TIME = "";

    public static Bitmap BITMAP1 = null;
    public static Bitmap BITMAP2 = null;
    public static Bitmap BITMAP3 = null;
    public static Bitmap bmp = null;
    public static Boolean take_photo = false;
    //public static Bitmap bitmapp = null;

    public static File imageFile = null;
    public String location_info = null;
    Shaved_shared_preferences sharedPref;
    String Device_Info = "";
    String INFO = "", POLE = "", LOCATION = "";
    RelativeLayout linear1, linear2, linear3;
    ImageView image, image1, image2, image3;
    TextView data1, data2, data3, infotext;
    FrameLayout frame;
    YellowStarDb indiaSolarDb;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

  //      Utils utils = new Utils();
   //     utils.setStatusBarGradiant(DashBoard.this);

        indiaSolarDb = new YellowStarDb(getApplicationContext());
        sharedPref = new Shaved_shared_preferences(DashBoard.this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        int count = indiaSolarDb.GETLOCATION().size();
        Log.e("sizee__db", "" + count);

        LOCATION = sharedPref.get_Location();
        location_info = sharedPref.get_LocationLatLng();
        Device_Info = sharedPref.get_Dev();
        INFO = LOCATION + "\n" + Device_Info + "\n" + location_info;

        relative = (RelativeLayout) findViewById(R.id.relative);
        linear1 = (RelativeLayout) findViewById(R.id.linear1);
        linear2 = (RelativeLayout) findViewById(R.id.linear2);
        linear3 = (RelativeLayout) findViewById(R.id.linear3);
        txtdetail = (TextView) findViewById(R.id.txtdetail);
        photoBtn = (TextView) findViewById(R.id.photoBtn);
        photoSubmit = (TextView) findViewById(R.id.photoSubmit);
        pole_num = (EditText) findViewById(R.id.pole_num);

        code1 = (EditText) findViewById(R.id.code1);
        code2 = (EditText) findViewById(R.id.code2);
        code3 = (EditText) findViewById(R.id.code3);
        location_remark = (EditText) findViewById(R.id.location_remark);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image = (ImageView) findViewById(R.id.image);
        frame = (FrameLayout) findViewById(R.id.frame);

        infotext = (TextView) findViewById(R.id.infotext);
        data1 = (TextView) findViewById(R.id.data1);
        data2 = (TextView) findViewById(R.id.data2);
        data3 = (TextView) findViewById(R.id.data3);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
        photoSubmit.setOnClickListener(this);

        txtdetail.setText("" + INFO);

        //  device_info();
        linear1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                scanQR(1);
            }
        });

        linear2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                scanQR(2);
            }
        });

        linear3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                scanQR(3);
            }
        });

        code1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("texttt___", "" + s);
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = "" + s;
                Log.e("texttt", "" + text);
                if (data1.getText().toString().equalsIgnoreCase("") && text.equalsIgnoreCase("")) {
                    DATA1 = "";
                } else {
                    DATA1 = "" + text;
                }

                boll = false;
                onResume();
            }
        });

        code2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = "" + s;
                if (data2.getText().toString().equalsIgnoreCase("") && text.equalsIgnoreCase("")) {
                    DATA2 = "";
                } else {
                    DATA2 = "" + text;
                }
                boll = false;
                onResume();
            }
        });

        code3.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = "" + s;
                if (data3.getText().toString().equalsIgnoreCase("") && text.equalsIgnoreCase("")) {
                    DATA3 = "";
                } else {
                    DATA3 = "" + text;

                }
                boll = false;
                onResume();
            }
        });

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmp = screenShot(view);
            }
        });
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void scanQR(int val)
    {
        POLE = pole_num.getText().toString();
        REMARK = location_remark.getText().toString();

        if(val==1)
        {
            DATA1 = "" ;
            code1.setText("");
        }
        else if(val==2)
        {
            DATA2 = "" ;
            code2.setText("");
        }
        else if(val==3)
        {
            DATA3 = "" ;
            code3.setText("");
        }

        if(POLE.equalsIgnoreCase(""))
        {
            showToast("Please Enter Pole Number");
        }
        else {

            boll = true;


            Intent dash_intent = new Intent(getApplicationContext(), ScannedBarcodeActivity.class);
            dash_intent.putExtra("val", "" + val);
            startActivity(dash_intent);
            overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if(!DATA1.equalsIgnoreCase("")) {linear1.setBackgroundColor(getResources().getColor(R.color.green));data1.setText(""+DATA1);  BITMAP1 = encodeToQrCode(DATA1,50,50); image1.setImageBitmap(BITMAP1);}
        else {linear1.setBackgroundColor(getResources().getColor(R.color.white));data1.setText(""+DATA1); BITMAP1 = null;}

        if(!DATA2.equalsIgnoreCase("")) {linear2.setBackgroundColor(getResources().getColor(R.color.green));data2.setText(""+DATA2); BITMAP2 = encodeToQrCode(DATA2,50,50); image2.setImageBitmap(BITMAP2);}
        else {linear2.setBackgroundColor(getResources().getColor(R.color.white));data2.setText(""+DATA2); BITMAP2 = null;}

        if(!DATA3.equalsIgnoreCase("")) {linear3.setBackgroundColor(getResources().getColor(R.color.green));data3.setText(""+DATA3);  BITMAP3 = encodeToQrCode(DATA3,50,50);  image3.setImageBitmap(BITMAP3);}
        else {linear3.setBackgroundColor(getResources().getColor(R.color.white));data3.setText(""+DATA3); BITMAP3 = null;}


        if(boll==true)
        {
            code1.setText(""+DATA1);
            code2.setText(""+DATA2);
            code3.setText(""+DATA3);

        }

        Log.e("bollboll",""+boll);
        Log.e("bollboll",""+DATA1);
        Log.e("bollboll",""+DATA2);
        Log.e("bollboll",""+DATA3);

        boll = false;

        Log.e("fileUrifileUri__",""+BITMAP1);
        Log.e("fileUrifileUri__",""+BITMAP2);
        Log.e("fileUrifileUri__",""+BITMAP3);
        Log.e("fileUrifileUri_ccc_",""+imageFile);
        // Log.e("fileUrifileUri_vv_",""+bitmapp);
        Log.e("fileUrifileUri_xx_",""+bmp);
        Log.e("fileUrifileUri_xx_",""+txtLocationn);


        if(bmp!=null &&  take_photo==true)
        {
            Log.e("fileUrifileUri_bmp_",""+bmp);

            photoBtn.setText("RETAKE PHOTO");
            photoSubmit.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);

            image1.setImageBitmap(BITMAP1);
            image2.setImageBitmap(BITMAP2);
            image3.setImageBitmap(BITMAP3);
            image.setImageBitmap(bmp);
            image.setRotation(90);


            //  BitmapDrawable ob = new BitmapDrawable(getResources(), bitmapp);
            //  image.setBackground(ob);
            //  image.setRotation(90);
            String locationinfo = ""+txtLocationn;
            infotext.setText(""+locationinfo);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    frame.callOnClick();

                }
            },1500);

//            Bitmap bitmappp =   takeScreenShot(DashBoard.this);
//
//            Log.e("fileUrifileUri_",""+bitmappp);
//            image.setImageBitmap(null);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                 //   image111.setRotation(0);
//                    image.setImageBitmap(bitmappp);
//
//                    Log.e("fileUrifileUri_vv_",""+bitmappp);
//                }
//            },3000);
            take_photo = false;
        }
    }


    public static Bitmap encodeToQrCode(String text, int width, int height){
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try {
            matrix = writer.encode(text, BarcodeFormat.QR_CODE, 50, 50);
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                bmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.photoSubmit:

                REMARK = location_remark.getText().toString();

                if(!REMARK.equalsIgnoreCase(""))
                {
                    CustomDialogClassx cdd=new CustomDialogClassx(DashBoard.this);
                    cdd.show();
                }
                else
                {
                    showToast("Enter Remark");
                }

                break;
            case R.id.photoBtn:
                Boolean bol = false;
                DATA1 = code1.getText().toString();
                DATA2 = code2.getText().toString();
                DATA3 = code3.getText().toString();
                REMARK = location_remark.getText().toString();

                POLE = pole_num.getText().toString();
                Log.e("pollll__",""+POLE);
                sharedPref.set_Dev(POLE);

                if(!DATA1.equalsIgnoreCase(""))
                {
                    bol = true;
                    showToast("Scan or Enter Luminary QR");
                }
                /*else if(DATA2.equalsIgnoreCase(""))
                {
                    showToast("Scan Battery QR");
                }
                else if(DATA3.equalsIgnoreCase(""))
                {
                    showToast("Scan Panel QR");
                }*/

                if(bol==true)
                {
                    boll = false;
                    take_photo = true;
                    Intent dash_intent = new Intent(getApplicationContext(), OpenCamera.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                }
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public class CustomDialogClassx extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;

        public CustomDialogClassx(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.submit_dialog);
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

                    int count = indiaSolarDb.GETLOCATION().size()+1;
                    Log.e("sizee__dbbb___",""+count);
                    Log.e("sizee__dbbb_1__",""+DATA1);
                    Log.e("sizee__dbbb_2__",""+DATA2);
                    Log.e("sizee__dbbb_3__",""+DATA3);

                    //   byte[] bt = bitmapToByteArray(bmp);

                    ByteArrayOutputStream boas = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, boas ); //bm is the bitmap object
                    byte[] bt = boas .toByteArray();
                    String encodedImage = Base64.encodeToString(bt, Base64.DEFAULT);

                    Log.e("sizee__dbbb_bt__",""+bt);

                    JSONObject paramObject = new JSONObject();
                    if(isInternetConnection(getApplicationContext()))
                    {

                 /*       RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type", "Add")
                                .addFormDataPart("file", "data:image/png;base64,"+encodedImage)
                                .addFormDataPart("uname", sharedPref.get_Uname())
                                .addFormDataPart("SID", sharedPref.get_SID())
                                .addFormDataPart("district_id", sharedPref.get_DistrictID())
                                .addFormDataPart("block_id", sharedPref.get_BlockID())
                                .addFormDataPart("panchayat_id", sharedPref.get_PunchID())
                                .addFormDataPart("ward_id", sharedPref.get_WardID())
                                .addFormDataPart("pole_id", POLE)
                                .addFormDataPart("luminary_qr", ""+DATA1)
                                .addFormDataPart("battery_qr", ""+DATA2)
                                .addFormDataPart("panel_qr", ""+DATA3)
                                .build();*/

                        SEND_SERVER(TAG,encodedImage);
                    }
                    else {
                        indiaSolarDb.insertContact("" + count, sharedPref.get_Location(), sharedPref.get_State(), sharedPref.get_DistrictID(),
                                sharedPref.get_BlockID(), sharedPref.get_PunchID(), sharedPref.get_WardID(), "Android", POLE, sharedPref.get_LocationLatLng(), DATE_TIME, DATA1, DATA2, DATA3, txtLocationn, bt,REMARK);

                                BITMAP1 = null;
                                BITMAP2 = null;
                                BITMAP3 = null;
                                bmp = null;
                                imageFile = null;
                                txtLocationn = "";
                                REMARK = "";

                                DATA1 = "";
                                DATA2 = "";
                                DATA3 = "";

                                photoBtn.setText("TAKE PHOTO");
                                frame.setVisibility(View.GONE);

                                Intent dash_intent = new Intent(getApplicationContext(), Performance.class);
                                startActivity(dash_intent);
                                overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                                finish();
                                finishAffinity();
                    }

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

    public void SEND_SERVER(String TAG,String imagebase64)
    {
        startProgressDialog();

        Call<Login> call1 = apiInterface.SendServer("Add", sharedPref.get_Uname(), sharedPref.get_SID(),imagebase64,
                sharedPref.get_DistrictID(),sharedPref.get_BlockID(),sharedPref.get_PunchID(),sharedPref.get_WardID(),
                POLE,""+DATA1,""+DATA2,""+DATA3);
        call1.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
                stopProgressDialog();
                Log.e(TAG,"asm__"+response.body().getStatus());

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    showToast(""+response.body().getDetail());

                    Intent dash_intent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                    finish();
                    finishAffinity();
                }
                else
                {
                    showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<Login> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"asm_c_"+t.getMessage());
                call.cancel();
            }
        });
    }


//    public class CustomDialogClass extends Dialog implements
//            android.view.View.OnClickListener {
//
//        public Activity c;
//        public Dialog d;
//        public Button yes, no;
//        public TextView txt_dia;
//        String text ="";
//
//        public CustomDialogClass(Activity a,String text_) {
//            super(a);
//            // TODO Auto-generated constructor stub
//            this.c = a;
//            this.text = text_;
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setContentView(R.layout.custom_logout);
//            txt_dia = (TextView) findViewById(R.id.txt_dia);
//            yes = (Button) findViewById(R.id.btn_yes);
//            no = (Button) findViewById(R.id.btn_no);
//            yes.setOnClickListener(this);
//            no.setOnClickListener(this);
//
//            txt_dia.setText(text);
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btn_yes:
//                    dismiss();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run()
//                        {
//                            if(text.contains("Logout")) {
//                                sharedPref.set_Login(0);
//                                sharedPref.set_Dev("");
//                                finish();
//                                finishAffinity();
//                            }
//                            else {
//                                Intent intent =  new Intent(getApplicationContext(), SetLocationActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.fade_in_fragment,R.anim.fade_out_fragment);
//                                finish();
//                            }
//                        }
//                    },500);
//
//                    break;
//                case R.id.btn_no:
//                    dismiss();
//                    break;
//                default:
//                    break;
//            }
//            dismiss();
//        }
//    }

}