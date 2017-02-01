package com.sourceedge.preco.login.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.register.controller.NewRegister;
import com.sourceedge.preco.register.controller.PrefManager;
import com.sourceedge.preco.register.controller.WelcomeActivity;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static android.R.attr.data;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private PrefManager prefManager;

    EditText username, password;
    TextView loginButton;
    LinearLayout registerNow;
    SharedPreferences sharedPreferences;
    LoginButton loginButton1;
    ImageButton fbLogin,googleLogin;

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;

   //private static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private ProgressDialog mProgressDialog;
    GoogleSignInOptions gso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (TextView) findViewById(R.id.login_button);
        registerNow = (LinearLayout) findViewById(R.id.register_now);
        fbLogin=(ImageButton)findViewById(R.id.fb_login_button);
        googleLogin=(ImageButton)findViewById(R.id.g_sign_in_button);
        loginButton1 = (LoginButton) findViewById(R.id.facebook_login_button);
        sharedPreferences=this.getSharedPreferences(Class_Genric.MyPref,MODE_PRIVATE);

        gso = ((MyApplication) getApplication()).getGoogleSignInOptions();
        Class_Static.mGoogleApiClient = ((MyApplication) getApplication()).getGoogleApiClient(Login.this, this);
        OnClicks();
        printHashKey();
        facebookLogin();


    }

    private void facebookLogin() {
        loginButton1.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton1.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    Class_Static.isFacebookSignIn=true;
                                    prefManager = new PrefManager(Login.this);
                                    if (!prefManager.isFirstTimeLaunch())
                                        startActivity(new Intent(Login.this, HomeScreen.class));
                                    else startActivity(new Intent(Login.this, WelcomeActivity.class));
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString(Class_Genric.Sp_Status,"LoggedIn");
                                    editor.commit();
                                    finish();
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    //tv_profile_name.setText(name);
                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                    //Picasso.with(MainActivity.this).load(imageurl).into(iv_profile_pic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
                            //tv_profile_name.setText("");
                            //iv_profile_pic.setImageResource(R.drawable.maleicon);
                        }
                    }
                };
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Class_Static.mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void signOut() {
        Auth.GoogleSignInApi.signOut(Class_Static.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //updateUI(false);
                    }
                });
    }

    public static void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(Class_Static.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //updateUI(false);
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
       // Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Class_Static.isGoogleSignIn=true;
            prefManager = new PrefManager(Login.this);
            if (!prefManager.isFirstTimeLaunch())
                startActivity(new Intent(Login.this, HomeScreen.class));
            else startActivity(new Intent(Login.this, WelcomeActivity.class));
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(Class_Genric.Sp_Status,"LoggedIn");
            editor.commit();
            finish();

           // Log.e(TAG, "display name: " + acct.getDisplayName());

           // String personName = acct.getDisplayName();
           // String personPhotoUrl = acct.getPhotoUrl().toString();
           // String email = acct.getEmail();

         //   Log.e(TAG, "Name: " + personName + ", email: " + email
                //    + ", Image: " + personPhotoUrl);

            /*txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/

           // updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
           // updateUI(false);
        }
    }

    private void OnClicks() {

        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton1.performClick();
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().matches("")) {
                    if (!password.getText().toString().matches("")) {
                        prefManager = new PrefManager(Login.this);
                        if (!prefManager.isFirstTimeLaunch())
                             startActivity(new Intent(Login.this, HomeScreen.class));
                        else startActivity(new Intent(Login.this, WelcomeActivity.class));
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(Class_Genric.Sp_Status,"LoggedIn");
                        editor.commit();
                        finish();
                    } else password.setError("Field cannot be empty");
                } else username.setError("Field cannot be empty");
            }
        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, NewRegister.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        callbackManager.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        Class_Static.mGoogleApiClient.connect();
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(Class_Static.mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            //Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        Class_Static.mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.sourceedge.preco",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
