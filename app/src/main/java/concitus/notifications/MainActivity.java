package concitus.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by Anik Chowdhury.
 */


public class MainActivity extends Activity {
    ListView list;
    CustomListAdapter adapter;
    ArrayList<Model> modelList;
    String user_phone_number = "";
    public static final String MyPREFERENCES = "phone_nb_saver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
        wl.release();


// .............Starting Block of setting up the runtime permissions ..........................................................................................
        String TAG = "Permission Check";

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
            //return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.READ_EXTERNAL_STORAGE} , 1);
            Log.v(TAG,"NOW Permission is granted");

        }

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
            //return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
            Log.v(TAG,"NOW Permission is granted");
        }

        if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
            //return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.READ_PHONE_STATE} , 1);
            Log.v(TAG,"NOW Permission is granted");
        }


        if (checkSelfPermission(android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
            //return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.WAKE_LOCK} , 1);
            Log.v(TAG,"NOW Permission is granted");
        }

//....................End Block of Setting up the runtime permission..............................................................................................


        /*
            SharedPreference for storing user phone number and incoming phone number
         */

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MyPREFERENCES,0);
        final SharedPreferences.Editor editor = pref.edit();

        user_phone_number = pref.getString("user_phn_nb","");
        Log.i("user_phone_nb",user_phone_number);

        /*
            Following function is used to check if user phone is empty or not. If empty, then a dialog is popped up
            for the input of user phone number.
         */

        if(user_phone_number.isEmpty()){


            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add user phone number")
                    .setMessage("Include country code also")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            user_phone_number = String.valueOf(taskEditText.getText());
                            // Log.d(TAG, "Task to add: " + task);
                            Log.i("user_phone_nb",user_phone_number);
                            editor.putString("user_phn_nb",user_phone_number);
                            editor.commit();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

        }

        /*
        Arraylist for showing response state and incoming phone number in app's UI.
         */

        modelList = new ArrayList<Model>();
        adapter = new CustomListAdapter(getApplicationContext(), modelList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }


    /*
        Function to turn on notification access.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*
    BoardcastReceiver to receive receiver's response and notification's feedback
 */
    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
           // String pack = intent.getStringExtra("package");
           // String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String incomingNB = intent.getStringExtra("title");
            String check_notification_KEY = "first";

            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(MainActivity.MyPREFERENCES,0);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(check_notification_KEY,"check_first");

            editor.apply();

            try {
                byte[] byteArray =intent.getByteArrayExtra("icon");
                Bitmap bmp = null;
                if(byteArray !=null) {
                    bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                Model model = new Model();
                model.setName(incomingNB +" " +text);
                model.setImage(bmp);

                if(modelList !=null) {
                    modelList.add(model);
                    adapter.notifyDataSetChanged();
                }else {
                    modelList = new ArrayList<Model>();
                    modelList.add(model);
                    adapter = new CustomListAdapter(getApplicationContext(), modelList);
                    list=(ListView)findViewById(R.id.list);
                    list.setAdapter(adapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
