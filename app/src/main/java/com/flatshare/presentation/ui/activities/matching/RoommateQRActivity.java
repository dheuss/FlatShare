package com.flatshare.presentation.ui.activities.matching;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.flatshare.R;
import com.flatshare.presentation.ui.AbstractActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Arber on 07/01/2017.
 */
public class RoommateQRActivity extends AbstractActivity { //implements RoommateProfilePresenter.View {

    private ImageView qrImageView;
    private final static int QR_CODE_DIM = 800;
    private Bitmap bitmap;
    private String roommateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindView();

        Bundle b = getIntent().getExtras();
        if(b != null){
            roommateId = b.getString("id");
            encodeStringToQR(roommateId);
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

}
