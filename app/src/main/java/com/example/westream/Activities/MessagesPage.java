package com.example.westream.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.westream.R;
import com.example.westream.Models.UserInfo;
import com.example.westream.Adapters.UsersAdapter;
import com.example.westream.databinding.ActivityMessagesPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesPage extends AppCompatActivity {
    ActivityMessagesPageBinding binding;

    FirebaseDatabase database;
    ArrayList<UserInfo>users;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
        users = new ArrayList<>();

        usersAdapter = new UsersAdapter(this,users);
        binding.recyclerView.setAdapter(usersAdapter);

        database.getReference().child("userinfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    UserInfo user = snapshot1.getValue(UserInfo.class);
                    System.out.println(user.getUid());
                    if(!user.getUid().contains(FirebaseAuth.getInstance().getUid())) {
                        users.add(user);
                    }
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.search:
                Toast.makeText(this,"search clicked.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"Settings clicked.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.videocall:
                startActivity(new Intent(MessagesPage.this,DashboardActivity.class));
                //Toast.makeText(this,"Video call clicked.",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.topmsg_menu,menu);
        //getMenuInflater().inflate(R.menu.msg_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}
