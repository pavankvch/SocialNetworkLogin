package com.icore.socialnetworklogins;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

/**
 * Created by sundar on 20/10/16.
 */
public class HomePage extends AppCompatActivity {

    Profile profile;
    TextView tv_profile;
    ImageView imageView;
    private ProfileTracker profileTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackProfile();
        setContentView(R.layout.activity_home);

        init();
    }

    private void trackProfile() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                displayProfile(Profile.getCurrentProfile());
            }
        };
    }

    private void displayProfile(Profile profile) {
        if(profile!=null) {
            tv_profile.setText(profile.getFirstName() + " " + profile.getLastName());
            Uri imageUri = profile.getProfilePictureUri(200, 200);
            Picasso.with(getApplicationContext()).load(imageUri).into(imageView);
        }
    }

    private void init() {

        tv_profile=(TextView)findViewById(R.id.tv_profileName);
        imageView=(ImageView)findViewById(R.id.img_profile);

        Bundle b = this.getIntent().getExtras();
        if (b != null){
            profile= b.getParcelable("PROFILE");

        }

        (findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
