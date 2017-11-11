package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.ac.yorksj.spray.david.caloriesnap.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
        finish();
    }
}
