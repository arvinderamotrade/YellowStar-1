package com.yellowstar.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static com.yellowstar.activity.DashBoard.DATE_TIME;
import static com.yellowstar.activity.DashBoard.txtLocationn;
import static com.yellowstar.activity.DashBoard.bmp;

import com.yellowstar.campus.Compass;
import com.yellowstar.campus.SOTWFormatter;
import com.yellowstar.database.Shaved_shared_preferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.yellowstar.R;

public class OpenCamera extends BaseActivity
{
    private static final String TAG = OpenCamera.class.getName()+"_API";
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    String LOCATION = "",LocationInfo="",POLE="",Degree = "",currentDateandTime="";
    TextView txtLocation,captureButton;
    Boolean bol = true;
    FrameLayout camera_preview;
    ImageView camera_output;
    Shaved_shared_preferences sharedPref;
    /** Called when the activity is first created. */


    private Compass compass;
    private ImageView arrowView;
    private TextView sotwLabel;  // SOTW is for "side of the world"

    private float currentAzimuth;
    private SOTWFormatter sotwFormatter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_demo);

   //     Utils utils = new Utils();
   //     utils.setStatusBarGradiant(OpenCamera.this);

        sharedPref = new Shaved_shared_preferences(OpenCamera.this);

        LOCATION = sharedPref.get_Location_();
        LocationInfo = sharedPref.get_LocationLatLng();
        POLE = sharedPref.get_Dev();

        Log.e("pollll",""+POLE);

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        camera_preview = (FrameLayout) findViewById(R.id.camera_preview);
        camera_output = (ImageView) findViewById(R.id.camera_output);
        camera_preview.addView(mCameraPreview);

        captureButton = (TextView) findViewById(R.id.button_capture);
        txtLocation = (TextView) findViewById(R.id.txtLocation);

        sotwFormatter = new SOTWFormatter(this);

        arrowView = findViewById(R.id.main_image_hands);
        setupCompass();

        date();

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                captureButton.setVisibility(View.GONE);
                mCamera.takePicture(null, null, mPicture);
            }
        });

    }

    private void date()
    {
        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm a", Locale.getDefault());
                currentDateandTime = sdf.format(new Date());

                txtLocation.setText(currentDateandTime+"\n"+Degree+"\n"+LocationInfo+"\n"+LOCATION+"\nPole No: "+POLE);

                DATE_TIME = currentDateandTime;
                if(bol==true)
                {
                    date();
                }
            }
        };

        handler.postDelayed(myRunnable, 1000);
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                bol = false;

                String fileUri = pictureFile.getAbsolutePath();
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                camera_output.setRotation(90);

                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                BitmapDrawable ob = new BitmapDrawable(getResources(), bmp);
                camera_preview.setBackground(ob);

                txtLocationn =  txtLocation.getText().toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },500);

            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };

    private static File getOutputMediaFile()
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs())
            {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }

    private void setupCompass() {
        compass = new Compass(this);
        Compass.CompassListener cl = getCompassListener();
        compass.setListener(cl);
    }

    private void adjustArrow(float azimuth) {
        Log.d(TAG, "will set rotation from " + currentAzimuth + " to "
                + azimuth);

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        arrowView.startAnimation(an);
    }

    private void adjustSotwLabel(float azimuth) {
      Degree = sotwFormatter.format(azimuth);
    }

    private Compass.CompassListener getCompassListener() {
        return new Compass.CompassListener() {
            @Override
            public void onNewAzimuth(final float azimuth) {
                // UI updates only in UI thread
                // https://stackoverflow.com/q/11140285/444966
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adjustArrow(azimuth);
                        adjustSotwLabel(azimuth);
                    }
                });
            }
        };
    }



}