package concitus.notifications;

import android.app.Dialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

//import org.w3c.dom.Text;

//import static concitus.notifications.NotificationService.getCustomAppContext;

// import android.content.SharedPreferences;


/**
 * Created by Anik Chowdhury.
 */



/* .....Class for detecting statusbar notification,
........listen dialog popup of related package ( Trucaller, Hiya)
........Take Screenshot, OCR the Screenshot......................
........Sends out the data to firebase database..................
.. */


public class NotificationService extends NotificationListenerService {


    Context context, mContext;
    String check_ts = "";

// ......for check...........

    String spam_check2 = "";
    String spam_check3 = "";
    String spam_check4 = "";
    String spam_check5 = "";
    String spam_check6 = "";
    String spam_check7 = "";

// ......for check...........


    //String count = "1";

    int notification_color;
    int notifi_check;
    int flag_test = 0;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        mContext = getApplicationContext();



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("FEEDBACK");
    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {

        cancelAllNotifications();

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
        String INCOMING_PHONE_NUMBER_test = "";
        String abjab = "";
        if(!(pref.getString("incoming","")).equals(abjab)){
            INCOMING_PHONE_NUMBER_test = pref.getString("incoming","");
        }

        String desi_code = "+1";
        Log.i("Desicode:",INCOMING_PHONE_NUMBER_test);

        String package_name1 = "com.truecaller";
        String package_name2 = "com.webascender.callerid";

        cancelAllNotifications();
        String check_notification_KEY = "first";
        String logtag = "NotificationLisener: ";

        String pack = sbn.getPackageName();
        Log.i("Tested Package",pack);
        String ticker ="";
        if(sbn.getNotification().tickerText !=null) {
            ticker = sbn.getNotification().tickerText.toString();
            // ticker = sbn.getNotification().number;
        }
        Bundle extras = sbn.getNotification().extras;

        String STATE = "";

        STATE = extras.getString("android.title");

        String INCOMING_SPAM_PHONE_NUMBER = "Not Spam";

        String Additional_DATA = "";

        String tag_test = sbn.getGroupKey();
        String tag_test1 = sbn.getKey();
        try {
            mContext = createPackageContext(pack,CONTEXT_INCLUDE_CODE|CONTEXT_IGNORE_SECURITY);
        }
        catch (Exception e){
            Log.i("exeption occur","in context");
            mContext = context;
            Log.i("Specific Exeption",String.valueOf(e));
        }

        Long tsLong = System.currentTimeMillis() /1000;
        String time_stamp = tsLong.toString();

        String date = (android.text.format.DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
        Bitmap id = sbn.getNotification().largeIcon;

        if((INCOMING_PHONE_NUMBER_test.contains(desi_code))){

            try {
                Process shh = Runtime.getRuntime().exec("su", null,null);
                DataOutputStream oss = new DataOutputStream(shh.getOutputStream());

                oss.writeBytes("service call phone 5\n");
                oss.flush();

                oss.writeBytes("exit\n");
                oss.flush();
                oss.close();
                Log.i("SUPER","USWER");
                shh.waitFor();


            } catch (IOException e) {
                // TODO handle I/O going wrong
                // this probably means that the device isn't rooted
            } catch (InterruptedException e) {
                // don't swallow interruptions
                Thread.currentThread().interrupt();
            }

            pack = "com.truecaller";
            Log.i("BIDESHI ANS PACKAGE: ",pack);


        }
        else {

            pack = sbn.getPackageName();
            Log.i("DESICODE ANS PACKAGE: ",pack);
            Log.i("Desicode another:",INCOMING_PHONE_NUMBER_test);

        }


        pack = sbn.getPackageName();
        Log.i("BIDESHI ANS PACKAGE: ",pack);


        //.........Dialog listener......................(start block)

        Dialog dialog = new Dialog(mContext);

        if( (pack.equals(package_name1) || pack.equals(package_name2)) && flag_test == 0 ){

            if (!dialog.isShowing()){



                Log.d(logtag,"DialogChecker - Dialog_Title: "+dialog.getContext().getPackageName());

                Log.d(logtag,"DialogChecker - Dialog_DB: "+dialog.getContext().databaseList());

               // Log.d(logtag,"DialogChecker - Dialog_color_failed: "+dialog.getContext().getColor((sbn.getId())));

                Log.d(logtag,"DialogChecker - Dialog_color: "+dialog.getContext().getPackageResourcePath());

                Log.d(logtag,"DialogChecker - Dialog_color_failed: "+dialog.getCurrentFocus());

                Log.d(logtag,"DialogChecker - Dialog_draw: "+dialog.getActionBar());

                Log.d(logtag,"DialogChecker - Dialog_path: "+dialog.getContext().getDataDir());

                Log.d(logtag,"DialogChecker - Dialog_resource_path: "+dialog.getContext().getPackageResourcePath());

                Log.d(logtag,"DialogChecker - Dialog_show: "+dialog.isShowing());

                Log.i("Check in","Dialog");


                // ............Dialog Listener.....(End Block).......












// ...................Screenshot take.................................................................................

                try {


                    Process sh = Runtime.getRuntime().exec("su", null,null);

                    String path = "/storage/emulated/0/img.png";

                    OutputStream os = sh.getOutputStream();
                    //InputStream as = sh.getInputStream();



                    os.write(("/system/bin/screencap -p " + "/storage/emulated/0/img.png").getBytes("ASCII"));

                    os.flush();

                    Log.i("screenshot process",pack);

                    os.close();

                    sh.waitFor();

// ..................Endblock of screenshot take ......................................................................













// ..................Read File (Stored Screenshot from storage)........................................................

                    File imgFile = new  File(Environment.getExternalStorageDirectory() + "/img.png");
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        Log.i("Bitmap","found");

// ..................End of read file..................................................................................










// ..................OCR................................................................................................


                        String match_text = "spam reports";
                        String find_text = "VIEW";

                        final TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                        if (!txtRecognizer.isOperational()) {
                            // txtView.setText(R.string.error_prompt);
                            Log.i("Text","Vision");
                        }
                        else {

                            Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                            SparseArray items = txtRecognizer.detect(frame);
                            StringBuilder strBuilder = new StringBuilder();
                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = (TextBlock) items.valueAt(i);
                                strBuilder.append(item.getValue());
                                strBuilder.append("/");

//                            if (find_text.equals("match found")){
//                                break;
//                            }

                                for (Text line : item.getComponents()) {
                                    //extract scanned text lines here
                                    Log.v("lines", line.getValue());

                                    String line_text = line.getValue();
                                    if (line_text.toLowerCase().contains(match_text.toLowerCase())) {

                                        // find_text = "match found";
                                        INCOMING_SPAM_PHONE_NUMBER = "spam";
                                        Log.i("TagTest","Found");
                                        break;

                                    }

                                    if (line_text.toLowerCase().contains(find_text.toLowerCase())) {

                                        flag_test = flag_test + 1;
                                        Log.i("flag",String.valueOf(flag_test));
                                        Log.i("Incoming Phone Number in OCR:",INCOMING_PHONE_NUMBER_test);

                                    }
                                }
                            }
                            Additional_DATA = (strBuilder.toString().substring(0, strBuilder.toString().length() - 1));
                        }


// ...............Endblock of OCR...............................................................

                }
                catch (Exception e){
                    Log.i("exeption occur","in context");
                    mContext = context;
                    Log.i("Specific Exeption",String.valueOf(e));
                }




            }
        }


// .............Test Purpose checking(Dialog)................................................

        if((pack.equals(package_name1) || pack.equals(package_name2)))
        {
            Notification notification = sbn.getNotification();
            notification_color = notification.color;
            notifi_check = notification.flags;


            DialogPreference check_dialog = new DialogPreference(this) {
                @Override
                public CharSequence getDialogTitle() {

                    Log.i("Hi hey","inside");
                    return super.getDialogTitle();
                }
            };


            DialogPreference check_dialog1 = new DialogPreference(this) {
                @Override
                public Drawable getDialogIcon() {
                    return super.getDialogIcon();
                }
            };

            DialogPreference check_dialog2 = new DialogPreference(this) {
                @Override
                public int getDialogLayoutResource() {
                    return super.getDialogLayoutResource();
                }
            };

            DialogPreference check_dialog3 = new DialogPreference(this) {
                @Override
                public Dialog getDialog() {
                    return super.getDialog();
                }
            };

            Log.d(logtag,"CharSequenceDialog - Dialog_Title: "+check_dialog.getDialogTitle());

            Log.d(logtag,"CharSequenceDialog - Dialog_Icon: "+check_dialog1.getDialogIcon());

            Log.d(logtag,"CharSequenceDialog - Dialog_Resource: "+check_dialog2.getDialogLayoutResource());

            Log.d(logtag,"CharSequenceDialog - Dialog_Dialog: "+check_dialog3.getDialog());

        }

//..............End of Test purpose checking(Dialog)........................................................................








//..........Get info of incoming number using sharedPreferences.............................................................

        SharedPreferences prefn = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
        String user_Phone_Number = prefn.getString("user_phn_nb","");

 //       String INCOMING_PHONE_NUMBER = pref.getString("incoming","");
// .........................................................................................................................

        cancelAllNotifications();



// check value for debug.......................................
        Log.i("Package",pack);
        Log.i("Ticker",ticker);
        Log.i("Title",STATE);
        Log.i("Text",INCOMING_PHONE_NUMBER_test);
        Log.i("Time_Stamp",date);
        Log.i("Unix_Time_Stamp",time_stamp);
        Log.i("check Ts:",check_ts);
        Log.i("user_phn_nb",user_Phone_Number);
        Log.i("SPAM Checker:",INCOMING_SPAM_PHONE_NUMBER);
        Log.i("color Checker1:",String.valueOf(notification_color));
        // Log.i("Notification Catagory:",notifi_check);
        Log.d(logtag,"Notification Catagory:"+notifi_check);
        Log.i("tag test::",tag_test);
        Log.i("tag test1:",tag_test1);
        // Log.i("tag test2:",tag_test2);




// ....end of check value for debug............................




 //.........Send data to Firebase.........................................................................................

        if ((check_ts!= null) && (check_ts != time_stamp.intern())) {

            check_ts = time_stamp;

            // cancelAllNotifications();

            SharedPreferences check = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
            SharedPreferences.Editor editor = check.edit();
            String check_tag = check.getString(check_notification_KEY,"");
            Log.i("test_notification_tag",check_tag);



            if( (pack.equals(package_name1) || pack.equals(package_name2)) && flag_test == 1 && !(Additional_DATA.equals("")) ) {

                Model friendlyMessage = new Model(STATE, INCOMING_PHONE_NUMBER_test, date, time_stamp, user_Phone_Number,INCOMING_SPAM_PHONE_NUMBER,Additional_DATA);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);

            }

        }

//.............end of send data to Firebase.........................................................


        //.................Intent activity to send data to Mainactivity..........................

        Intent msgrcv = new Intent("Msg");
        msgrcv.putExtra("package", pack);
        msgrcv.putExtra("ticker", ticker);
        //msgrcv.putExtra("title", STATE);
        //msgrcv.putExtra("text", INCOMING_PHONE_NUMBER);
        if(id != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            msgrcv.putExtra("icon",byteArray);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);

        //.................End of Intent activity.......................................

         //}
    }


    //........This function activates when the statusbar notificatons are removed...........

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

        flag_test = 0;
        Log.i("flag reset",String.valueOf(flag_test));


        String check_notification_KEY = "first";
        // String check_tag = "";

        SharedPreferences check = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
        SharedPreferences.Editor editor = check.edit();

        editor.remove(check_notification_KEY);
        editor.apply();
        String check_tag1 = check.getString(check_notification_KEY,"");
        Log.i("Remove firebase_test_notification_tag",check_tag1);

        editor.putString(check_notification_KEY,"dumy_value");
        editor.apply();
        String check_tag = check.getString(check_notification_KEY,"");
        Log.i("Insert firebase_test_notification_tag",check_tag);

    }
//,,,,,,,,,,,,,,,,End of onNotificationRemoved.................................................






}
