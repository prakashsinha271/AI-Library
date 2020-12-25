package com.aildemo.ailibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class registration extends AppCompatActivity {

    TextView selected, viewTxt;
    EditText inID, inName, inPName, inMobile, inRoll, inAdYear;
    Button bntSave,tvSign,tvClear, btnMax;
    DatabaseReference rootRef;
    private Spinner spinner;
    long maxID = 0;
    String tmpID = "STD001";
    String autoIdNo = "STD001";
    private Toast editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inName = (EditText) findViewById(R.id.txtName);
        inPName = (EditText) findViewById(R.id.txtPName);
        inMobile = (EditText) findViewById(R.id.txtMobile);
        inRoll = (EditText) findViewById(R.id.txtRoll);
        inAdYear = (EditText) findViewById(R.id.txtAdYear);
        bntSave = (Button) findViewById(R.id.btnReg);
        viewTxt = (TextView) findViewById(R.id.viewTxt);
        // selected = findViewById(R.id.selectDept);
        spinner = findViewById(R.id.spinner);
        tvSign = findViewById(R.id.btnCancel);
        tvClear = findViewById(R.id.btnReset);

        rootRef = FirebaseDatabase.getInstance().getReference().child("Students");
        //childRef = rootRef.child("Students");

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(registration.this, MainActivity.class);
                startActivity(i);
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        final List<String> Categories = new ArrayList<>();
        //Categories.add(0,"Department");
        Categories.add("Computer Science and Engineering");
        Categories.add("Electronics and Telecommunication");
        Categories.add("Information Technology");

        ArrayAdapter dataAdapter;
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals(Categories)) {
                    String text = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();

                }
                else {
                    //selected.setText(parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bntSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rootRef.addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            maxID = (dataSnapshot.getChildrenCount());
                            if(maxID > 0){
                                for(int i = 1; i<=maxID; i++){
                                    if(i>0 && i<10){
                                        rootRef.child(autoIdNo).child("StudentID").addValueEventListener(new ValueEventListener(){

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    final String dbID = dataSnapshot.getValue(String.class);
                                                    if (dbID.equals(autoIdNo)) {
                                                        String tmp = autoIdNo.substring(3,6);
                                                        int num = 0;
                                                        try{
                                                            num = Integer.parseInt(tmp);
                                                        }//end of try
                                                        catch (Exception e) {
                                                            e.printStackTrace();
                                                        }// end of catch

                                                        num++;
                                                        tmp = Integer.toString(num);
                                                        autoIdNo = "STD00".concat(tmp);

                                                    }//end of if (dbID.equals(autoIdNo))

                                                } // end of  if (dataSnapshot.exists())

                                                else{
                                                    autoID(autoIdNo);
                                                } // end of else

                                            }//end of onDataChange

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }//end of onCancelled

                                        });//end of rootRef.child(autoIdNo)

                                    }//end of if(i>0 && i<10)

                                }//end of for loop

                            }//end of if(maxID > 0)

                        } // end of if (dataSnapshot.exists())

                        else{
                            //assign autoIDNo = "STD001" and call
                            autoID(autoIdNo);
                        } //end of else

                    } //end of onDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    } //end of onCancelled
                });//end of line rootRef.addListenerForSingleValueEvent
            }//end of line onClick
        }); //end of line btnSave.setOnClickListener
    }

    private void  addArrayList(String i) {
        String ID = i.trim();
        String NAME = inName.getText().toString().trim();
        String PNAME = inPName.getText().toString().trim();
        editText.setText(PNAME.toUpperCase());
        String MOBILE = inMobile.getText().toString().trim();
        String ROLLNO = inRoll.getText().toString().trim();
        String ADMISSION = inAdYear.getText().toString().trim();
        String SPINNER1 = String.valueOf(spinner.getSelectedItem());

        if (TextUtils.isEmpty(NAME)) {
            Toast.makeText(registration.this,"PLEASE ENTER NAME", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(PNAME)) {
            Toast.makeText(registration.this,"PLEASE ENTER GUARDIAN NAME", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(MOBILE)) {
            Toast.makeText(registration.this,"PLEASE ENTER VALID MOBILE", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(ROLLNO)) {
            Toast.makeText(registration.this,"PLEASE ENTER VALID ROLLNO", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(ADMISSION)) {
            Toast.makeText(registration.this,"PLEASE ENTER ADMISSION YEAR", Toast.LENGTH_LONG).show();
        }
        else  {

            Student students = new Student(ID,NAME,PNAME,MOBILE,ROLLNO,ADMISSION,SPINNER1);
            rootRef.child(ID).child("StudentID").setValue(ID.toString());
            rootRef.child(ID).child("Name").setValue(NAME.toString());
            rootRef.child(ID).child("Parent").setValue(PNAME.toString());
            rootRef.child(ID).child("ContactNo").setValue(MOBILE.toString());
            rootRef.child(ID).child("RollNo").setValue(ROLLNO.toString());
            rootRef.child(ID).child("AdmissionYear").setValue(ADMISSION.toString());
            rootRef.child(ID).child("Branch").setValue((SPINNER1.toString()));
            rootRef.child(ID).child("BookIssued").setValue(("xx"));
            rootRef.child(ID).child("Password").setValue(("def123"));

            viewTxt.setText("Your Registration ID is: " + ID + "and Password is your registered Mobile Number, You can login after allowed by Librarian");

            inName.setEnabled(false);
            inPName.setEnabled(false);
            inMobile.setEnabled(false);
            inRoll.setEnabled(false);
            inAdYear.setEnabled(false);
            bntSave.setEnabled(false);
            selected.setEnabled(false);
            spinner.setEnabled(false);
            tvClear.setEnabled(false);
            bntSave.setEnabled(false);
            /*Intent k = new Intent(registration.this, MainActivity.class);
            k.setFlags(k.FLAG_ACTIVITY_NEW_TASK | k.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(k);*/
        }

    }
    private void clearForm() {

        //inID.setText("");
        inName.setText("");
        inPName.setText("");
        inMobile.setText("");
        inRoll.setText("");
        inAdYear.setText("");
        //    spinner.getDropDownHorizontalOffset();
    }

    private void autoID(String i){
        //Toast.makeText(this,"ID " + i, Toast.LENGTH_LONG).show();
        addArrayList(i);

    }
}

