package com.flatshare.presentation.ui.activities.matching;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.flatshare.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by Arber on 07/01/2017.
 */
public class QRCodeReaderActivity extends Activity {

    private static final int PERMISSIONS_REQUEST_CAMERA = 100;

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;


    private SurfaceView cameraView;
    private TextView barcodeInfo;
    private BarcodeDetector barcodeDetector;

    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);

        setupReader();
        bindView();
        checkForPermission();
    }

    private void setupReader() {

        setupBarcodeDetector();
        setupCameraSource();
        setupSurfaceView();

    }

    private void setupBarcodeDetector() {

        if (barcodeDetector == null) {
            barcodeDetector =
                    new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

            if (barcodeDetector.isOperational()) {
                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {
                        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                        if (barcodes.size() != 0) {
                            barcodeInfo.post(new Runnable() {
                                @Override
                                public void run() {

                                    System.out.println("CODE = " + barcodes.valueAt(0));

                                    barcodeInfo.setText(    // Update the TextView
                                            barcodes.valueAt(0).displayValue
                                    );
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    private void setupCameraSource() {

        if (cameraSource == null) {
            cameraSource = new CameraSource
                    .Builder(this, barcodeDetector)
                    .setAutoFocusEnabled(true)
                    .setRequestedPreviewSize(WIDTH, HEIGHT)
                    .build();
        }

    }

    private void setupSurfaceView() {
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                stopCamera();
            }
        });
    }

    private void checkForPermission() {
        if (ActivityCompat.checkSelfPermission(QRCodeReaderActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(QRCodeReaderActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(QRCodeReaderActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_CAMERA);
            }

        }
    }

    private void openCamera() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            cameraSource.start(cameraView.getHolder());
        } catch (IOException ie) {
            Log.e("CAMERA SOURCE", ie.getMessage());
        }
    }

    private void stopCamera() {
        cameraSource.stop();
    }


    private void bindView() {

        barcodeInfo = (TextView) findViewById(R.id.code_info);

    }

}