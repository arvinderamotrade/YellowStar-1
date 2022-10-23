package com.yellowstar.activity;

import static com.yellowstar.activity.DashBoard.BITMAP1;
import static com.yellowstar.activity.DashBoard.DATA1;
import static com.yellowstar.activity.DashBoard.BITMAP2;
import static com.yellowstar.activity.DashBoard.DATA2;
import static com.yellowstar.activity.DashBoard.BITMAP3;
import static com.yellowstar.activity.DashBoard.DATA3;

import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;

import com.yellowstar.R;

public class ScannedBarcodeActivity extends BaseActivity
{
    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private static final int CAMERA_REQUEST = 111;
    TextView btnAction;
    String intentData = "";
    String val = "",INFO;
    boolean isEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);

        Intent i = getIntent();
        val = i.getStringExtra("val");

        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnAction.getText().toString().equalsIgnoreCase("DONE"))
                {
                    Bitmap bitmap = screenShot(surfaceView.getRootView());
                    Log.e("bitmappp_data_", "----" + txtBarcodeValue.getText().toString());
                    Log.e("bitmappp", "----" + bitmap);
                    if(txtBarcodeValue.getText().toString().equalsIgnoreCase("No Barcode Detected"))
                    {
                        showToast("No Barcode Detected");
                    }
                    else {
                        if (val.equalsIgnoreCase("1")) {
                            DATA1 = txtBarcodeValue.getText().toString();
                            BITMAP1 = bitmap;
                        } else if (val.equalsIgnoreCase("2")) {
                            DATA2 = txtBarcodeValue.getText().toString();
                            BITMAP2 = bitmap;
                        } else if (val.equalsIgnoreCase("3")) {
                            DATA3 = txtBarcodeValue.getText().toString();
                            BITMAP3 = bitmap;
                        }
                        onBackPressed();
                    }
                }

            }
        });
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
                btnAction.setBackground(getResources().getDrawable(R.drawable.button_green));
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                           /*     btnAction.setText("SCANNING QR");
                                btnAction.setBackground(getResources().getDrawable(R.drawable.button_grey));*/
                            } else {
                                isEmail = false;
                                btnAction.setText("DONE");
                                btnAction.setBackground(getResources().getDrawable(R.drawable.button_black));
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);

                                cameraSource.stop();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
        }
    }
}