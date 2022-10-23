package com.yellowstar.activity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yellowstar.GPSTracker;
import com.yellowstar.Url.APIClient;
import com.yellowstar.Url.APIInterface;
import com.yellowstar.database.BlockDb;
import com.yellowstar.database.DistrictDb;
import com.yellowstar.database.PanchDb;
import com.yellowstar.database.Shaved_shared_preferences;
import com.yellowstar.database.WardDb;
import com.yellowstar.model.District;

import com.yellowstar.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetLocationActivity extends BaseActivity
{
    private static final String TAG = SetLocationActivity.class.getName()+"_API";
    ImageView backBtn,pending;
    Spinner spin_dis,spin_block,spin_punch,spin_war,spin_device;

    TextView AddPoleBtn,location;
   // String Device_Info="",DEVICE="";
    String WARD="",PUNCH="",BLOCK="",DIST="",STATE="Bihar";
    String WARD_="",PUNCH_="",BLOCK_="",DIST_="",STATE_="Bihar";
    String DEVICE_ID="",WARD_ID="",PUNCH_ID="",BLOCK_ID="",DIST_ID="";
    String PID="1",BRANCH="5",LocationInfo="";
    String[] distId={};
    String[] distName={};
    String[] blockId={};
    String[] blockName={};
    String[] punchId={};
    String[] punchName={};
    String[] wardId={};
    String[] wardName={};
  //  String[] devId={};
 //   String[] devName={};

    Shaved_shared_preferences sharedPref;
    APIInterface apiInterface;

    DistrictDb districtDb;
    BlockDb blockDb;
    PanchDb panchDb;
    WardDb wardDb;

    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        districtDb = new DistrictDb(getApplicationContext());
        blockDb = new BlockDb(getApplicationContext());
        panchDb = new PanchDb(getApplicationContext());
        wardDb = new WardDb(getApplicationContext());

    //    Utils utils = new Utils();
    //    utils.setStatusBarGradiant(SetLocationActivity.this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        sharedPref = new Shaved_shared_preferences(SetLocationActivity.this);

        AddPoleBtn = (TextView) findViewById(R.id.AddPoleBtn);
        location = (TextView) findViewById(R.id.location);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        pending = (ImageView) findViewById(R.id.pending);
        spin_dis = (Spinner) findViewById(R.id.spin_dis);
        spin_block = (Spinner) findViewById(R.id.spin_block);
        spin_punch = (Spinner) findViewById(R.id.spin_punch);
        spin_war = (Spinner) findViewById(R.id.spin_war);
        spin_device = (Spinner) findViewById(R.id.spin_device);

        backBtn.setOnClickListener(this);
        pending.setOnClickListener(this);
        AddPoleBtn.setOnClickListener(this);


        PID = sharedPref.get_PID();
        BRANCH = sharedPref.get_Branch();

        spin_dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                DIST = parent.getItemAtPosition(position).toString();
                DIST_ID =  distId[position];

                sharedPref.set_DistrictID(DIST_ID);

                LOG(TAG,DIST+"----"+DIST_ID);
                BLOCK_ = "";BLOCK_ID="";
                PUNCH_ = "";PUNCH_ID="";
                WARD_ = "";WARD_ID="";
             //   DEVICE = "";DEVICE_ID="";

                blockId = new String[]{};
                blockName = new String[]{};
                punchId = new String[]{};
                punchName = new String[]{};
                wardId = new String[]{};
                wardName = new String[]{};
                //devId = new String[]{};
              //  devName = new String[]{};

                set_blockAdpc();
                set_PunchAdpc();
                set_WardAdpc();
              //  set_DevAdp();

                if(position>0)
                {
                    PID = DIST_ID;
                    BRANCH = "4";

                    SET_BLOCK();
                }

                if(!DIST.equalsIgnoreCase("") && position>0)
                {
                    if(DIST.length()>=3)
                    {
                        DIST_  = "/"+DIST.substring(0,3);
                    }
                    else
                    {
                        DIST_  = "/"+DIST;
                    }
                }

                location.setText(""+STATE_+" "+DIST_+" "+BLOCK_+" "+PUNCH_+" "+WARD_);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                BLOCK = parent.getItemAtPosition(position).toString();
                BLOCK_ID =  blockId[position];

                sharedPref.set_BlockID(BLOCK_ID);

                LOG(TAG,BLOCK+"----"+BLOCK_ID);
                PUNCH_ = "";PUNCH_ID="";
                WARD_ = "";WARD_ID="";
              //  DEVICE = "";DEVICE_ID="";

                punchId = new String[]{};
                punchName = new String[]{};
                wardId = new String[]{};
                wardName = new String[]{};
              //  devId = new String[]{};
              //  devName = new String[]{};

                set_PunchAdpc();
                set_WardAdpc();
              //  set_DevAdp();

                if(position>0)
                {
                    PID = BLOCK_ID;
                    BRANCH = "3";

                    SET_PANCH();
                }

                if(!BLOCK.equalsIgnoreCase("") && position>0)
                {
                    if(BLOCK.length()>=3)
                    {
                        BLOCK_  = "/"+BLOCK.substring(0,3);
                    }
                    else
                    {
                        BLOCK_  = "/"+BLOCK;
                    }
                }

                location.setText(""+STATE_+" "+DIST_+" "+BLOCK_+" "+PUNCH_+" "+WARD_);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_punch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                PUNCH = parent.getItemAtPosition(position).toString();
                PUNCH_ID =  punchId[position];

                sharedPref.set_PunchID(PUNCH_ID);

                LOG(TAG,PUNCH+"----"+PUNCH_ID);
                WARD_ = "";WARD_ID="";
              //  DEVICE = "";DEVICE_ID="";

                wardId = new String[]{};
                wardName = new String[]{};
              //  devId = new String[]{};
              //  devName = new String[]{};

                set_WardAdpc();
              //  set_DevAdp();

                if(position>0)
                {
                    PID = PUNCH_ID;
                    BRANCH = "2";

                    SET_WARD();
                }

                if(!PUNCH.equalsIgnoreCase("") && position>0)
                {
                    PUNCH_  = "/"+PUNCH;
                }

                location.setText(""+STATE_+" "+DIST_+" "+BLOCK_+" "+PUNCH_+" "+WARD_);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_war.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WARD = parent.getItemAtPosition(position).toString();
                WARD_ID = wardId[position];

                sharedPref.set_WardID(WARD_ID);

                LOG(TAG, WARD + "----" + WARD_ID);
              //  DEVICE = "";DEVICE_ID="";

                //  devId = new String[]{};
              //  devName = new String[]{};

               // set_DevAdp();

                if (position > 0) {
                    PID = WARD_ID;
                    BRANCH = "1";

                   // SET_DEVICE();
                }

                if (!WARD.equalsIgnoreCase("") && position > 0) {
                        WARD_ = "/" + WARD;
                }

                location.setText(""+ STATE_ + " " + DIST_ + " " + BLOCK_ + " " + PUNCH_ + " " + WARD_);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        spin_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            //    DEVICE = parent.getItemAtPosition(position).toString();
             //   DEVICE_ID =  devId[position];

                LOG(TAG,DEVICE+"----"+DEVICE_ID);

                if(position>0)
                {
                    PID = DEVICE_ID;
                    BRANCH = "1";

                  //  device_info();
                }
                location.setText(""+STATE_+" "+DIST_+" "+BLOCK_+" "+PUNCH_+" "+WARD_);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(distName.length>0) {for(int i=0;i<distName.length;i++) {if(DIST.equalsIgnoreCase(distName[i].toString())) {spin_dis.setSelection(i);}}}
        if(blockName.length>0) {for(int i=0;i<blockName.length;i++) {if(BLOCK.equalsIgnoreCase(blockName[i].toString())) {spin_block.setSelection(i);}}}
        if(punchName.length>0) {for(int i=0;i<punchName.length;i++) {if(PUNCH.equalsIgnoreCase(punchName[i].toString())) {spin_punch.setSelection(i);}}}
        if(wardName.length>0) {for(int i=0;i<wardName.length;i++) {if(WARD.equalsIgnoreCase(wardName[i].toString())) {spin_war.setSelection(i);}}}

        SET_DISTRICT();

        //device_info();
    }

    private void SET_DISTRICT()
    {
        if(isInternetConnection(getApplicationContext()))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    District district = new District();
                    district.setType("List");
                    district.setUname(""+sharedPref.get_Uname());
                    district.setSID(""+sharedPref.get_SID());
                    district.setPID(PID);
                    district.setBranch(BRANCH);

                    Gson gson = new Gson();
                    String json = gson.toJson(district,District.class);

                    LOG(TAG,json);

                    GET(TAG,district);
                }
            },500);

        }
        else
        {
            set_disAdp();
            showToast("No Internet Connection");
        }
    }


    private void SET_BLOCK()
    {
        if(isInternetConnection(getApplicationContext()))
        {
                    District district = new District();
                    district.setType("List");
                    district.setUname(""+sharedPref.get_Uname());
                    district.setSID(""+sharedPref.get_SID());
                    district.setPID(PID);
                    district.setBranch(BRANCH);

                    Gson gson = new Gson();
                    String json = gson.toJson(district,District.class);

                    LOG(TAG,json);

                    GETB(TAG,district);

        }
        else
        {
            set_blockAdp();
            showToast("No Internet Connection");
        }
    }


    private void SET_PANCH()
    {
        if(isInternetConnection(getApplicationContext()))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    District district = new District();
                    district.setType("List");
                    district.setUname(""+sharedPref.get_Uname());
                    district.setSID(""+sharedPref.get_SID());
                    district.setPID(PID);
                    district.setBranch(BRANCH);

                    Gson gson = new Gson();
                    String json = gson.toJson(district,District.class);

                    LOG(TAG,json);

                    GETP(TAG,district);
                }
            },500);

        }
        else
        {
            set_PunchAdp();
            showToast("No Internet Connection");
        }
    }


    private void SET_WARD()
    {
        if(isInternetConnection(getApplicationContext()))
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    District district = new District();
                    district.setType("List");
                    district.setUname(""+sharedPref.get_Uname());
                    district.setSID(""+sharedPref.get_SID());
                    district.setPID(PID);
                    district.setBranch(BRANCH);

                    Gson gson = new Gson();
                    String json = gson.toJson(district,District.class);

                    LOG(TAG,json);

                    GETW(TAG,district);
                }
            },500);

        } else
        {
            set_WardAdp();
            showToast("No Internet Connection");
        }
    }


 /*   private void SET_DEVICE()
    {
        if(isInternetConnection()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    District district = new District();
                    district.setType("List");
                    district.setUname(""+sharedPref.get_Uname());
                    district.setSID(""+sharedPref.get_SID());
                    district.setPID(PID);
                    district.setBranch(BRANCH);

                    Gson gson = new Gson();
                    String json = gson.toJson(district,District.class);

                    LOG(TAG,json);

                    //GETD(TAG,district);
                }
            },500);

        } else {showToast("No Internet Connection");}
    }
*/

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.backBtn:
                CustomDialogClass cdd=new CustomDialogClass(SetLocationActivity.this);
                cdd.show();
                break;
            case R.id.AddPoleBtn:
                ADD_DATA();
                break;

        }
    }

    private void ADD_DATA()
    {
        if( DIST.equalsIgnoreCase("")|| BLOCK.equalsIgnoreCase("") ||   PUNCH.equalsIgnoreCase("")||   WARD.equalsIgnoreCase(""))//||   DEVICE.equalsIgnoreCase(""))
        {
            showToast("Please Select All Fields");
        }
        else
        {
            sharedPref.set_State(STATE);
            sharedPref.set_District(DIST);
            sharedPref.set_Block(BLOCK);
            sharedPref.set_Punch(PUNCH);
            sharedPref.set_Ward(WARD);
            sharedPref.set_Dev("");

          //  Log.e("chwckkckckckckc",LocationInfo+"-----"+checkPermission()+"----"+turnGPSOn());

            if (checkPermission()) {
                if(turnGPSOn()) {

                    sharedPref.set_Location(location.getText().toString());
                    sharedPref.set_Location_(WARD+"-"+PUNCH+"\n"+BLOCK+"-"+DIST+"\n"+STATE);

                    Intent dash_intent = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(dash_intent);
                    overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                }
            } else {
                requestPermission();
            }
        }
    }

    public void GET(String TAG,District district)
    {
        startProgressDialog();

        Call<District> call1 = apiInterface.GetDistrict(district.getType(),district.getUname(),district.getBranch(),district.getSID(),district.getPID());
        call1.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response)
            {
                stopProgressDialog();
                Log.e(TAG,"response__"+response.body().getStatus());

                districtDb.delall();
                DIST ="";
                DIST_ID ="";

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    if(response.body().getResult().size()>0)
                  {
                      distId = new String[response.body().getResult().size()+1];
                      distName = new String[response.body().getResult().size()+1];

                      districtDb.insert("0","Select District");

                      for(int k=0;k<response.body().getResult().size();k++)
                      {
                          String ID = response.body().getResult().get(k).getId();
                          String Name = response.body().getResult().get(k).getName();

                          Log.e(TAG,"response__"+ID+"--"+Name);

                          districtDb.insert(ID,Name);

                      }
                      set_disAdp();
                  }
                }
                else
                {
                   showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<District> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"response_c_"+t.getMessage());
                call.cancel();
            }
        });
    }

    private void set_disAdp()
    {
        int pos = 0;
        DIST = sharedPref.get_District();
        DIST_ID = sharedPref.get_DistrictID();
        Log.e("dataaa_set_sh_",DIST+"=="+DIST_ID);
        Log.e("dataaa_size",""+districtDb.getAllData().size());
        if(districtDb.getAllData().size()>0)
        {
            for(int k=0;k<districtDb.getAllData().size();k++)
            {
                Log.e("dataaa_set_",districtDb.getAllData().get(k).getID()+"---"+districtDb.getAllData().get(k).getNAME());
                distId[k] = ""+districtDb.getAllData().get(k).getID();
                distName[k] = ""+districtDb.getAllData().get(k).getNAME();

                if(DIST.equalsIgnoreCase(distName[k]))
                {
                    pos = k;
                }
            }
        }

        if(distName.length>0) {
            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa2 = new ArrayAdapter(SetLocationActivity.this, R.layout.dropdown, distName);
            aa2.setDropDownViewResource(R.layout.dropdown);
            spin_dis.setAdapter(aa2);

            spin_dis.setSelection(pos);
        }
    }


    public void GETB(String TAG,District district)
    {
        startProgressDialog();

        Call<District> call1 = apiInterface.GetDistrict(district.getType(),district.getUname(),district.getBranch(),district.getSID(),district.getPID());
        call1.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response)
            {
                stopProgressDialog();
                Log.e(TAG,"response__"+response.body().getStatus());

                blockDb.delall();
                BLOCK ="";
                BLOCK_ID ="";

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    if(response.body().getResult().size()>0)
                  {
                      blockId = new String[response.body().getResult().size()+1];
                      blockName = new String[response.body().getResult().size()+1];

                      blockDb.insert("0","Select Block");

                      for(int k=0;k<response.body().getResult().size();k++)
                      {
                          String ID = response.body().getResult().get(k).getId();
                          String Name = response.body().getResult().get(k).getName();

                          Log.e(TAG,"response__"+ID+"--"+Name);

                          blockDb.insert(ID,Name);
                      }

                      set_blockAdp();
                  }
                }
                else
                {
                   showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<District> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"response_c_"+t.getMessage());
                call.cancel();
            }
        });
    }

    private void set_blockAdpc()
    {
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa3 = new ArrayAdapter(SetLocationActivity.this,R.layout.dropdown,blockName);
        aa3.setDropDownViewResource(R.layout.dropdown);
        spin_block.setAdapter(aa3);
    }

    private void set_blockAdp()
    {
        int pos = 0;
        BLOCK = sharedPref.get_Block();
        BLOCK_ID = sharedPref.get_BlockID();
        Log.e("dataaa_set_sh_",BLOCK+"=="+BLOCK_ID);
        Log.e("dataaa_size",""+blockDb.getAllData().size());
        if(blockDb.getAllData().size()>0)
        {
            for(int k=0;k<blockDb.getAllData().size();k++)
            {
                Log.e("dataaa_set_",blockDb.getAllData().get(k).getID()+"---"+blockDb.getAllData().get(k).getNAME());
                blockId[k] = ""+blockDb.getAllData().get(k).getID();
                blockName[k] = ""+blockDb.getAllData().get(k).getNAME();

                if(BLOCK.equalsIgnoreCase(blockName[k]))
                {
                    pos = k;
                }
            }
        }

        if(blockName.length>0) {
            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa3 = new ArrayAdapter(SetLocationActivity.this,R.layout.dropdown,blockName);
            aa3.setDropDownViewResource(R.layout.dropdown);
            spin_block.setAdapter(aa3);

            spin_block.setSelection(pos);
        }
    }


    public void GETP(String TAG,District district)
    {
        startProgressDialog();

        Call<District> call1 = apiInterface.GetDistrict(district.getType(),district.getUname(),district.getBranch(),district.getSID(),district.getPID());
        call1.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response)
            {
                stopProgressDialog();
                Log.e(TAG,"response__"+response.body().getStatus());

                panchDb.delall();
                PUNCH = "";
                PUNCH_ID = "";

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    if(response.body().getResult().size()>0)
                  {
                      punchId = new String[response.body().getResult().size()+1];
                      punchName = new String[response.body().getResult().size()+1];

                      panchDb.insert("0","Select Panchayat");

                      for(int k=0;k<response.body().getResult().size();k++) {
                          String ID = response.body().getResult().get(k).getId();
                          String Name = response.body().getResult().get(k).getName();

                          Log.e(TAG,"response__"+ID+"--"+Name);
                          panchDb.insert(ID,Name);

                      }
                      set_PunchAdp();
                  }
                }
                else
                {
                   showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<District> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"response_c_"+t.getMessage());
                call.cancel();
            }
        });
    }

    private void set_PunchAdpc()
    {
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa4 = new ArrayAdapter(SetLocationActivity.this,R.layout.dropdown,punchName);
        aa4.setDropDownViewResource(R.layout.dropdown);
        spin_punch.setAdapter(aa4);
    }

    private void set_PunchAdp()
    {
        int pos = 0;
        PUNCH = sharedPref.get_Punch();
        PUNCH_ID = sharedPref.get_PunchID();
        Log.e("dataaa_set_sh_",PUNCH+"=="+PUNCH_ID);
        Log.e("dataaa_size",""+panchDb.getAllData().size());
        if(panchDb.getAllData().size()>0)
        {
            for(int k=0;k<panchDb.getAllData().size();k++)
            {
                Log.e("dataaa_set_",panchDb.getAllData().get(k).getID()+"---"+panchDb.getAllData().get(k).getNAME());
                punchId[k] = ""+panchDb.getAllData().get(k).getID();
                punchName[k] = ""+panchDb.getAllData().get(k).getNAME();

                if(PUNCH.equalsIgnoreCase(punchName[k]))
                {
                    pos = k;
                }
            }
        }

        if(punchName.length>0) {
            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa4 = new ArrayAdapter(SetLocationActivity.this,R.layout.dropdown,punchName);
            aa4.setDropDownViewResource(R.layout.dropdown);
            spin_punch.setAdapter(aa4);

            spin_punch.setSelection(pos);
        }
    }

    public void GETW(String TAG,District district)
    {
        startProgressDialog();

        Call<District> call1 = apiInterface.GetDistrict(district.getType(),district.getUname(),district.getBranch(),district.getSID(),district.getPID());
        call1.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response)
            {
                stopProgressDialog();
                Log.e(TAG,"response__"+response.body().getStatus());

                wardDb.delall();
                WARD = "";
                WARD_ID = "";

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    if(response.body().getResult().size()>0)
                  {
                      wardId = new String[response.body().getResult().size()+1];
                      wardName = new String[response.body().getResult().size()+1];

                      wardDb.insert("0","Select Ward");

                      for(int k=0;k<response.body().getResult().size();k++) {
                          String ID = response.body().getResult().get(k).getId();
                          String Name = response.body().getResult().get(k).getName();

                          Log.e(TAG, "response__" + ID + "--" + Name);
                          wardDb.insert(ID,Name);
                      }
                          set_WardAdp();
                  }
                }
                else
                {
                   showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<District> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"response_c_"+t.getMessage());
                call.cancel();
            }
        });
    }

    private void set_WardAdpc()
    {
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa5 = new ArrayAdapter(this,R.layout.dropdown,wardName);
        aa5.setDropDownViewResource(R.layout.dropdown);
        spin_war.setAdapter(aa5);
    }

    private void set_WardAdp()
    {
        int pos = 0;
        WARD = sharedPref.get_Ward();
        WARD_ID = sharedPref.get_WardID();
        Log.e("dataaa_set_sh_",WARD+"=="+WARD_ID);
        Log.e("dataaa_size",""+wardDb.getAllData().size());
        if(panchDb.getAllData().size()>0)
        {
            for(int k=0;k<panchDb.getAllData().size();k++)
            {
                Log.e("dataaa_set_",wardDb.getAllData().get(k).getID()+"---"+wardDb.getAllData().get(k).getNAME());
                wardId[k] = ""+wardDb.getAllData().get(k).getID();
                wardName[k] = ""+wardDb.getAllData().get(k).getNAME();

                if(WARD.equalsIgnoreCase(wardName[k]))
                {
                    pos = k;
                }
            }
        }

        if(wardName.length>0) {
            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa5 = new ArrayAdapter(this,R.layout.dropdown,wardName);
            aa5.setDropDownViewResource(R.layout.dropdown);
            spin_war.setAdapter(aa5);

            spin_war.setSelection(pos);
        }
    }

 /*   public void GETD(String TAG,District district)
    {
        startProgressDialog();

        Call<District> call1 = apiInterface.GetDistrict(district.getType(),district.getUname(),district.getBranch(),district.getSID(),district.getPID());
        call1.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response)
            {
                stopProgressDialog();
                Log.e(TAG,"response__"+response.body().getStatus());

                if(response.body().getStatus().equalsIgnoreCase("OK"))
                {
                    if(response.body().getResult().size()>0)
                    {
                        devId = new String[response.body().getResult().size()+1];
                        devName = new String[response.body().getResult().size()+1];

                        devId[0] = "0";
                        devName[0] = "Select Device";

                        for(int k=0;k<response.body().getResult().size();k++) {
                            String ID = response.body().getResult().get(k).getId();
                            String Name = response.body().getResult().get(k).getName();

                            Log.e(TAG, "response__" + ID + "--" + Name);

                            devId[k+1] = ID;
                            devName[k+1] = Name;
                        }
                        set_DevAdp();
                    }
                }
                else
                {
                    showToast(""+response.body().getDetail());
                }

            }
            @Override
            public void onFailure(Call<District> call, Throwable t)
            {
                stopProgressDialog();
                Log.e(TAG,"response_c_"+t.getMessage());
                call.cancel();
            }
        });
    }
*/
 /*   private void set_DevAdp()
    {
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.dropdown,devName);
        aa.setDropDownViewResource(R.layout.dropdown);
        spin_device.setAdapter(aa);
    }*/

    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

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
                            sharedPref.set_Dev("");
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

     private void device_info()
    {

        Log.e("TAGxx", "SERIAL: " + Build.SERIAL);
        Log.e("TAGxx","MODEL: " + Build.MODEL);
        Log.e("TAGxx","ID: " + Build.ID);
        Log.e("TAGxx","TIME: " + Build.TIME);
        Log.e("TAGxx","Manufacture: " + Build.MANUFACTURER);
        Log.e("TAGxx","brand: " + Build.BRAND);
        Log.e("TAGxx","type: " + Build.TYPE);
        Log.e("TAGxx","user: " + Build.USER);
        Log.e("TAGxx","BASE: " + Build.VERSION_CODES.BASE);
        Log.e("TAGxx","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.e("TAGxx","SDK  " + Build.VERSION.SDK);
        Log.e("TAGxx","BOARD: " + Build.BOARD);
        Log.e("TAGxx","BRAND " + Build.BRAND);
        Log.e("TAGxx","HOST " + Build.HOST);
        Log.e("TAGxx","FINGERPRINT: "+Build.FINGERPRINT);
        Log.e("TAGxx","Version Code: " + Build.VERSION.RELEASE);

        String BRAND = Build.BRAND;
        String MODEL = Build.MODEL;
       // String ID = Build.ID;
       // String Version_Code =Build.VERSION.RELEASE;

        //Device_Info = DEVICE;//+"\n"+"BRAND "+BRAND+"\n"+"MODEL "+MODEL;//+"\n"+"ID "+ID+"\n"+"Version_Code "+Version_Code;
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
                        CustomDialogClassP cdd=new CustomDialogClassP(SetLocationActivity.this,"Location");
                        cdd.show();
                    }
                    else  if (!cameraAccepted) {//Toast.makeText(getApplicationContext(), "Need to Grant Camera Permission", Toast.LENGTH_LONG).show();
                        CustomDialogClassP cdd=new CustomDialogClassP(SetLocationActivity.this,"Camera");
                        cdd.show();}
                    else {

                        if(turnGPSOn())
                        {
                            sharedPref.set_Location(location.getText().toString());
                            sharedPref.set_Location_(WARD.replace("/","")+"-"+PUNCH.replace("/","")+"\n"+BLOCK.replace("/","")+"-"+DIST.replace("/","")+"\n"+STATE);

                            Intent dash_intent = new Intent(getApplicationContext(), HomeScreen.class);
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
            CustomDialogClassGPS cdd=new CustomDialogClassGPS(SetLocationActivity.this,"Your GPS seems to be disabled, do you want to enable it?");
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