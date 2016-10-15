package com.example.sagar.mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sagar.mobileproject.constant.Constant;
import com.example.sagar.mobileproject.service.LocationService;

import java.util.List;




public class ActivityDialog extends Activity {
    SharedPreferences sharedPreferences;
    Spinner spinner;
    List<String> categories;
    ArrayAdapter<String> dataAdapter;
    String selected_value;
    SharedPreferences.Editor editor;
    private RadioGroup univ;
    private RadioButton cal;
    CheckBox sjsu,sjsu2,sdsu,sdsu2,lb,lb2,csula,csula2,santa,santa2,sfu,sfu2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_dialog);
        sjsu = (CheckBox) findViewById(R.id.sjsu);
        sdsu = (CheckBox) findViewById(R.id.sdsu);
        sjsu2 = (CheckBox) findViewById(R.id.sjsu2);
        sdsu2 = (CheckBox) findViewById(R.id.sdsu2);
        lb = (CheckBox) findViewById(R.id.lb);
        lb2 = (CheckBox) findViewById(R.id.lb2);
        csula = (CheckBox) findViewById(R.id.csula);
        csula2 = (CheckBox) findViewById(R.id.csula2);
        sfu = (CheckBox) findViewById(R.id.sfu);
        sfu2 = (CheckBox) findViewById(R.id.sfu2);
        santa = (CheckBox) findViewById(R.id.santa);
        santa2 = (CheckBox) findViewById(R.id.santa2);
        sharedPreferences  = getSharedPreferences("PASSCODE_ACTIVITY", MODE_PRIVATE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }

    public void returnToActivityA(View view) {
            String result ="";
            if(sjsu.isChecked())
            {  result =result+","+sjsu.getText().toString();}
            if(sjsu2.isChecked())
            {  result =result+","+sjsu2.getText().toString();}
            if(sdsu.isChecked())
            {  result =result+","+sdsu.getText().toString();}
        if(sdsu2.isChecked())
        {  result =result+","+sdsu2.getText().toString();}
            if(santa.isChecked())
            {  result =result+","+santa.getText().toString();}
            if(santa2.isChecked())
            {  result =result+","+santa2.getText().toString();}
            if(sfu.isChecked())
            {  result =result+","+sfu.getText().toString();}
            if(sfu2.isChecked())
            {  result =result+","+sfu2.getText().toString();}
            if(csula.isChecked())
            {  result =result+","+csula.getText().toString();}
            if(csula2.isChecked())
            {  result =result+","+csula2.getText().toString();}
            if(lb.isChecked())
            {  result =result+","+lb.getText().toString();}
            if(lb2.isChecked())
            {  result =result+","+lb2.getText().toString();}
            if(!result.equals(""))
            {
                result = result.substring(1);
             //   Toast.makeText(getApplicationContext(), "Password is " + result, Toast.LENGTH_LONG).show();
                String stored_value=sharedPreferences.getString("password_no","");
                if (stored_value.equalsIgnoreCase(result)){
                    editor.putString("Flag","true");
                    Toast.makeText(getApplicationContext(), "Password is correct", Toast.LENGTH_LONG).show();
                    editor.apply();
                    stopService(new Intent(ActivityDialog.this, PowerButtonService.class));

                            }else  {
                    Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                    Intent locService = new Intent(getApplicationContext(), LocationService.class);
                    locService.putExtra(Constant.ROOTCAUSE,"Wrong Password Notification");
                    startService(locService);}
            }else{
                Toast.makeText(getApplicationContext(), "Please select atleast one image", Toast.LENGTH_LONG).show();
            }
        finish();
                //super.onBackPressed();
      /*  int selectedId = univ.getCheckedRadioButtonId();
        cal = (RadioButton) findViewById(selectedId);
        Log.d("Display", "selected2 is " + cal.getText());
        String stored_value=sharedPreferences.getString("password_no","");
        Log.d("Display","Stored value is "+stored_value);
        if(stored_value.equalsIgnoreCase(""+cal.getText())){
            editor.putString("Flag","true");
            Log.d("Display", "Password is true");
            editor.apply();
            stopService(new Intent(ActivityDialog.this, PowerButtonService.class));
        }else{
            Log.d("Display","Password is false");
            Intent locService = new Intent(getApplicationContext(), LocationService.class);
            locService.putExtra(Constant.ROOTCAUSE,"Wrong Password Notification");
            startService(locService);
        }
        finish();
*/

    }
}


