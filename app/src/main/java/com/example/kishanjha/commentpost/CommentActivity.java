package com.example.kishanjha.commentpost;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements CommentAdapter.CommentId {

    //Making the Variables for Different variables:
    List<Comment> myLis;
    EditText namec,comment;
    Button sav;
    RecyclerView commRec;
    DatabaseReference commRef;
    CommentAdapter commentAdapter;


    public static final String IS_LOGIN="is_login";
    public static final String USER_ID="user_id";
    public static final String KEY_MYPREF="mypref";
    SharedPreferences checkLogin;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_comment );
        //connecting the UI component to the Activity:
        namec=findViewById ( R.id.CommentName );
        comment=findViewById ( R.id.CommentPost );
        sav=findViewById ( R.id.SaveComment );
        commRec=findViewById ( R.id.CommentRec );
        myLis=new ArrayList <> (  );



        checkLogin = getSharedPreferences (KEY_MYPREF,MODE_PRIVATE);
        myEditor = checkLogin.edit ();
        //Making the SharedPreference: variables for writing data to that:
        checkLogin = getSharedPreferences (KEY_MYPREF,MODE_PRIVATE);
        boolean isLogin = checkLogin.getBoolean ("is_login" , false );
        final String user = checkLogin.getString ( USER_ID,null );
        //passing the postID from the previous intent:
        String getPostId = getIntent ().getStringExtra ( "PostID" );
        //making the reference of database:
        commRef= FirebaseDatabase.getInstance ().getReference ("Posts").child ( getPostId ).child ( "Comment" );



        //Setting the onClick Listener to the save button
        sav.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name,commen;
                name=namec.getText ().toString ();
                commen=comment.getText ().toString ();
                String timeStamp = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").format( Calendar.getInstance().getTime());
                Comment comm = new Comment ( name,commen,timeStamp,user );
                commRef.push ().setValue ( comm ).addOnCompleteListener ( new OnCompleteListener <Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText ( CommentActivity.this , "Comment Success!" , Toast.LENGTH_SHORT ).show ();
                        finish ();
                    }
                } );
            }
        } );
        commentAdapter=new CommentAdapter (myLis,this);
        commRec.setAdapter ( commentAdapter );
        loadPost ();
    }
    public void loadPost(){
        commRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Checking the datasnapshot is null or not:
                if (dataSnapshot.exists ()){
                    //clearing the list if its full:
                    myLis.clear ();
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren ()){
                        Comment loadPost = dataSnapshot1.getValue (Comment.class);
                        String key = dataSnapshot1.getKey ();
                        //Assigning the key to the loadpost object:
                        loadPost.id=key;
                        myLis.add ( loadPost );
                    }
                }
                //Notifying the adapter for dataSet Changed:
                commentAdapter.notifyDataSetChanged ();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast for if any error:
                Toast.makeText ( CommentActivity.this , "Error"+databaseError.getDetails () , Toast.LENGTH_SHORT ).show ();

            }
        } );
    }
    //overirde the method for getting the comment ID:
    @Override
    public void getCommentId(String id) {

    }
}