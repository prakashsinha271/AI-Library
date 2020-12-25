package com.aildemo.ailibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class password extends AppCompatActivity {

    Button btnChange, btnBack;
    EditText oldPass, newPass, confPass;
    DatabaseReference rootRef, childRef;
    TextView caps, num, small, words, sym;
    CheckBox show;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+='':;()*!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,15}" +               //at least 8 characters and max 15
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        final String sessionId = getIntent().getStringExtra("LoggedSTDID");
        oldPass = (EditText) findViewById(R.id.oldPass);
        newPass = (EditText) findViewById(R.id.newPass);
        confPass = (EditText) findViewById(R.id.confPass);

        btnChange = (Button) findViewById(R.id.btnChange);
        btnBack = (Button) findViewById(R.id.btnBack1);

        caps = (TextView) findViewById(R.id.AZ);
        num = (TextView) findViewById(R.id.num);
        small = (TextView) findViewById(R.id.az);
        words = (TextView) findViewById(R.id.character);
        sym = (TextView) findViewById(R.id.symbol);

        show = (CheckBox) findViewById(R.id.chkbox);

        rootRef = FirebaseDatabase.getInstance().getReference();
        childRef = rootRef.child("Students");

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)  {
                    newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnChange.setEnabled(false);

        btnChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String inOldP = null;
                String inNewP = null;
                String inConfP = null;// tPassword.getText().toString();
                try {
                    inOldP = oldPass.getText().toString();
                    inNewP = newPass.getText().toString();
                    inConfP = confPass.getText().toString();

                    final String finalInOldP = inOldP;
                    final String finalInNewP = inNewP;
                    final String finalInConfP = inConfP;

                    childRef.child(sessionId).child("Password").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String dbPassword = dataSnapshot.getValue(String.class);
                            if (dbPassword != null) {
                                if (dbPassword.equals(finalInOldP)) {

                                    childRef.child(sessionId).child("ContactNo").addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            final String dbContact = dataSnapshot.getValue(String.class);
                                            if (dbContact != null) {
                                                if (finalInNewP.equals(finalInConfP)) {
                                                    if (dbContact.equals(finalInNewP)) {
                                                        Toast.makeText(password.this, "Password can not be same as your registered mobile number", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        childRef.child(sessionId).child("Password").setValue(finalInNewP.toString());

                                                                   Intent i = new Intent(password.this, MainActivity.class);
                                                                  i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(i);
                                                    }

                                                } else {
                                                    Toast.makeText(password.this, "New password did not match with confirm password", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    Toast.makeText(password.this, "Incorrect old password", Toast.LENGTH_LONG).show();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(password.this, MainActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pas = newPass.getText().toString();
                validatePassword(pas);
                validates();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void validatePassword(String newPass) {

        Pattern upper = Pattern.compile("[A-Z]");
        Pattern lower = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern symbol = Pattern.compile("(?=.*[@#$%^&+=])");

        if (!lower.matcher(newPass).find()) {
            small.setTextColor(Color.RED);
        } else {
            small.setTextColor(Color.GREEN);
        }

        if (!upper.matcher(newPass).find()) {
            caps.setTextColor(Color.RED);
        } else {
            caps.setTextColor(Color.GREEN);
        }

        if (!digit.matcher(newPass).find()) {
            num.setTextColor(Color.RED);
        } else {
            num.setTextColor(Color.GREEN);
        }

        if (!symbol.matcher(newPass).find()) {
            sym.setTextColor(Color.RED);
        } else {
            sym.setTextColor(Color.GREEN);
        }

        if (newPass.length() > 7 && newPass.length() < 16) {
            words.setTextColor(Color.GREEN);
        } else {
            words.setTextColor(Color.RED);
        }
    }

    public boolean validates() {
        String passwordInput = newPass.getText().toString().trim();
       // Toast.makeText(password.this, "hiiPassword must be strong", Toast.LENGTH_LONG).show();

        if (passwordInput.isEmpty()) {
            btnChange.setEnabled(false);
            newPass.setError("Field can't be empty");
            return false;
         //   btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "hello Password must be strong", Toast.LENGTH_LONG).show();

        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            btnChange.setEnabled(false);
            newPass.setError("Password should include following characters.");
            return false;
          //  btnChange.setTextColor(Color.GRAY);
            //        Toast.makeText(password.this, "Password must be strong", Toast.LENGTH_LONG).show();

        } else {
            newPass.setError(null);
            btnChange.setEnabled(true);
            return true;
        }
    }

 /*   public void confirmInput(View v) {
        if (!validates()) {
            return;
        }

        String input = "Password: " + newPass.getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
*/

}

