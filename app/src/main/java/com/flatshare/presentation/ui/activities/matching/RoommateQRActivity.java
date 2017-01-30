package com.flatshare.presentation.ui.activities.matching;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.flatshare.R;
import com.flatshare.presentation.presenters.profile.RoommateQRPresenter;
import com.flatshare.presentation.presenters.profile.impl.RoommateQRPresenterImpl;
import com.flatshare.presentation.ui.AbstractActivity;
import com.flatshare.presentation.ui.activities.profile.RoommateWaitingActivity;
import com.flatshare.threading.MainThreadImpl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import static com.flatshare.presentation.ui.activities.profile.ApartmentProfileActivity.APARTMENT_ID;
import static com.flatshare.presentation.ui.activities.profile.ApartmentProfileActivity.ROOMMATE_ID;

/**
 * Created by Arber on 07/01/2017.
 */
public class RoommateQRActivity extends AbstractActivity implements RoommateQRPresenter.View {

    private static final String TAG = "RoommateQRActivity";
    private ImageView qrImageView;
    private final static int QR_CODE_DIM = 800;
    private Bitmap bitmap;
    private String qrCodeString;

    private RoommateQRPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        mPresenter = new RoommateQRPresenterImpl(
                MainThreadImpl.getInstance(),
                this
        );

        Bundle b = getIntent().getExtras();
        String roommateId;
        if (b != null) {
            roommateId = b.getString(ROOMMATE_ID);
            qrCodeString = QRCodeReaderActivity.QR_IDENTIFIER + ":" + roommateId;
            encodeStringToQR(qrCodeString);
            mPresenter.listenToDB(roommateId);
        }

    }

    private void bindView() {

        qrImageView = (ImageView) findViewById(R.id.qr_image_view);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_roommate_qr;
    }

    public void encodeStringToQR(String value) {

        try {
            bitmap = TextToImageEncode(value);

            qrImageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }


    private Bitmap TextToImageEncode(String value) throws WriterException {

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
//            hints.put(EncodeHintType.CHARACTER_SET, value);


        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QR_CODE_DIM, QR_CODE_DIM, hints
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);

        bitmap.setPixels(pixels, 0, bitMatrixWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onQRCodeRead() {

        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
        changeToWaitingActivity();

    }

    @Override
    public void changeToWaitingActivity() {
        Intent intent = new Intent(this, RoommateWaitingActivity.class);
        startActivity(intent);
        Log.d(TAG, "success! changed to RoommateWaitingActivity!");
    }

}
