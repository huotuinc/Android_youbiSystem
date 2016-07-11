package com.huotu.youbi.ui.splash;

import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.youbi.R;
import com.huotu.youbi.config.Contant;
import com.huotu.youbi.model.BaseModel;
import com.huotu.youbi.ui.base.BaseActivity;
import com.huotu.youbi.ui.guide.GuideActivity;
import com.huotu.youbi.utils.ActivityUtils;
import com.huotu.youbi.utils.AuthParamUtils;
import com.huotu.youbi.utils.GsonRequest;
import com.huotu.youbi.utils.ToastUtils;
import com.huotu.youbi.utils.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_iv)
    ImageView splashIv;

    @Override
    protected void initData() {
        startAnimation();
        final String url = Contant.REQUEST_URL;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(),this);
        Map<String, Object> maps = new HashMap<>();
        maps.put("action","init");
        Map<String, Object> param = params.obtainPostParam(maps);
        GsonRequest<BaseModel> gsonRequest = new GsonRequest(
                Request.Method.POST,
                url,
                BaseModel.class,
                param,
                new Response.Listener<BaseModel>() {
                    @Override
                    public void onResponse(BaseModel response) {
                        BaseModel base = response;
                        if (1 == base.getResultCode()) {
                            ToastUtils.showLongToast(SplashActivity.this,base.getResultDescription());
                            goNextUi();
                        }
                        else {
                            //上传失败
                            ToastUtils.showLongToast(SplashActivity.this,base.getResultDescription());
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        VolleyUtil.addRequest(gsonRequest);



}




    @Override
    protected void initTitle() {

    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    private void startAnimation() {
        /*// 动画集合，一起播放
        AnimationSet animationSet = new AnimationSet(true);

        // 缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(Contant.ANIMATION_DURATION);
        animationSet.addAnimation(scaleAnimation);


        // 透明度的动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(Contant.ANIMATION_DURATION);
        animationSet.addAnimation(alphaAnimation);*/
        AlphaAnimation anima = new AlphaAnimation(0.0f, 1.0f);
        anima.setDuration(Contant.ANIMATION_DURATION);// 设置动画显示时间
        splashIv.setAnimation(anima);
        anima.setAnimationListener(animationListener);
    }

    static Handler handler = new Handler();
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            handler.postDelayed(goNextUiRunnable, 2000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
//    private Runnable goNextUiRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//            goNextUi();
//        }
//    };

    private void goNextUi() {
        ActivityUtils.getInstance().skipActivity(this, GuideActivity.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 防止再弹出主界面
//        handler.removeCallbacks(goNextUiRunnable);
    }


}
