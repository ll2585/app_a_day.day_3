package com.lukeli.appaday.day3;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    protected class User{
        protected String name;
        protected int wins;
        protected int losses;
        protected int arraylist_index;

        protected User(String name, int index){
            this.name = name;
            wins = 0;
            losses = 0;
            arraylist_index = index;
        }

        protected void win(){
            this.wins += 1;
        }

        protected void lose(){
            this.losses += 1;
        }
    }
    private ArrayList<String> listItems=new ArrayList<String>();
    private HashMap<String, User> user_map = new HashMap<String, User>();
    protected ArrayAdapter<String> adapter;

    private String[] random_info = {"There are two reds.", "You are blue", "The screen is white", "1+1=2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView scoreboard = (ListView) findViewById(R.id.score_list_view);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        scoreboard.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.exit_app){
            finish();
            return true;
        } else if (Arrays.asList(R.id.make_red, R.id.make_white, R.id.make_blue).contains(id)){
            DialogFragment color_fragment = CustomDialogFragment.newInstance(id);

            color_fragment.show(getFragmentManager(), "color fragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onJoinGameButton(View view) {

        Intent getNameScreenIntent = new Intent(this, SecondScreen.class);
        String random_info = getRandomInfo();
        final int result = 1;

        getNameScreenIntent.putExtra("randomInfo", random_info);

        startActivityForResult(getNameScreenIntent, result);
    }

    private String getRandomInfo(){
        Random rand = new Random();
        int rand_index =  rand.nextInt(random_info.length);
        return random_info[rand_index];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String usersName = data.getStringExtra("usersName");
        boolean win = data.getBooleanExtra("winStatus", false);
        boolean new_user = false;
        if(user_map.size() == 0 || !user_map.containsKey(usersName)){
            user_map.put(usersName, new User(usersName, listItems.size()));
            new_user = true;
        }
        if(win){
            user_map.get(usersName).win();
        }else{
            user_map.get(usersName).lose();
        }
        if(new_user){
            addToListView(user_map.get(usersName));
        }else{
            updateListView(user_map.get(usersName));
        }
    }

    private void addToListView(User u){
        ListView scoreboard = (ListView) findViewById(R.id.score_list_view);
        if (scoreboard.getVisibility() == View.GONE) {
            TextView no_scores_text_view = (TextView) findViewById(R.id.no_scores_text_view_id);
            no_scores_text_view.setVisibility(View.GONE);

            scoreboard.setVisibility(View.VISIBLE);
        }

        String player_score = getString(R.string.user_score);
        listItems.add(String.format(player_score, u.name, u.wins, u.losses));
        adapter.notifyDataSetChanged();
    }

    private void updateListView(User u){
        String user_score = listItems.get(u.arraylist_index);
        String player_score = getString(R.string.user_score);
        user_score = String.format(player_score, u.name, u.wins, u.losses);
        listItems.set(u.arraylist_index, user_score);
        adapter.notifyDataSetChanged();
    }
}
