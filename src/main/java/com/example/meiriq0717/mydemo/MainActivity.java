package com.example.meiriq0717.mydemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.meiriq0717.mydemo.service.ActivityService;
import com.example.meiriq0717.mydemo.service.MessengerService;
import com.example.meiriq0717.mydemo.service.MyServicce;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import java.io.File;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private SeekBar seekBar;
    private EditText editText;
    private TextView textView;
    private Button btn;
    private Object aDouble;
    LinkedList<String> linkedList;
    private boolean isBind = false;
    private ServiceConne conne;
    ActivityService.MyBinder binder;
    ActivityService mService;
    private String imageUrl = "http://pic1a.nipic.com/2008-10-16/2008101617232580_2.jpg";
    private Button btn_startService;
    private ImageView showimage;
    private MyServicce mStartService;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x11:
                    textView.setText(msg.getData().getBoolean("isFinish") + "");
                    showimage.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }
    };
    private MessengerService messengerService;
    private Messenger mMessager;
    private AsymmetricGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, ActivityService.class);
        intent.setAction("android.service.action.BINDERSERVICE");
        conne = new ServiceConne();
        bindService(intent, conne, BIND_AUTO_CREATE);
        initView();
        messengerService = new MessengerService();
        Intent intent1 = new Intent(MainActivity.this, MessengerService.class);

        bindService(intent1, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isBind = true;
                mMessager = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMessager = null;
                isBind = false;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        Log.i("BinderService", "=========binder=======");
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        editText = (EditText) findViewById(R.id.editText);
        seekBar.setOnSeekBarChangeListener(this);
        textView = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        linkedList = new LinkedList<String>();

        btn_startService = (Button) findViewById(R.id.startService);
        showimage = (ImageView) findViewById(R.id.imageView);
        btn_startService.setOnClickListener(this);
        gridView = (AsymmetricGridView) findViewById(R.id.view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            editText.setText(progress + "");
            textView.setText(progress + "");
            textView.setTextSize(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Double d = getDouble();
                textView.setText(d + "");
                break;
            case R.id.startService:
                mStartService = new MyServicce();
                new MyServicce.Data().setPath(imageUrl);
                new MyServicce.Data().setCallBack(new MyServicce.Data.ServiceCompleteCallBack() {
                    @Override
                    public void compleate(final boolean compleate, File file) {
                        if (compleate) {
                            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                            Message message = Message.obtain();
//                            message.what = 0x11;
//                            Bundle bundle = new Bundle();
//                            bundle.putBoolean("isFinish", compleate);
//                            message.obj = bitmap;
//                            message.setData(bundle);
//                            handler.sendMessage(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(compleate + "");
                                    showimage.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }
                });
                Intent intent = new Intent(MainActivity.this, MyServicce.class);
                startService(intent);
                Message message = new Message();
                message.what = 0x1;
                message.arg1 = 0x0222;
                try {
                    mMessager.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //获取Double值
    public Double getDouble() {
        Double d;
        d = Double.valueOf(editText.getText().toString());
        return d.doubleValue();
    }

    public class ServiceConne implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            binder = (ActivityService.MyBinder) service;
            mService = binder.getService();
            textView.setText(mService.getData());
            Log.i("ServiceConnect", "--------------服务链接成功-----------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
            isBind = false;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conne);
        if (isBind == true) {
            isBind = false;
        }
        super.onDestroy();
    }

    public void setData(Bitmap bitmap, boolean compleate) {
        showimage.setImageBitmap(bitmap);
        textView.setText(compleate + " ");
    }
}
