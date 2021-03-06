package com.kingz.newfeatures;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kingz.customdemo.R;

@TargetApi(Build.VERSION_CODES.O)
public class FadeAnim extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_fade_anim);
    }
}
