package com.example.icursoft_codertung.democanvas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.dalong.marqueeview.MarqueeView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MarqueeView marqueeView , marqueeVieww;
    private Handler mHandler;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        marqueeVieww.setText("23456");//设置文本
                        break;
                    case 2:
                        tvTime.setText(msg.getData().getString("time"));
                        break;
                }
                return false;
            }
        });


        String notice = "心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！心中有阳光，脚底有力量！";

        marqueeView = (MarqueeView)findViewById(R.id.tv_marquee);
        marqueeView.setFocusable(true);
        marqueeView.requestFocus();
        marqueeView.setText(notice);//设置文本

        marqueeVieww = (MarqueeView)findViewById(R.id.tv_marquee1);
        marqueeVieww.setFocusable(true);
        marqueeVieww.requestFocus();
        marqueeVieww.setText(notice);//设置文本

        mHandler.sendEmptyMessageDelayed(1 , 3000);

        /*mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        } , 5000);*/

        MyMarqueeView marqueeView1 = (MyMarqueeView) findViewById(R.id.marqueeView);

        /*List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是孙福生。");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：sfsheng0322");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 个人博客：sunfusheng.com");
        info.add("6. 微信公众号：孙福生");
        marqueeView1.startWithList(info);

// 在代码里设置自己的动画
        marqueeView1.startWithList(info, R.anim.anim_right_in, R.anim.anim_left_out);*/

        marqueeView1.startWithText(notice);

// 在代码里设置自己的动画
        marqueeView1.startWithText(notice, R.anim.anim_right_in, R.anim.anim_left_out);


        final String webUrl2 = "http://www.baidu.com";//百度
        final String webUrl3 = "http://www.taobao.com";//淘宝
        final String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
        final String webUrl5 = "http://www.360.cn";//360
        final String webUrl6 = "http://www.beijing-time.org";//beijing-time
        tvTime = (TextView) findViewById(R.id.tv_time);

    }

    private HomeWatcherReceiver mHomeKeyReceiver = null;

    private void registerHomeKeyReceiver(Context context) {
        Log.i("HomeReceiver", "registerHomeKeyReceiver");
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private void unregisterHomeKeyReceiver(Context context) {
        Log.i("HomeReceiver", "unregisterHomeKeyReceiver");
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }

    public class HomeWatcherReceiver extends BroadcastReceiver {
        private static final String LOG_TAG = "HomeReceiver";
        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
        private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(LOG_TAG, "onReceive: action: " + action);
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                // android.intent.action.CLOSE_SYSTEM_DIALOGS
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                Log.i(LOG_TAG, "reason: " + reason);

                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    // 短按Home键
                    Log.i(LOG_TAG, "homekey");

                }
                else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                    // 长按Home键 或者 activity切换键
                    Log.i(LOG_TAG, "long press home key or activity switch");

                }
                else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                    // 锁屏
                    Log.i(LOG_TAG, "lock");
                }
                else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                    // samsung 长按Home键
                    Log.i(LOG_TAG, "assist");
                }

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerHomeKeyReceiver(this);
    }

    @Override
    protected void onPause() {

        unregisterHomeKeyReceiver(this);
        super.onPause();
    }

    /**
     * 获取指定网站的日期时间
     *
     * @param webUrl
     * @return
     * @author SHANHY
     * @date   2015年11月27日
     */
    private static String getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_DOWN:
                marqueeView.setText("123");
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                marqueeView.setText("3333333");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                /*tvTime.setText(getWebsiteDatetime(webUrl2) + " [百度]" + "/n" + getWebsiteDatetime(webUrl3) + " [淘宝]" +
                        getWebsiteDatetime(webUrl4) + " [中国科学院国家授时中心]" + "/n" + getWebsiteDatetime(webUrl5) + " [360安全卫士]" +
                        getWebsiteDatetime(webUrl6) + " [beijing-time]");*/
                        Message message = new Message();
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("time" , getWebsiteDatetime("http://www.baidu.com"));
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }).start();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeView.startScroll();
        marqueeVieww.startScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeView.stopScroll();
        marqueeVieww.stopScroll();
    }
}
