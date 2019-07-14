package com.example.mymina.javatest;

import android.util.Log;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by cj on 2019/7/14.
 */
public class ServerHandler extends IoHandlerAdapter {

    private static final String TAG = "cj";


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Log.e(TAG, "sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log.e(TAG, "sessionOpened");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log.e(TAG, "sessionClosed");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        Log.e(TAG, "sessionIdle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Log.e(TAG, "exceptionCaught");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Log.e(TAG, "messageReceived:"+message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log.e(TAG, "messageSent");
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        Log.e(TAG, "inputClosed");
    }
}
