package com.example.kishanjha.commentpost;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeletePostActivity extends AppCompatActivity {

    Button conf;
    DatabaseReference delRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_delete_post );
        conf=findViewById ( R.id.confirmDelete );
        //getting the post id
        String getPostId = getIntent ().getStringExtra ( "PostID" );
        //reference of database:
        delRef= FirebaseDatabase.getInstance ().getReference ("Posts").child ( getPostId );
        //confirem button setting the onClickListener:
        conf.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                delRef.removeValue ().addOnCompleteListener ( new OnCompleteListener <Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful ()){
                            Toast.makeText ( DeletePostActivity.this , "Post Deleted" , Toast.LENGTH_SHORT ).show ();
                            finish ();
                        }
                    }
                } );
            }
        } );
    }
}
