package com.skyhope.filechooseranimation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FileSelectAnimationAdapter.ItemClickListener {

    private FileSelectAnimationAdapter mAdapter;
    RecyclerView mRecyclerView;
    Button mButton;
    private WeakReference<Activity> mContextReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mRecyclerView = findViewById(R.id.recycler_view);
        mButton = findViewById(R.id.button);

        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        List<FileContainer> fileContainers = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {

            Drawable drawable = packageInfo.loadIcon(getPackageManager());
            String appName = packageInfo.loadLabel(getPackageManager()).toString();

            fileContainers.add(new FileContainer(drawable, appName));
        }

        mAdapter = new FileSelectAnimationAdapter(this, this);


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addItems(fileContainers);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, com.skyhope.flytocartanimation.MainActivity.class));
            }
        });
    }

    @Override
    public void onGetItem(ImageView imageViewAnim, Drawable drawable) {
        imageViewAnim.setVisibility(View.VISIBLE);

        imageViewAnim.bringToFront();

        float destX = mButton.getWidth();
        float destY = mButton.getHeight();
        float originX = imageViewAnim.getWidth();
        float originY = imageViewAnim.getHeight();

        final float endRadius = Math.max(destX, destY) / 2;
        final float startRadius = Math.max(originX, originY);
        float scaleFactor = 0.5f;

        AnimationSet set = new AnimationSet(true);

        int[] src = new int[2];
        imageViewAnim.getLocationOnScreen(src);

        int[] dest = new int[2];
        mButton.getLocationOnScreen(dest);

        float y = imageViewAnim.getY();
        float x = imageViewAnim.getX();

        TranslateAnimation translateAnimation = new TranslateAnimation(x,
                x + dest[0] - (src[0] + (originX * scaleFactor - 2 * endRadius * scaleFactor) / 2) + (0.5f * destX - scaleFactor * endRadius),
                y, y + dest[1] - (src[1] + (originY * scaleFactor - 2 * endRadius * scaleFactor) / 2) + (0.5f * destY - scaleFactor * endRadius));

        translateAnimation.setFillAfter(true);
        translateAnimation.setFillEnabled(true);

        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        translateAnimation.setDuration(1000);

        set.addAnimation(translateAnimation);


        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.4f);
        alphaAnimation.setDuration(1000);
        set.addAnimation(alphaAnimation);


        imageViewAnim.startAnimation(set);
    }
}
