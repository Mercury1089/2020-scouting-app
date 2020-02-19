package com.mercury1089.scoutingapp2019;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        /*
        ObjectAnimator anim = new ObjectAnimator().ofFloat(developersText, View.ALPHA, 1.0f, .75f);
        anim.setDuration(500);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(anim);
        animatorSet.start();
        */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConstraintLayout constraintLayout = findViewById(R.id.SplashActivity);
                ImageView lightningBolt = findViewById(R.id.LightningBolt);
                TextView developersText = findViewById(R.id.CreditWhereCreditsDue);

                int lightningBoltSpeed = 200;

                ObjectAnimator animatorX = ObjectAnimator.ofFloat(lightningBolt, View.X, 100, 200).setDuration(lightningBoltSpeed);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(lightningBolt, View.Y, -200, 300).setDuration(lightningBoltSpeed);
                ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(lightningBolt, View.ALPHA, 0, 1).setDuration(lightningBoltSpeed);
                ObjectAnimator animatorScreenAlphaOff = ObjectAnimator.ofFloat(constraintLayout, View.ALPHA, 1, 0).setDuration(100);
                ObjectAnimator animatorTextAlpha = ObjectAnimator.ofFloat(developersText, View.ALPHA, 0, .5f).setDuration(0);
                ObjectAnimator animatorScreenAlphaOn = ObjectAnimator.ofFloat(constraintLayout, View.ALPHA, 0, 1).setDuration(100);

                ObjectAnimator anim = ObjectAnimator.ofFloat(developersText, View.ALPHA, .5f, 1.0f).setDuration(500);
                anim.setRepeatCount(1);
                anim.setRepeatMode(ObjectAnimator.REVERSE);

                AnimatorSet lightningAnimation = new AnimatorSet();
                lightningAnimation.playTogether(animatorX, animatorY, animatorAlpha);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(lightningAnimation, animatorScreenAlphaOff, animatorTextAlpha, animatorScreenAlphaOn, anim);

                animatorSet.start();
            }
        }, 500);

        Handler handler1 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // After t seconds redirect to another intent
                Intent intent = new Intent(SplashActivity.this, PregameActivity.class);
                startActivity(intent);

                //Remove activity
                finish();
            }
        }, 2000);
    }
}
