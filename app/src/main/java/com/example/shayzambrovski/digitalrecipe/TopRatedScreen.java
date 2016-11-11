package com.example.shayzambrovski.digitalrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

public class TopRatedScreen extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView oButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_rated_screen);
        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1200);
        view.startAnimation(mLoadAnimation);
        Bundle extras = getIntent().getExtras();
        String sUserName = extras.getString("key");
        bindUI(sUserName);
    }
    public void bindUI(final String sUserName) {
        try {
            this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
            final ProgressBar oProgressBar = this.progressBar;
            oProgressBar.setVisibility(View.GONE);
            final Context oContext = this;
            //final DatabaseHandler db = new DatabaseHandler(oContext);

            DatabaseHandlerV3 loader = new DatabaseHandlerV3(oContext, 2, oProgressBar, (RelativeLayout) findViewById(R.id.shaike), sUserName);

            try {
                final List<Recipe> recipeList = (List<Recipe>)loader.execute().get();
                String[] topRated = new String[recipeList.size()];
                for(int i = 0 ; i < recipeList.size() ; i ++) {
                    Recipe r = recipeList.get(i);
                    topRated[i] = "Recipe name: " + r.getName() + ".\nRecipe author: " + r.getUserName() + ".\nRate: " + r.getRate();
                }
                ListView listView = (ListView) findViewById(R.id.myListView);
                listView.setTextFilterEnabled(true);
                listView.setAdapter(new ArrayAdapter<String>(oContext, R.layout.my_item, topRated));

            } catch (Exception e) {
                Log.e("Error: ", e.toString());
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}
