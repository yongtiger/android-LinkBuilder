package cc.brainbook.android.linkbuilder.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cc.brainbook.android.linkbuilder.Link;
import cc.brainbook.android.linkbuilder.LinkBuilder;

public class SampleActivity extends AppCompatActivity {

    private static final String GITHUB_LINK = "https://github.com/klinker24";
    private static final String TWITTER_PROFILE = "https://twitter.com/";
    private static final String PLAY_STORE = "https://play.google.com/store/apps/developer?id=Klinker+Apps&hl=en";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the content view. Contains a scrollview with a text view inside
        setContentView(R.layout.activity_sample);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);

        // find the text view. Used to create the link builder
        TextView demoText = (TextView) findViewById(R.id.test_text);

        // Add the links and make the links clickable
        LinkBuilder.on(demoText)
                .addLinks(getExampleLinks())
                .build();
    }

    private List<Link> getExampleLinks() {
        List<Link> links = new ArrayList<>();

        // create a single click link to the github page
        Link github = new Link("TextView-LinkBuilder");
        github.setTypeface(Typeface.DEFAULT_BOLD)
        .setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                openLink(GITHUB_LINK);
            }
        });

        // create a single click link to the matched twitter profiles
        Link mentions = new Link(Pattern.compile("@\\w{1,15}"));
        mentions.setTextColor(Color.parseColor("#00BCD4"));
        mentions.setHighlightAlpha(.4f);
        mentions.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                openLink(TWITTER_PROFILE + clickedText.replace("@", ""));
            }
        });

        // match the numbers that I created
        Link numbers = new Link(Pattern.compile("[0-9]+"));
        numbers.setTextColor(Color.parseColor("#FF9800"));
        numbers.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                showToast("Clicked: " + clickedText);
            }
        });

        // action on a long click instead of a short click
        Link longClickHere = new Link("here");
        longClickHere.setTextColor(Color.parseColor("#259B24"));
        longClickHere.setOnLongClickListener(new Link.OnLongClickListener() {
            @Override
            public void onLongClick(String clickedText) {
                showToast("You long clicked. Nice job.");
            }
        });

        // underlined
        Link yes = new Link("Yes");
        yes.setUnderlined(true);
        yes.setTextColor(Color.parseColor("#FFEB3B"));

        // not underlined
        Link no = new Link("No");
        no.setUnderlined(false);
        no.setTextColor(Color.parseColor("#FFEB3B"));

        // bold
        Link bold = new Link("bold");
        bold.setBold(true);
        bold.setTextColor(Color.parseColor("#FF0000"));

        // prepended text
        Link prepend = new Link("prepended");
        prepend.setPrependedText("(!)");

        Link appended = new Link("appended");
        appended.setAppendedText("(!)");

        // link to our play store page
        Link playStore = new Link("Play Store");
        playStore.setTextColor(Color.parseColor("#FF9800"));
        playStore.setTextColorOfHighlightedLink(Color.parseColor("#FF6600"));
        playStore.setHighlightAlpha(0f);
        playStore.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                openLink(PLAY_STORE);
            }
        });

        // add the links to the list
        links.add(github);
        links.add(mentions);
        links.add(numbers);
        links.add(longClickHere);
        links.add(yes);
        links.add(no);
        links.add(bold);
        links.add(prepend);
        links.add(appended);
        links.add(playStore);

        return links;
    }

    private void openLink(String link) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private void showToast(String text) {
        Toast.makeText(SampleActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
