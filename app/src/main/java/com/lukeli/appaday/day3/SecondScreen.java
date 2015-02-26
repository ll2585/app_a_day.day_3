package com.lukeli.appaday.day3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondScreen extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_screen);

        Intent activityThatCalled = getIntent();

        String random_info = activityThatCalled.getExtras().getString("randomInfo");

        String random_prompt = getString(R.string.secret_info_text_view);

        TextView random_info_text_view = (TextView) findViewById(R.id.secret_info_text_view_id);

        random_info_text_view.setText(String.format(random_prompt, random_info));
    }

    public void onWinButtonClick(View view) {
        handleClick(true);
    }

    public void onLoseButtonClick(View view) {
        handleClick(false);
    }

    private void handleClick(boolean win){
        EditText usersNameET = (EditText) findViewById(R.id.users_name_edit_text);
        String usersName = String.valueOf(usersNameET.getText());
        Intent goingBack = new Intent();
        goingBack.putExtra("usersName", usersName);
        goingBack.putExtra("winStatus", win);
        setResult(RESULT_OK, goingBack);
        finish();
    }
}
