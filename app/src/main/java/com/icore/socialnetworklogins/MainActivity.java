package com.icore.socialnetworklogins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private TextView textView;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    LoginButton loginButton;
    AccessToken accessToken;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        facebookSDKInitialize();

        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textView);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        getLoginDetails(loginButton);
    }

    /*
Initialize the facebook sdk.
And then callback manager will handle the login responses.
*/
    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
               Profile profile= Profile.getCurrentProfile();
                displayMessage(currentProfile);
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

    }


    protected void getLoginDetails(LoginButton login_button){

// Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                Log.d(" getLoginDetails( )", " "+login_result.getRecentlyGrantedPermissions());

                Intent intent = new Intent(getApplicationContext(), HomePage.class);

                Bundle b = new Bundle();
                b.putParcelable("PROFILE", Profile.getCurrentProfile());
                intent.putExtras(b);
                intent.setClass(MainActivity.this, HomePage.class);
                startActivity(intent);
//                intent.putExtra("PROFILE", Profile.getCurrentProfile());
//                startActivity(intent);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        Profile profile = Profile.getCurrentProfile();
//        displayMessage(profile);
        Log.e("data",data.toString());
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }

    private void displayMessage(Profile profile){
        if(profile != null){

            Log.d(" getLoginDetails( )", " Profile Name: "+profile.getFirstName()+" "+profile.getLastName());
            textView.setText(profile.getName());
        }
    }

}




//    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            AccessToken accessToken = loginResult.getAccessToken();
//            Profile profile = Profile.getCurrentProfile();
//            displayMessage(profile);
//        }
//
//        @Override
//        public void onCancel() {
//
//        }
//
//        @Override
//        public void onError(FacebookException e) {
//
//        }
//    };
//
//    public MainActivity() {
//
//    }


//        callbackManager = CallbackManager.Factory.create();
//
//        accessTokenTracker= new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
//
//            }
//        };

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//
//    }
//
//    private void displayMessage(Profile profile){
//        if(profile != null){
//            textView.setText(profile.getName());
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        setContentView(R.layout.activity_main);
//        Profile profile = Profile.getCurrentProfile();
//        displayMessage(profile);
//
//        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
//        textView = (TextView) findViewById(R.id.textView);
//
//        loginButton.setReadPermissions("user_friends");
////        loginButton.setFragment(getApplicationContext());
//        loginButton.registerCallback(callbackManager, callback);
//
//        profileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                displayMessage(newProfile);
//            }
//        };
//
//        accessTokenTracker.startTracking();
//        profileTracker.startTracking();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        accessTokenTracker.stopTracking();
//        profileTracker.stopTracking();
//    }
