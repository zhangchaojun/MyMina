package com.example.app2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();




    }

    private void initView() {
        bt_send = (Button) findViewById(R.id.bt_send);

        bt_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:

                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        IoSession session = null;
                        IoConnector connector = new NioSocketConnector();
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

                break;
        }
    }
}
