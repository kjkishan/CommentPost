package com.example.kishanjha.commentpost;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditPostActivity extends AppCompatActivity {

    EditText name,post;
    Button save;
    DatabaseReference myRef;
    Post loadPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_post );
        name=findViewById ( R.id.EditName1 );
        post=findViewById ( R.id.EditPost1 );
        save=findViewById ( R.id.Save1 );
        String getPostId = getIntent ().getStringExtra ( "PostID" );
        myRef= FirebaseDatabase.getInstance ().getReference ("Posts").child ( getPostId );
        setPost();
        save.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String namel,postl;
                String timeStamp = new SimpleDateFormat ("yyyyMMdd_HHmmss").format( Calendar.getInstance().getTime());
                namel = name.getText ().toString ();
                postl = post.getText ().toString ();
                Post loadPost =new Post ( namel,postl,timeStamp );
                myRef.setValue ( loadPost ).addOnCompleteListener ( new OnCompleteListener <Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText ( EditPostActivity.this , "Success" , Toast.LENGTH_SHORT ).show ();
                            finish ();
                        }
                        else {
                            Toast.makeText ( EditPostActivity.this , "Error" , Toast.LENGTH_SHORT ).show ();
                        }
                    }
                } );
            }
        } );

    }

    public void setPost(){
        myRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren ()){
                        Post loadPost =dataSnapshot.getValue (Post.class);
                        name.setText ( loadPost.getName () );
                        post.setText ( loadPost.getPost () );

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

    }
}
