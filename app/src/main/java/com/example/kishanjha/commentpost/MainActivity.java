package com.example.kishanjha.commentpost;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.kishanjha.commentpost.PostAdapter.*;
import static com.example.kishanjha.commentpost.UtilityClass.IS_LOGIN;
import static com.example.kishanjha.commentpost.UtilityClass.KEY_MYPREF;
public class MainActivity extends AppCompatActivity implements PostIdListener {
    
    //Making the list for Post type:
    List<Post> myLis;
    RecyclerView rec;
    FloatingActionButton fab;
    DatabaseReference myRef;
    PostAdapter adapter;
    //Fields for the Sharedpreference:
    public static final String IS_LOGIN="is_login";
    public static final String USER_ID="user_id";
    public static final String KEY_MYPREF="mypref";
    SharedPreferences checkLogin;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        fab=findViewById ( R.id.Fab );
        rec=findViewById ( R.id.MainPageRec );
        checkLogin = getSharedPreferences (KEY_MYPREF,MODE_PRIVATE);
        myEditor = checkLogin.edit ();
        //Making the SharedPreference: variables for writing data to that:
        checkLogin = getSharedPreferences (KEY_MYPREF,MODE_PRIVATE);
        boolean isLogin = checkLogin.getBoolean ("is_login" , false );
        String user = checkLogin.getString ( USER_ID,null );
        if (!isLogin){
            startActivity ( new Intent ( MainActivity.this,LoginActivity.class ) );
            finish ();

        }
        //initialize the list:
        myLis = new ArrayList <> (  );
        myRef= FirebaseDatabase.getInstance ().getReference ("Posts");
        //Adding the Fab to onClickListener:
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //taking to the nextActivity
                startActivity ( new Intent ( MainActivity.this,MakeNewPostActivity.class ) );
            }
        } );

        adapter = new PostAdapter ( myLis , this);
        rec.setAdapter ( adapter );
        loadPost();
    }

    //Loading the post in RecycleView:
    public void loadPost(){
        myRef.addValueEventListener ( new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //checking the list
                if (dataSnapshot!=null){
                    //clearing the list:
                    myLis.clear ();
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren ()){
                        Post loadPost = dataSnapshot1.getValue (Post.class);
                        String key = dataSnapshot1.getKey ();
                        loadPost.id=key;
                        myLis.add ( loadPost );
                    }
                }
                adapter.notifyDataSetChanged ();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );

    }

    @Override
    public void editPost(String id) {
        Intent edit = new Intent ( MainActivity.this, EditPostActivity.class);
        edit.putExtra ( "PostID",id );
        startActivity ( edit );
    }


    @Override
    public void deletePost(String id) {
        Intent edit = new Intent ( MainActivity.this, DeletePostActivity.class);
        edit.putExtra ( "PostID",id );
        startActivity ( edit );
    }


    @Override
    public void commentPost(String id) {

        Intent edit = new Intent ( MainActivity.this, CommentActivity.class);
        edit.putExtra ( "PostID",id );
        startActivity ( edit );
    }
    //Making the onOption menu for Logout menu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater ();
        inflater.inflate ( R.menu.menu_item,menu );
        return super.onCreateOptionsMenu ( menu );
    }

    //for selecting the menu option in the android option menu:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()){
            //Case one for logout option if user select it:
            case R.id.logout:
                SharedPreferences.Editor Editor= checkLogin.edit ();
                //Removing the data from the SharedPreference:
                Editor.remove ( IS_LOGIN );
                Editor.commit ();
                Toast.makeText ( this , "Logout Successfull !!" , Toast.LENGTH_LONG ).show ();
                startActivity ( new Intent ( this,LoginActivity.class ) );
                finish ();
                break;
            //Case two for the setting:
            case R.id.Setting:
                    Toast.makeText ( this , "This option is comming soon keep Patient !!" , Toast.LENGTH_LONG ).show ();
                    break;
            default:break;
        }

        return super.onOptionsItemSelected ( item );
    }
}