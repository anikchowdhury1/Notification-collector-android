package concitus.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by Anik Chowdhury.
 */


public class ServiceReceiver extends BroadcastReceiver {

//    public static final String INCOMING_number = "incoming_number";
//
//    public String incoming_phn_nb = "";

//    public static final String check_notification_KEY = "first";
//    public static final String CHECK_TAG = "set_notification";

    @Override
    public void onReceive(final Context context, Intent intent) {

        // final String demo = "Hello";

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                System.out.println("incomingNumber : " + incomingNumber);

//                SharedPreferences check = context.getApplicationContext().getSharedPreferences(CHECK_TAG,0);
//                SharedPreferences.Editor editor = check.edit();
//                editor.putString(check_notification_KEY,"check_first");
//                editor.commit();

//                SharedPreferences incoming_number = context.getApplicationContext().getSharedPreferences(INCOMING_number,0);
//                final SharedPreferences.Editor editor = incoming_number.edit();


//                ..............Shared Pref save.......


                SharedPreferences pref = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
                SharedPreferences.Editor editor = pref.edit();

                if( incomingNumber != null && !incomingNumber.equals("") ){
                    editor.putString("incoming",incomingNumber);
                    editor.apply();
                }


// ...................end shared pref...............................

               //  incoming_phn_nb = demo;

                // Log.i("Incoming_phn_nb",demo);

                // HelperSharedPreferences.putSharedPreferencesString(context.getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.incoming_phn_nb,incomingNumber);

//                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("incoming_NB",incomingNumber);
//                editor.commit();


                //PreferenceManager.getDefaultSharedPreferences(context).edit().putString("incomingNB",incomingNumber).apply();
//                SharedPreferences pref = SharedPreferences.Editor;
//                pref.edit();

                // context.getApplicationContext().getSharedPreferences("Phn_NB",Context.MODE_PRIVATE|Context.MODE_MULTI_PROCESS);
//                Context shc;
//                super(context);
//                shc=context;

//                SharedPreferences pref = context.getSharedPreferences("phn_nb",Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//
//                editor.putString("incomingPHN_NB",incomingNumber);
//                editor.apply();
//
//                String inCOMphn = pref.getString("incomingPHN_NB",null);
//                Log.i("Incoming phn nb",inCOMphn);


                Intent msgrcv = new Intent("Msg");
             //   msgrcv.putExtra("package", "");
              //  msgrcv.putExtra("ticker", incomingNumber);
//                String empty_str = "";
//                if( !(incomingNumber.equals(empty_str)) && (incomingNumber) != null ){
//                    msgrcv.putExtra("title", incomingNumber);
//                }

               // msgrcv.putExtra("text", "");
                //number = incomingNumber;
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}