package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button next, previous;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    RadioButton a,b ,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("QuesBank");
      //  databaseReference = database.getReference("passwordTable").child("ques1");
        QuesModel quesModel=new QuesModel("how many "," new ","old ","just","check",2);
        databaseReference.push().setValue(quesModel);
        textView=findViewById(R.id.textView);
        a=findViewById(R.id.radioButton4);
        b=findViewById(R.id.radioButton3);
        c=findViewById(R.id.radioButton2);
        d=findViewById(R.id.radioButton);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("snap ", (snapshot.getValue().toString()));
              for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                  Log.e("id ",snapshot1.toString());
                  QuesModel quesModel1 = snapshot1.getValue(QuesModel.class);
                  textView.setText(quesModel1.getQues());
                  Log.e("question", quesModel1.getQues() + " " + snapshot.getValue(QuesModel.class));
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}