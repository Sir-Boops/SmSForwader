package org.boops.smsforwader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    static String number = "";
    static boolean run = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load prefs
        final SharedPreferences prefs = this.getSharedPreferences("org.boops.SmSForwader", Context.MODE_PRIVATE);

        // Load all the buttons
        final EditText number = findViewById(R.id.number);
        final Button save = findViewById(R.id.save);
        final Switch run = findViewById(R.id.run);

        // Make phone number look pretty
        number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // On start make not running
        run.setChecked(false);

        // Reload the number
        MainActivity.number = prefs.getString("number", "");
        number.setText(prefs.getString("number", ""));

        // On Run Switch Change
        run.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    MainActivity.run = true;
                    number.setEnabled(false);
                    save.setEnabled(false);
                } else {
                    MainActivity.run = false;
                    number.setEnabled(true);
                    save.setEnabled(true);
                }
            }
        });

        // On Save button click
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("number", number.getText().toString().replaceAll("\\D+", "")).apply();
                MainActivity.number = prefs.getString("number", "");
            }
        });

    }
}
