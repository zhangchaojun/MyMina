package com.example.mymina;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mymina.javatest.ServerHandler;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {


    static int port = 7080;
    static IoAcceptor acceptor = null;
    private static final String TAG = "cj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            acceptor = new NioSocketAcceptor();
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(
                            new TextLineCodecFactory(
                                    Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
            acceptor.getSessionConfig().setReadBufferSize(1024);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.setHandler(new ServerHandler());
            acceptor.bind(new InetSocketAddress(port));
            Log.e(TAG, "server已经启动，端口： " + port);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "exception: " + e.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        acceptor.dispose();//销毁
        acceptor.unbind();
    }
}
