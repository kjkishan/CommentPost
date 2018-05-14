package com.example.kishanjha.commentpost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.example.kishanjha.commentpost.UtilityClass.IS_LOGIN;
import static com.example.kishanjha.commentpost.UtilityClass.KEY_MYPREF;
import static com.example.kishanjha.commentpost.UtilityClass.USER_ID;

public class LoginActivity extends AppCompatActivity {

    public static final String IS_LOGIN="is_login";
    public static final String USER_ID="user_id";
    public static final String KEY_MYPREF="mypref";
    SharedPreferences checkLogin;
    SharedPreferences.Editor  myEditor;
    EditText email,pass;
    Button login,register;
    DatabaseReference myLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        email=findViewById ( R.id.nameOfUser );
        pass=findViewById ( R.id.Password );
        login=findViewById ( R.id.LoginButton );
        register=findViewById ( R.id.UserRegister );
        checkLogin = getSharedPreferences ( KEY_MYPREF,MODE_PRIVATE );
        myEditor=checkLogin.edit ();
        myLogin= FirebaseDatabase.getInstance ().getReference ("User");
        //Setting the OnClick Listener to the login button:
        login.setOnClickListener ( new View.OnClickListener () {


            @Override
            public void onClick(View v) {
                //getting the Email and Password from the user:
                final String emails,passw;
                emails=email.getText ().toString ();
                passw=pass.getText ().toString ();
                //checking the user email id:
                myLogin.orderByChild("email").equalTo(emails)
                        .addListenerForSingleValueEvent ( new ValueEventListener () {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists ()) {
                            //iterating the datasnapshot for the value:
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren ()) {
                                //getting the user details from the FirebaseDatabase:
                                User user = dataSnapshot1.getValue ( User.class );
                                String UserEmail = user.getEmail ();
                                String UserPassword = user.getPassword ();
                                String UserID= dataSnapshot1.getKey ();
                                String name = user.getName ();
                                //Checking the Details:
                                if ((UserEmail.equals ( emails )) && UserPassword.equals ( passw )) {
                                    //Making the SHaredPreference:
                                    myEditor.putBoolean ( "is_login",true ).putString ("user_id",UserID  ).apply ();
                                    //Making the Toast message after login in the to the Main Screen:
                                    Toast.makeText ( LoginActivity.this , "hello "+name.toUpperCase () , Toast.LENGTH_LONG ).show ();
                                    startActivity ( new Intent ( LoginActivity.this , MainActivity.class ) );
                                    //Finishing the  Activity:
                                    finish ();
                                }

                            }
                        }
                        else {
                            //Making the Toast Message if any Error in the User Data:
                            Toast.makeText ( LoginActivity.this , "Error !! Invalid Details" , Toast.LENGTH_LONG ).show ();
                        }

                    }

                    //If reloading of data is cancelled
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText ( LoginActivity.this , "Unknown Error"+databaseError.getDetails () , Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );
        //Taking to the Register Page:
        register.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity ( new Intent ( LoginActivity.this,UserRegistrationActivity.class ) );

            }
        } );

    }
}