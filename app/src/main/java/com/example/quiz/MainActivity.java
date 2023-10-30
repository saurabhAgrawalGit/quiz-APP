package com.example.quiz;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button next, previous;
    ImageButton imageButton;
    ArrayList<QuesModel> arrayList= new ArrayList<>();

    RadioGroup radioGroup;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

   int i =0;
    RadioButton a,b ,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("QuesBank");
      //  databaseReference = database.getReference("passwordTable").child("ques1");
        QuesModel quesModel=new QuesModel("how many "," new ","old ","just","check",2);
       // databaseReference.push().setValue(quesModel);
        imageButton =findViewById(R.id.imageView);

        i=0;

        textView=findViewById(R.id.ques);
        a=findViewById(R.id.radioButton4);
        b=findViewById(R.id.radioButton3);
        c=findViewById(R.id.radioButton2);
        d=findViewById(R.id.radioButton);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.pre);
        radioGroup=findViewById(R.id.radioGroup);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               arrayList.clear();
              for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                  QuesModel quesModel1 = snapshot1.getValue(QuesModel.class);
                  arrayList.add(quesModel1);
              }
                textView.setText(arrayList.get(i).getQues());
                a.setText(arrayList.get(i).getOptionA());
                b.setText(arrayList.get(i).getOptionB());
                c.setText(arrayList.get(i).getOptionC());
                d.setText(arrayList.get(i).getOptionD());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Toast.makeText(MainActivity.this, "radio group"+checkedId+" "+a.getId(), Toast.LENGTH_SHORT).show();

                    }
                });

                i++;
                if(-1<i&&i<arrayList.size()) {
                    textView.setText(arrayList.get(i).getQues());
                    a.setText(arrayList.get(i).getOptionA());
                    Log.e("ques ", arrayList.get(i).getQues());
                    b.setText(arrayList.get(i).getOptionB());
                    c.setText(arrayList.get(i).getOptionC());
                    d.setText(arrayList.get(i).getOptionD());

                }
                else{
                    Toast.makeText(MainActivity.this, "Question Over", Toast.LENGTH_SHORT).show();
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if(-1<i&&i<arrayList.size()) {
                    textView.setText(arrayList.get(i).getQues());
                    a.setText(arrayList.get(i).getOptionA());
                    Log.e("ques ", arrayList.get(i).getQues());
                    b.setText(arrayList.get(i).getOptionB());
                    c.setText(arrayList.get(i).getOptionC());
                    d.setText(arrayList.get(i).getOptionD());




                }
                else{
                    Toast.makeText(MainActivity.this, "Question Over", Toast.LENGTH_SHORT).show();
                }

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

              //  Toast.makeText(MainActivity.this, "radio group"+checkedId+" "+a.getId(), Toast.LENGTH_SHORT).show();

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s= radioGroup.getCheckedRadioButtonId();
                RadioButton just  =findViewById(s);
                  String name = just.getText().toString();

                if(s==-1)
                {
                    Toast.makeText(MainActivity.this,"you not select any option ", Toast.LENGTH_SHORT).show();
                }

                else if(just.getText().toString()==name)
                {
                   Toast.makeText(MainActivity.this,"your answer is correct", Toast.LENGTH_SHORT).show();
                    imageButton.setImageResource(R.drawable.right);
                }
                else
                {
                   Toast.makeText(MainActivity.this,"your answer is wrong ", Toast.LENGTH_SHORT).show();
                    imageButton.setImageResource(R.drawable.ic_baseline_do_not_disturb_alt_24);
                }
               // Toast.makeText(MainActivity.this,String.valueOf(s), Toast.LENGTH_SHORT).show();
                //arrayList.get(i).getQues();

            }
        });



    }
}