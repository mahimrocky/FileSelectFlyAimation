package com.skyhope.filechooseranimation;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
    public void onGetItem(ImageView imageViewAnim, View mainView, Drawable ic) {
        final ImageView dummyImage = findViewById(R.id.image_view_dymmy);

        dummyImage.setVisibility(View.VISIBLE);


        Rect rectf = new Rect();
        imageViewAnim.getGlobalVisibleRect(rectf);

        dummyImage.setLeft(mainView.getLeft());
        dummyImage.setTop(mainView.getTop());
        dummyImage.setBottom(mainView.getBottom());
        mainView.setRight(mainView.getRight());

        Log.d("AnimationTest", "x: " + imageViewAnim.getX() + " y: " + imageViewAnim.getY());

        dummyImage.setImageDrawable(ic);

        dummyImage.bringToFront();

        float destX = mButton.getWidth();
        float destY = mButton.getHeight();
        float originX = dummyImage.getWidth();
        float originY = dummyImage.getHeight();

        final float endRadius = Math.max(destX, destY) / 2;
        final float startRadius = Math.max(originX, originY);
        float scaleFactor = 0.5f;

        AnimationSet set = new AnimationSet(true);

        int[] src = new int[2];
        dummyImage.getLocationOnScreen(src);

        int[] dest = new int[2];
        mButton.getLocationOnScreen(dest);

        float y = dummyImage.getY();
        float x = dummyImage.getX();

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

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dummyImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        dummyImage.startAnimation(set);
    }
}
