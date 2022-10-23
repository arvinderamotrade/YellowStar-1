package com.yellowstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yellowstar.Url.APIClient;
import com.yellowstar.Url.APIInterface;
import com.yellowstar.database.YellowStarDb;
import com.yellowstar.database.Shaved_shared_preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.yellowstar.R;
import com.yellowstar.model.Login;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPending extends BaseActivity
{
    private static final String TAG = DashBoard.class.getName();
    TextView txtdetail, photoSubmit;
    RelativeLayout relative;
    TextView pole_num, code1, code2, code3,location_remark;
    private ImageView backBtn;
    RelativeLayout linear1, linear2, linear3;
    public static File imageFile = null;
    Shaved_shared_preferences sharedPref;
    String INFO = "", POLE = "",REMARK="";
    String DATA1 = "", DATA2 = "",DATA3="",DATE_TIME="",txtLocationn="";
    String DistrictID = "", BlockID = "",PunchID="",WardID="";
    ImageView image;
    public static Bitmap bmp = null;
    YellowStarDb indiaSolarDb;
    APIInterface apiInterface;
    String ID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pending);

   //     Utils utils = new Utils();
   //     utils.setStatusBarGradiant(DetailPending.this);

        indiaSolarDb = new YellowStarDb(getApplicationContext());
        sharedPref = new Shaved_shared_preferences(DetailPending.this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        int count = indiaSolarDb.GETLOCATION().size();
        Log.e("sizee__db", "" + count);
        Log.e("sizee__db_date_", "" + ID);

        if(count>0) {
            for (int k = 0; k < count; k++) {
                if (indiaSolarDb.getAllData().get(k).getId().equalsIgnoreCase(ID)) {
                    INFO =  indiaSolarDb.getAllData().get(k).getLocation();
                    POLE =  indiaSolarDb.getAllData().get(k).getPole();
                    DATA1 =  indiaSolarDb.getAllData().get(k).getLuminary();
                    DATA2 =  indiaSolarDb.getAllData().get(k).getBattery();
                    DATA3 =  indiaSolarDb.getAllData().get(k).getPanel();
                    DistrictID =  indiaSolarDb.getAllData().get(k).getDistrict();
                    BlockID =  indiaSolarDb.getAllData().get(k).getBlock();
                    PunchID =  indiaSolarDb.getAllData().get(k).getPanchayat();
                    WardID =  indiaSolarDb.getAllData().get(k).getWard();
                    REMARK =  indiaSolarDb.getAllData().get(k).getRemark();

                    bmp = convertCompressedByteArrayToBitmap(indiaSolarDb.getAllData().get(k).getPhoto());

                    Log.e("sizee__db_loca_", "" + POLE);
                }
            }
        }

        relative = (RelativeLayout) findViewById(R.id.relative);
        linear1 = (RelativeLayout) findViewById(R.id.linear1);
        linear2 = (RelativeLayout) findViewById(R.id.linear2);
        linear3 = (RelativeLayout) findViewById(R.id.linear3);
        txtdetail = (TextView) findViewById(R.id.txtdetail);
        photoSubmit = (TextView) findViewById(R.id.photoSubmit);
        pole_num = (TextView) findViewById(R.id.pole_num);
        location_remark = (TextView) findViewById(R.id.location_remark);

        code1 = (TextView) findViewById(R.id.code1);
        code2 = (TextView) findViewById(R.id.code2);
        code3 = (TextView) findViewById(R.id.code3);

        image = (ImageView) findViewById(R.id.image);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);
        photoSubmit.setOnClickListener(this);

        txtdetail.setText("" + INFO);
        pole_num.setText("" + POLE);
        location_remark.setText("" + REMARK);
    }

    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(!DATA1.equalsIgnoreCase("")) {linear1.setBackgroundColor(getResources().getColor(R.color.green)); }
        else {linear1.setBackgroundColor(getResources().getColor(R.color.white));}

        if(!DATA2.equalsIgnoreCase("")) {linear2.setBackgroundColor(getResources().getColor(R.color.green));}
        else {linear2.setBackgroundColor(getResources().getColor(R.color.white));}

        if(!DATA3.equalsIgnoreCase("")) {linear3.setBackgroundColor(getResources().getColor(R.color.green));}
        else {linear3.setBackgroundColor(getResources().getColor(R.color.white)); }


        code1.setText(""+DATA1);
        code2.setText(""+DATA2);
        code3.setText(""+DATA3);

        image.setImageBitmap(bmp);

        Log.e("bollboll",""+DATA1);
        Log.e("bollboll",""+DATA2);
        Log.e("bollboll",""+DATA3);
        Log.e("bollboll",""+bmp);
        Log.e("bollboll",""+DistrictID);
        Log.e("bollboll",""+BlockID);
        Log.e("bollboll",""+PunchID);
        Log.e("bollboll",""+WardID);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.photoSubmit:
                CustomDialogClassx cdd=new CustomDialogClassx(DetailPending.this);
                cdd.show();
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
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, boas); //bm is the bitmap object
                    byte[] bt = boas .toByteArray();
                    String encodedImage = Base64.encodeToString(bt, Base64.DEFAULT);

                    Log.e("sizee__dbbb_bt__",""+bt);

                    JSONObject paramObject = new JSONObject();
                    if(isInternetConnection(getApplicationContext()))
                    {
                        try {
                            paramObject.put("file", "g54g5g45");
                            paramObject.put("type", "Add");
                            paramObject.put("uname", sharedPref.get_Uname());
                            paramObject.put("SID", sharedPref.get_SID());
                            paramObject.put("district_id", DistrictID);
                            paramObject.put("block_id", BlockID);
                            paramObject.put("panchayat_id", PunchID);
                            paramObject.put("ward_id", WardID);
                            paramObject.put("pole_id", POLE);
                            paramObject.put("luminary_qr", ""+DATA1);
                            paramObject.put("battery_qr", ""+DATA2);
                            paramObject.put("panel_qr", ""+DATA3);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        Log.e("sizee__dbbb_Object__",""+paramObject);

                        SEND_SERVER(TAG,encodedImage);
                    }
                    else {
                        showToast("No Internet Connection");
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

                    indiaSolarDb.delrow(ID);

                    Intent dash_intent = new Intent(getApplicationContext(), Performance.class);
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


    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;
        public TextView txt_dia;
        String text ="";

        public CustomDialogClass(Activity a,String text_) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.text = text_;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_logout);
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
                        public void run()
                        {
                            if(text.contains("Logout")) {
                                sharedPref.set_Login(0);
                                sharedPref.set_Dev("");
                                finish();
                                finishAffinity();
                            }
                            else {
                                Intent intent =  new Intent(getApplicationContext(), SetLocationActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in_fragment,R.anim.fade_out_fragment);
                                finish();
                            }
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

}