package com.example.reedxiong.qr_scan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.reedxiong.qr_scan.view.CameraManager;
import com.example.reedxiong.qr_scan.view.ViewfinderResultPointCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Reed.Xiong on 2016/10/13.
 */
class DecodeThread extends Thread {
    public static final String BARCODE_BITMAP = "barcode_bitmap";
    private MipcaActivityCapture activity;


    private Hashtable<DecodeHintType, Object> hints;
    private Handler handler;
    private CountDownLatch handlerInitLatch;

    public DecodeThread(MipcaActivityCapture activity, Vector<BarcodeFormat> decodeFormats,
                        String characterSet, ViewfinderResultPointCallback viewfinderResultPointCallback) {
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
        hints = new Hashtable<DecodeHintType, Object>(3);
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        if (characterSet != null) {
            hints.put(DecodeHintType.CHARACTER_SET, characterSet);
        }

        hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, viewfinderResultPointCallback);
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity, hints);
        handlerInitLatch.countDown();
        Looper.loop();
        // must execute the loop(),otherwise the message don‘t be sent to handle
    }

    class DecodeHandler extends Handler {
        private final MultiFormatReader multiFormatReader;
        private final MipcaActivityCapture activity;

        public DecodeHandler(MipcaActivityCapture activity, Hashtable<DecodeHintType, Object> hints) {
            multiFormatReader = new MultiFormatReader();
            multiFormatReader.setHints(hints);
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case R.id.decode:
                    //Log.d(TAG, "Got decode message");
                    decode((byte[]) message.obj, message.arg1, message.arg2);
                    break;
            }
        }

        private void decode(byte[] data, int width, int height) {
            long start = System.currentTimeMillis();
            Result rawResult = null;

            //modify here
            byte[] rotatedData = new byte[data.length];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++)
                    rotatedData[x * height + height - y - 1] = data[x + y * width];
            }
            int tmp = width; // Here we are swapping, that's the difference to #11
            width = height;
            height = tmp;

            PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                rawResult = multiFormatReader.decodeWithState(bitmap);
            } catch (ReaderException re) {
                // continue
            } finally {
                multiFormatReader.reset();
            }

            if (rawResult != null) {
                long end = System.currentTimeMillis();
                //Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
                Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
                message.setData(bundle);
                //Log.d(TAG, "Sending decode succeeded message...");
                message.sendToTarget();
            } else {
                Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
                message.sendToTarget();
            }
        }
    }


}

