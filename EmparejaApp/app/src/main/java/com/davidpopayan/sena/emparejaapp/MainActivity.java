package com.davidpopayan.sena.emparejaapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Button btnI;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

       // printKeyHash();
        inicializar();
        btnI = findViewById(R.id.btnI);



        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setQuote("")
                        .setContentUrl(Uri.parse(""))
                        .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("")
                        .build())
                        .build();

                if (shareDialog.canShow(ShareLinkContent.class)){

                    shareDialog.show(content);
                }


            }
        });


    }

    private void inicializar() {
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

    }

    private void printKeyHash() {


        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.davidpopayan.sena.emparejaapp",PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures){

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

    }
}
