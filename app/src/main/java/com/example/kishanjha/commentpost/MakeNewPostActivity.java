package com.example.kishanjha.commentpost;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MakeNewPostActivity extends AppCompatActivity {
    List<MakeNewPostActivity> post;
    DatabaseReference myPostRef;
    EditText name,posts;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_make_new_post );
        name=findViewById ( R.id.EditName );
        posts=findViewById ( R.id.EditPost );
        save=findViewById ( R.id.Save );
        myPostRef = FirebaseDatabase.getInstance ().getReference ("Posts");


        save.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String nam ,pos;
                nam=name.getText ().toString ();
                pos=posts.getText ().toString ();
                String timeStamp = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format( Calendar.getInstance().getTime());
                Post myPost = new Post ( nam,pos,timeStamp );
                myPostRef.push ().setValue ( myPost ).addOnCompleteListener ( new OnCompleteListener <Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText ( MakeNewPostActivity.this , "post success" , Toast.LENGTH_SHORT ).show ();
                            finish ();
                        }
                    }
                } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText ( MakeNewPostActivity.this , "Error"+e , Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );

    }
}
