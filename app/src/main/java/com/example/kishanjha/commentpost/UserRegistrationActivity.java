package com.example.kishanjha.commentpost;

import android.content.Intent;
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

public class UserRegistrationActivity extends AppCompatActivity {
    EditText name,email,phone,password,conPass;
    Button regis;
    DatabaseReference regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_user_registration );
        name=findViewById ( R.id.userName );
        email=findViewById ( R.id.userEmail );
        phone=findViewById ( R.id.userPassword );
        password=findViewById ( R.id.userPassword );
        conPass=findViewById ( R.id.ConfirmPassword );
        regis=findViewById ( R.id.Register );
        regist= FirebaseDatabase.getInstance ().getReference ("User");
        regis.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String nam,eml,phn,pass,cpass;
                nam=name.getText ().toString ();
                eml=email.getText ().toString ();
                pass=password.getText ().toString ();
                cpass=conPass.toString ().toString ();
                phn=phone.getText ().toString ();
                //Validation
                if (nam.isEmpty () && eml.isEmpty () && phn.isEmpty () && pass.isEmpty ()){
                    Toast.makeText ( UserRegistrationActivity.this , "Enter All Details" , Toast.LENGTH_SHORT ).show ();

                }
                else{

                    User User = new User ( nam,eml,phn,pass );
                    regist.push ().setValue ( User ).addOnCompleteListener ( new OnCompleteListener <Void> () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText ( UserRegistrationActivity.this , "User Success" , Toast.LENGTH_LONG ).show ();
                            startActivity ( new Intent ( UserRegistrationActivity.this,LoginActivity.class ) );
                            finish ();
                        }
                    } ).addOnFailureListener ( new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText ( UserRegistrationActivity.this , "Error !!"+e.getMessage () , Toast.LENGTH_SHORT ).show ();
                        }
                    } );



                }


            }
        } );




    }
}
