package cc.brainbook.android.linkbuilder.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_sample) {
            startActivity(new Intent(this, SampleActivity.class));
        } else if (view.getId() == R.id.btn_html) {
            startActivity(new Intent(this, HtmlLinkExampleActivity.class));
        } else if (view.getId() == R.id.btn_list) {
            startActivity(new Intent(this, ListActivity.class));
        }
    }
}