package com.flatshare.presentation.ui.activities.matching;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.domain.datatypes.pair.Pair;
import com.flatshare.domain.datatypes.pair.ParcelablePair;
import com.flatshare.presentation.presenters.profile.QRScannerPresenter;
import com.flatshare.presentation.presenters.profile.impl.QRScannerPresenterImpl;
import com.flatshare.presentation.ui.activities.profile.ApartmentProfileActivity;
import com.flatshare.threading.MainThreadImpl;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import dmax.dialog.SpotsDialog;

/**
 * Created by Arber on 07/01/2017.
 */
public class QRCodeReaderActivity extends Activity implements QRScannerPresenter.View {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    public static final String QR_IDENTIFIER = "d91dbdf280373c5b";
    private static final String TAG = "QRCodeReaderActivity";

    QRScannerPresenter mPresenter;

    private AlertDialog progressDialog;

    private SurfaceView cameraView;
    private BarcodeDetector barcodeDetector;

    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);

        progressDialog = new SpotsDialog(this, R.style.Custom);

        mPresenter = new QRScannerPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        setupReader();
//        checkForPermission();
    }

    private void setupReader() {

        setupBarcodeDetector();
        setupCameraSource();
        setupSurfaceView();

    }

    private void setupBarcodeDetector() {

        final AtomicBoolean flag = new AtomicBoolean(false);

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

                        if (flag.get()) {
                            return;
                        }

                        if (barcodes.size() != 0) {
                            String[] values = barcodes.valueAt(0).displayValue.split(":");
                            if (values[0].equals(QR_IDENTIFIER)) {
                                String roommateId = values[1];
                                flag.set(true);

                                Log.d(TAG, "receiveDetections: RoommateID! " + roommateId);
                                mPresenter.addRoomate(roommateId);

                            }
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

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void onDestroy() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void displayReadCode(Pair<String, String> idNicknamePair) {
        Intent intent = new Intent();
        ParcelablePair parcelablePair = new ParcelablePair(idNicknamePair);
        intent.putExtra(ApartmentProfileActivity.ROOMMATE_ID_NICKNAME_PAIR, parcelablePair);

        setResult(Activity.RESULT_OK, intent);
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        v.vibrate(100);
        finish();
    }
}