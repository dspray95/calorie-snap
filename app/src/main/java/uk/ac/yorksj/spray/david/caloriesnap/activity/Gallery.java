package uk.ac.yorksj.spray.david.caloriesnap.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import uk.ac.yorksj.spray.david.caloriesnap.R;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle extras = getIntent().getExtras();
        String fileName = extras.getString("file");

        Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
    }
}
