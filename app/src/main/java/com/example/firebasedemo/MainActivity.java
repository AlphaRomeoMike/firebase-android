package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private Button add;
    private EditText edit;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listview);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "User has been logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });
        //TODO: TRY THIS
        //FirebaseDatabase.getInstance().getReference().child("Firebase Tutorial").child("Android").setValue("Alpha Romeo Mike");

        //TODO: FOR MULTIPLE BRANCHES
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Name", "Abdul Rafay Modi");
//        map.put("Email", "rafaymodi18@gmail.com");
//
//        FirebaseDatabase.getInstance().getReference().child("Firebase Tutorials").child("Multiple Values").updateChildren(map);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_anything = edit.getText().toString().toString();
                if(text_anything.isEmpty()) {
                    Snackbar.make(v, "Empty value cannot be submitted", Snackbar.LENGTH_LONG);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Languages").child("Name").setValue(text_anything);
                    Toast.makeText(MainActivity.this, text_anything + " has been submitted", Toast.LENGTH_LONG).show();
                    edit.setText("");
                }
            }
        });
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Languages");
        //DETAILS OF EACH DATA OF LANGUAGES BRANCH
        reference.addValueEventListener(new ValueEventListener() {
            //TO ITERATE THROUGH EACH DATA
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}