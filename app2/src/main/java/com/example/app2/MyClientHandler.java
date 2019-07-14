package com.example.app2;

import android.util.Log;
import android.widget.Toast;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by cj on 2019/7/14.
 */
public class MyClientHandler extends IoHandlerAdapter {

    private static final String TAG = "cj";

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        Log.e(TAG, "messageReceived: " );

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }
}
