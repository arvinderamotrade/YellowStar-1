package com.yellowstar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yellowstar.Url.APIClient;
import com.yellowstar.Url.APIInterface;
import com.yellowstar.database.Shaved_shared_preferences;
import com.yellowstar.model.Login;

import com.yellowstar.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends BaseActivity
{
    private static final String TAG = LoginPage.class.getName();
    TextView loginBtn,signup;
   // String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-zA-Z0-9._-]+";
    String Uname="",Pass="";
    EditText etEmpId,etPassword;
    ImageView passshow;
    Boolean show = false;

    APIInterface apiInterface;
    Shaved_shared_preferences shaved_shared_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

  //      Utils utils = new Utils();
   //     utils.setStatusBarGradiant(LoginPage.this);

        shaved_shared_preferences = new Shaved_shared_preferences(LoginPage.this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if(shaved_shared_preferences.get_Login()==1)
        {
            if(shaved_shared_preferences.get_Location().equalsIgnoreCase(""))
            {
                Intent dash_intent = new Intent(getApplicationContext(), SetLocationActivity.class);
              //  Intent dash_intent = new Intent(getApplicationContext(), OpenCamera.class);
                startActivity(dash_intent);
                overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                finish();
            }
            else
            {
                Intent dash_intent = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(dash_intent);
                overridePendingTransition(R.anim.fade_in_fragment, R.anim.fade_out_fragment);
                finish();
            }

        }

        loginBtn = (TextView) findViewById(R.id.loginBtn);
        signup = (TextView) findViewById(R.id.signup);
        etEmpId = (EditText) findViewById(R.id.emailId);
        etPassword = (EditText) findViewById(R.id.password);
        passshow = (ImageView) findViewById(R.id.passshow);
        loginBtn.setOnClickListener(this);
        signup.setOnClickListener(this);
        passshow.setOnClickListener(this);

        passshow.setImageDrawable(getResources().getDrawable(R.drawable.hidden_eye));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                LOGIN_SERVER();
                break;
            case R.id.signup:
                showToast("Please Register to Website");
                break;
            case R.id.passshow:
                if(show)
                {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    show = false;
                    passshow.setImageDrawable(getResources().getDrawable(R.drawable.hidden_eye));
                }
                else
                {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show = true;
                    passshow.setImageDrawable(getResources().getDrawable(R.drawable.view_eye));

                }
                break;
        }
    }


    private void LOGIN_SERVER()
    {
        Boolean BOL = false;

        Uname = etEmpId.getText().toString().trim();
        Pass = etPassword.getText().toString().trim();

        if(Uname.equalsIgnoreCase(""))
        {
            etEmpId.setError(getString(R.string.enteruser));
            BOL  = true;
        }
       /* else if(!EMP_ID.matches(emailPattern))
        {
            etEmpId.setError(getString(R.string.invalid));
            BOL  = true;
        }
*/
        if(Pass.equalsIgnoreCase(""))
        {
            etPassword.setError(getString(R.string.pass));
            BOL  = true;
        }
        else if(Pass.length()<4)
        {
            etPassword.setError(getString(R.string.invalid_pass));
            BOL  = true;
        }

        if (BOL == false)
        {
            if(isInternetConnection(getApplicationContext()))
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Login login = new Login();
                        login.setType("Login");
                        login.setUname(Uname);
                        login.setPass(Pass);

                        POST(TAG,login);

                    }
                },500);

            }
            else {
                showToast("No Internet Connection");
            }

        }
    }


    public void POST(String TAG,Login login)
    {
        startProgressDialog();

        Call<Login> call1 = apiInterface.LoginUser(login.getType(),login.getUname(),login.getPass());
        call1.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
              stopProgressDialog();
              Log.e(TAG,"asm__"+response.body().getStatus());

              if(response.body().getStatus().equalsIgnoreCase("OK"))
              {
                  showToast(""+response.body().getDetail());

                  shaved_shared_preferences.set_Login(1);
                  shaved_shared_preferences.set_Uname(Uname);
                  shaved_shared_preferences.set_SID(response.body().getSid());
                  shaved_shared_preferences.set_Branch(response.body().getBranch());
                  shaved_shared_preferences.set_PID(response.body().getPid());


                  Intent intent =  new Intent(getApplicationContext(), SetLocationActivity.class);
                  startActivity(intent);
                  overridePendingTransition(R.anim.fade_in_fragment,R.anim.fade_out_fragment);
                  finish();
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
}