package com.weza.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.media.AudioManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.weza.myapplication.util.RingerHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//set permissions
        checkPermissions();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //ImageView ringerOn = findViewById(R.id.r)
        // Find the ImageView by its ID

        // Find the ImageView by its ID
        Button button =  (Button) findViewById(R.id.switchbutton);

        // Set an initial image resource (e.g., ringer_off.png)
        updateImage();

        // Set a click listener for the ImageView
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here
                // For example, toggle between two images (ringer_on.png and ringer_off.png)
                RingerHelper.toggleRinger(audioManager);
                updateImage();
            }
        });
    }
    protected void updateImage(){
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button button =  (Button) findViewById(R.id.switchbutton);
        TextView helpText = findViewById(R.id.helpTextView);

        if (RingerHelper.isPhoneSilent(audioManager)) {
            imageView.setImageResource(R.drawable.ringer_off);
            button.setText("Turn sound ON");
            helpText.setText("Your sound is OFF");
        } else {
            imageView.setImageResource(R.drawable.ringer_on);
            button.setText("Turn sound OFF");
            helpText.setText("Your sound is ON");
        }
    }

    protected void checkPermissions() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * Every time the activity is resumed, make sure to update the
     * buttons to reflect the current state of the system (since the
     * user may have changed the phone’s silent state while we were in
     * the background).
     *
     * Visit http://d.android.com/reference/android/app/Activity.html
     * for more information about the Android Activity lifecycle.
     */
    @Override
    protected void onResume() {
        super.onResume();
// Update our UI in case anything has changed.
        updateImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
// Update our UI in case anything has changed.
        updateImage();
    }


}