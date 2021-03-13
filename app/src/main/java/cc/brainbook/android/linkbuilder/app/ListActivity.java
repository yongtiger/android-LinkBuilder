package cc.brainbook.android.linkbuilder.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * This sample illustrates how to use LinkBuilder along with a ListView.OnItemClickListener method.
 *
 * It is pretty simple, but requires you to implement the LinkConsumableTextView rather than a normal
 * TextView in your layout.
 *
 * By doing this, the LinkConsumableTextView will only consume the touch event if the link was actually clicked.
 * If the link was not clicked, then it will defer to your ListView.OnItemClickListener method instead.
 *
 * The SampleAdapter contains the LinkBuilder code for the list items.
 */
public class ListActivity extends AppCompatActivity {
    private static final String TAG = ListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new SampleAdapter(this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onListItemClick position=" + i);
            }
        });
    }
}
