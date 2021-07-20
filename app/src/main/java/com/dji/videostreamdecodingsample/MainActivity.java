package com.dji.videostreamdecodingsample;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.decades.djistreamerlib.StreamerConfig;
import com.decades.djistreamerlib.StreamerEvents;
import com.decades.djistreamerlib.StreamerLib;


public class MainActivity extends Activity implements  StreamerEvents {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MSG_WHAT_SHOW_TOAST = 0;
    private boolean started = false;
    private GLSurfaceView mSurfaceView;
    private Button mStartStopButton;

    @Override
    public void onError(int code, String message) {
        showToast("Error "+ code + " " +message);
    }

    @Override
    public void onStatus(int code, String message) {
        showToast("Status "+ code + " " +message);
    }


    public Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_SHOW_TOAST:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };

    private StreamerLib mStreamerLib;

    @Override
    protected void onResume() {
        mSurfaceView.onResume();

        mStartStopButton.setText(started ? "Stop": "Start");
        super.onResume();
    }

    @Override
    protected void onPause() {
        mSurfaceView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSurfaceView = (GLSurfaceView)findViewById(R.id.surface);
        mStartStopButton = (Button)findViewById(R.id.start_stop_broadcast);

        // You need to obtain licenseKey and userId beforehand
        // Please get yourself a time limited test credential at
        // https://webrtcserver.ddns.net/licensed

        StreamerConfig config = new StreamerConfig.Builder()
                .setUserId("your-djistreamerlib-userid-here")           // MANDATORY
                .setLicenseKey("your-djistreamerlib-license-key-here")  // MANDATORY
                .setSurfaceView(mSurfaceView)                           // OPTIONAL
                .setYuvFormat(StreamerConfig.FORMAT_I420)               // OPTIONAL, FORMAT_I420 is default
                .setVideoWidth(1280)                                    // OPTIONAL, 800 is default, must not be bigger than YuvWidth
                .setVideoHeight(720)                                    // OPTIONAL, 600 is default, must not be bigger than YuvHeight
                .build();
        try {
            mStreamerLib = new StreamerLib(MainActivity.this, config);
            mSurfaceView.setEGLContextClientVersion(2);
            mSurfaceView.setRenderer(mStreamerLib);
            mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        } catch (Exception e) {
            showToast("Exception "+e.toString());
            e.printStackTrace();
        }

    }

    private void showToast(String s) {
        mainHandler.sendMessage(mainHandler.obtainMessage(MSG_WHAT_SHOW_TOAST, s));
    }



    public void onClick(View v) {

        if (v.getId() == R.id.start_stop_broadcast) {
            if (mStreamerLib != null) {
                if (started)
                    mStreamerLib.stop();
                else
                    mStreamerLib.start();
                started = !started;
                mStartStopButton.setText(started ? "Stop": "Start");
            }
        }
    }

}
