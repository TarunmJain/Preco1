package com.sourceedge.preco.homescreen.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.sourceedge.preco.R;
import com.sourceedge.preco.history.controller.History;
import com.sourceedge.preco.homescreen.view.ServicesAdapter;
import com.sourceedge.preco.login.controller.Login;
import com.sourceedge.preco.payment.controller.AddPrecoPoints;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.Class_SyncApi;
import com.sourceedge.preco.support.MyApplication;

public class HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    CoordinatorLayout coordinatorLayout;
    RelativeLayout addPointLayout, historyLayout;
    static Activity a;
    static SharedPreferences sharedPreferences;
    static Button button, button1;
    static GoogleApiClient mGoogleApiClient;
    Dialog dialog;
    CheckBox blackAndWhiteCheckbox, colorCheckbox;
    LinearLayout blackAndWhiteColor, colorType;
    TextView colorConfirm;
    static RecyclerView servicesRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Class_Genric.requestStorage(HomeScreen.this);
        Class_Genric.requestCamera(HomeScreen.this);
        if (!Class_Genric.checkLocationPermission(HomeScreen.this)) {
            Class_Genric.requestPermission(HomeScreen.this);
        }
        Class_Genric.turnGPSOn(HomeScreen.this);
        Class_SyncApi.ServiceApi(HomeScreen.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar.setTitle("Preco");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, HomeScreen.this);
        addPointLayout = (RelativeLayout) findViewById(R.id.addpointlay);
        historyLayout = (RelativeLayout) findViewById(R.id.historylay);
        servicesRecyclerview=(RecyclerView)findViewById(R.id.services_recyclerview);

        mGoogleApiClient = ((MyApplication) getApplication()).getGoogleApiClient(HomeScreen.this, this);
        OnClicks();
        Class_Genric.DrawerOnClicks(HomeScreen.this);

    }

    public static void InitializeAdapter(Context context) {
        servicesRecyclerview.setLayoutManager(new GridLayoutManager(context,2));
        servicesRecyclerview.setAdapter(new ServicesAdapter(context));
    }

    private void OnClicks() {
        /*print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REVERT BACK
                Class_Static.isCopyScan = false;
                dialog = new Dialog(HomeScreen.this);
                dialog.setContentView(R.layout.dialog_copies);
                blackAndWhiteCheckbox = (CheckBox) dialog.findViewById(R.id.black_and_white_checkbox);
                colorCheckbox = (CheckBox) dialog.findViewById(R.id.color_checkbox);
                blackAndWhiteColor = (LinearLayout) dialog.findViewById(R.id.black_and_white_color);
                colorType = (LinearLayout) dialog.findViewById(R.id.color_type);
                colorConfirm = (TextView) dialog.findViewById(R.id.color_confirm);
                dialog.show();

                blackAndWhiteColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        blackAndWhiteCheckbox.setChecked(true);
                        colorCheckbox.setChecked(false);
                    }
                });

                colorType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        blackAndWhiteCheckbox.setChecked(false);
                        colorCheckbox.setChecked(true);
                    }
                });

                blackAndWhiteCheckbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        blackAndWhiteCheckbox.setChecked(true);
                        colorCheckbox.setChecked(false);
                    }
                });

                colorCheckbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        blackAndWhiteCheckbox.setChecked(false);
                        colorCheckbox.setChecked(true);
                    }
                });

                colorConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(HomeScreen.this, MapLocation.class));
                        dialog.dismiss();
                    }
                });
            }
        });*/

        /*copyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.isCopyScan = false;
                startActivity(new Intent(HomeScreen.this, Scan.class));
            }
        });*/

        /*bookXerox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.isCopyScan = false;
                startActivity(new Intent(HomeScreen.this, Copy.class));
            }
        });

        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, Forms.class));
            }
        });*/

        addPointLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, AddPrecoPoints.class));
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, History.class));
            }
        });
    }

    public static void logout(final Context context) {
        FacebookSdk.sdkInitialize(context);
        a = ((Activity) context);
        sharedPreferences = a.getSharedPreferences(Class_Genric.MyPref, a.MODE_PRIVATE);
        final Dialog dialog = new Dialog(a);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_alert);
        button = (Button) dialog.findViewById(R.id.btn);
        button1 = (Button) dialog.findViewById(R.id.btn1);
        dialog.show();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Class_Static.isGoogleSignIn) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    Class_Static.isGoogleSignIn = false;
                                }
                            });
                } else if (Class_Static.isFacebookSignIn) {
                    Class_Static.isFacebookSignIn = false;
                    LoginManager.getInstance().logOut();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                a.startActivity(new Intent(a, Login.class));
                a.finish();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
