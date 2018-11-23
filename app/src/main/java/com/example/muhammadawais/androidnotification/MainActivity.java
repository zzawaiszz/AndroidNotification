package com.example.muhammadawais.androidnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {



    public  static final String channel_id="sp_coding";
    public  static final String channel_name="sp_name";
    public  static final String channel_descption="sp_description";
    EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.textView);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channel_id,channel_name,NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(channel_descption);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
           if(task.isComplete())
           {
               String str= task.getResult().getToken();
             //  tv.setText(str);

           }else
               Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        LatestFirebaseMessagingService m=new LatestFirebaseMessagingService();
        m.onNewToken("awi");
      findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            displayNotification();
    }
});
    }


    private void displayNotification(){

        NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this,channel_id)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("this is Title Awais")
                .setContentText("this is ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mManager=NotificationManagerCompat.from(this);
        mManager.notify(1,mBuilder.build());
    }
    private class LatestFirebaseMessagingService extends FirebaseMessagingService {

        @Override
        public void onNewToken(String mToken) {
            super.onNewToken(mToken);
            tv.setText(mToken.toString());
            Log.d("TOKEN",mToken);
        }

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
        }}
}
