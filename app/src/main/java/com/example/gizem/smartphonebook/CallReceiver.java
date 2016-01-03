package com.example.gizem.smartphonebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Gizem on 02.01.2016.
 */
public class CallReceiver extends BroadcastReceiver{
    public int startTime=0, endTime=0;

    int x;


      @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

           // Log.e("numara", incomingNumber);
            //Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
        }
          if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
          {

              startTime = (int) System.currentTimeMillis()/1000 %60;
              Toast.makeText(context,"baslama"+ String.valueOf(startTime),Toast.LENGTH_SHORT).show();


           //   x=0;

          }
          if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
          {
             endTime = (int) System.currentTimeMillis()/1000%60 - startTime;
              Toast.makeText(context,"bitis"+String.valueOf(endTime),Toast.LENGTH_SHORT).show();
                //long totalTime =(endTime)+(startTime);
              //endTime-=startTime;
             Toast.makeText(context,"total"+ String.valueOf(endTime),Toast.LENGTH_LONG).show();

          }

         /* if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
          {

              for(x=0;x<1;x++)
              {
                  endTime = System.currentTimeMillis() / 1000 % 60;
              }
              totalTime = (int)(endTime-startTime);


              Toast.makeText(context, String.valueOf(totalTime),Toast.LENGTH_LONG).show();
              totalTime=0;

          }*/
    }
}
