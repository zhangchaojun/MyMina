package com.example.app2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static String host = "192.168.0.104";
    static int port = 7080;
    private Button bt_send;
    private IoConnector connector;
    private IoSession session = null;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        new Thread() {
            @Override
            public void run() {
                super.run();

                if (connector == null) {
                    connector = new NioSocketConnector();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "重新new NioSocketConnector", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                connector.getFilterChain().addLast("codec",
                        new ProtocolCodecFilter(
                                new TextLineCodecFactory(
                                        Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
                connector.setHandler(new MyClientHandler());
                ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
                future.awaitUninterruptibly();//等待我们的连接，阻塞，就能获取到session了
                session = future.getSession();
                session.write("你好啊，我是客户端");
                session.getCloseFuture().awaitUninterruptibly();//等待服务端关闭连接
                connector.dispose();

            }
        }.start();


    }

    private void initView() {
        bt_send = (Button) findViewById(R.id.bt_send);

        bt_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                session.write("你好啊，我是客户端");


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connector.dispose();
    }
}
