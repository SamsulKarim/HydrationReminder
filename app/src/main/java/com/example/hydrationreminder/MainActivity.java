package com.example.hydrationreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hydrationreminder.sync.WaterReminderIntentService;
import com.example.hydrationreminder.utilities.PreferenceUtilities;
import com.example.hydrationreminder.utilities.ReminderTasks;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mChargingCountDisplay, mWaterCountDisplay;

    private Button hydrate_button;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChargingCountDisplay = findViewById(R.id.charging_reminder_text);
        mWaterCountDisplay = findViewById(R.id.water_count_text);
        hydrate_button = findViewById(R.id.hydrate_button);
        hydrate_button.setOnClickListener(this);

        updateWaterCount();
        updateChargingReminderCount();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);


    }

    public void incrementWater(View view){

        if(mToast != null)mToast.cancel();
        mToast = Toast.makeText(this,"GLUG GLUG GLUG",Toast.LENGTH_SHORT);
        mToast.show();
        Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        startService(incrementWaterCountIntent);



    }
    private void updateWaterCount(){
        int waterCount = PreferenceUtilities.getWaterCount(this);
        mWaterCountDisplay.setText(waterCount+" hoes mad");
    }
    private void updateChargingReminderCount(){
        int chargingReminders = PreferenceUtilities.getChargingReminderCount(this);
        String formattedChargingReminders = getResources().getQuantityString(R.plurals.charge_notification_count,chargingReminders,chargingReminders);
        mChargingCountDisplay.setText(formattedChargingReminders);
    }

    @Override
    public void onClick(View view) {
        incrementWater(view);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount();
        } else if (PreferenceUtilities.KEY_CHARGING_REMINDER_COUNT.equals(key)) {
            updateChargingReminderCount();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
}