package com.aide.financial.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aide.financial.R;
import com.aide.financial.util.ScreenUtils;
import com.aide.financial.util.SizeUtils;
import com.aide.financial.widget.MCircularProgressBar;

/**
 * Created by maning on 2017/12/29.
 * 带进度条的Dialog
 */

public class MProgressBarDialog {

    // 水平进度条 or 圆环进度条
    public final static int STYLE_HORIZONTAL = 0;
    public final static int STYLE_CIRCLE = 1;

    private Context mContext;
    private Dialog mDialog;

    private MProgressBarDialog.Builder mBuilder;

    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private TextView tvShow;
    private ProgressBar horizontalProgressBar;
    private MCircularProgressBar circularProgressBar;

    public MProgressBarDialog(Context context) {
        this(context, new MProgressBarDialog.Builder(context));
    }

    public MProgressBarDialog(Context context, MProgressBarDialog.Builder builder) {
        mContext = context;
        mBuilder = builder;
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mn_progress_bar_dialog_layout, null);// 得到加载view
        mDialog = new Dialog(mContext, R.style.MNCustomDialog);// 创建自定义样式dialog
        mDialog.setCancelable(false);// 不可以用“返回键”取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mProgressDialogView);// 设置布局

        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = ScreenUtils.getScreenWidth(mContext);
        layoutParams.height = ScreenUtils.getScreenHeight(mContext);
        mDialog.getWindow().setAttributes(layoutParams);

        dialog_window_background = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_view_bg);
        tvShow = (TextView) mProgressDialogView.findViewById(R.id.tvShow);
        horizontalProgressBar = (ProgressBar) mProgressDialogView.findViewById(R.id.horizontalProgressBar);
        circularProgressBar = (MCircularProgressBar) mProgressDialogView.findViewById(R.id.circularProgressBar);

        horizontalProgressBar.setVisibility(View.GONE);
        circularProgressBar.setVisibility(View.GONE);

        horizontalProgressBar.setProgress(0);
        horizontalProgressBar.setSecondaryProgress(0);
        circularProgressBar.setProgress(0);
        tvShow.setText("");

        configView();

    }

    private void configView() {
        dialog_window_background.setBackgroundColor(mBuilder.backgroundWindowColor);
        tvShow.setTextColor(mBuilder.textColor);

        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(mBuilder.backgroundViewColor);
        myGrad.setStroke(SizeUtils.dp2px(mContext, mBuilder.strokeWidth), mBuilder.strokeColor);
        myGrad.setCornerRadius(SizeUtils.dp2px(mContext, mBuilder.cornerRadius));
        dialog_view_bg.setBackground(myGrad);

        //horizontalProgressBar 配置
        //背景
        GradientDrawable progressBarBackgroundDrawable = new GradientDrawable();
        progressBarBackgroundDrawable.setColor(mBuilder.progressbarBackgroundColor);
        progressBarBackgroundDrawable.setCornerRadius(SizeUtils.dp2px(mContext, mBuilder.progressCornerRadius));
        //二级进度条
        GradientDrawable secondProgressDrawable = new GradientDrawable();
        secondProgressDrawable.setColor(mBuilder.progressbarBackgroundColor);
        secondProgressDrawable.setCornerRadius(SizeUtils.dp2px(mContext, mBuilder.progressCornerRadius));
        ClipDrawable hProgressBar02 = new ClipDrawable(secondProgressDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //一级进度条
        GradientDrawable progressDrawable = new GradientDrawable();
        progressDrawable.setColor(mBuilder.progressColor);
        progressDrawable.setCornerRadius(SizeUtils.dp2px(mContext, mBuilder.progressCornerRadius));
        ClipDrawable hProgressBar03 = new ClipDrawable(progressDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        //组合
        Drawable[] layers = {progressBarBackgroundDrawable, hProgressBar02, hProgressBar03};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setId(0, android.R.id.background);
        layerDrawable.setId(1, android.R.id.secondaryProgress);
        layerDrawable.setId(2, android.R.id.progress);
        horizontalProgressBar.setProgressDrawable(layerDrawable);

        ViewGroup.LayoutParams layoutParams = horizontalProgressBar.getLayoutParams();
        layoutParams.height = SizeUtils.dp2px(mContext, mBuilder.horizontalProgressBarHeight);
        horizontalProgressBar.setLayoutParams(layoutParams);

        //circularProgressBar 配置
        circularProgressBar.setBackgroundColor(mBuilder.progressbarBackgroundColor);
        circularProgressBar.setColor(mBuilder.progressColor);
        circularProgressBar.setProgressBarWidth(SizeUtils.dp2px(mContext, mBuilder.circleProgressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(SizeUtils.dp2px(mContext, mBuilder.circleProgressBarBackgroundWidth));

    }

    public void showProgress(int progress, String message) {
        showProgress(progress, 0, message);
    }

    public void showProgress(int progress, int secondProgress, String message) {
        if (mBuilder.style == STYLE_HORIZONTAL) {
            if (horizontalProgressBar.getVisibility() == View.GONE) {
                horizontalProgressBar.setVisibility(View.VISIBLE);
            }
            horizontalProgressBar.setProgress(progress);
            horizontalProgressBar.setSecondaryProgress(secondProgress);
        } else {
            if (circularProgressBar.getVisibility() == View.GONE) {
                circularProgressBar.setVisibility(View.VISIBLE);
            }
            circularProgressBar.setProgress(progress);
        }
        tvShow.setText(message);
        mDialog.show();
    }

    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        } else {
            return false;
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void refreshBuilder(MProgressBarDialog.Builder builder) {
        mBuilder = builder;
        configView();
    }

    public static final class Builder {

        private Context mContext;

        //窗体背景色
        int backgroundWindowColor;
        //View背景色
        int backgroundViewColor;
        //View边框的颜色
        int strokeColor;
        //View背景圆角
        float cornerRadius;
        //View边框的宽度
        float strokeWidth;
        //文字的颜色
        int textColor;
        //Progressbar 背景色
        int progressbarBackgroundColor;
        //Progressbar 条颜色
        int progressColor;
        //水平进度条Progress圆角
        float progressCornerRadius;
        //style:0:水平，1:圆形
        int style;
        // CircleProgressbar宽度
        int circleProgressBarWidth;
        int circleProgressBarBackgroundWidth;
        // horizontalProgressBar 宽度
        int horizontalProgressBarHeight;

        public Builder(Context context) {
            mContext = context;
            //默认配置
            backgroundWindowColor = mContext.getResources().getColor(R.color.mn_colorDialogWindowBg);
            backgroundViewColor = mContext.getResources().getColor(R.color.mn_colorDialogViewBg);
            strokeColor = mContext.getResources().getColor(R.color.mn_colorDialogTrans);
            textColor = mContext.getResources().getColor(R.color.mn_colorDialogTextColor);
            cornerRadius = 6;
            strokeWidth = 0;
            progressbarBackgroundColor = mContext.getResources().getColor(R.color.mn_colorDialogProgressBarBgColor);
            progressColor = mContext.getResources().getColor(R.color.mn_colorDialogProgressBarProgressColor);
            progressCornerRadius = 2;
            style = STYLE_HORIZONTAL;
            circleProgressBarWidth = 3;
            circleProgressBarBackgroundWidth = 1;
            horizontalProgressBarHeight = 4;
        }

        public MProgressBarDialog build() {
            return new MProgressBarDialog(mContext, this);
        }


        public Builder setBackgroundWindowColor(@Nullable int backgroundWindowColor) {
            this.backgroundWindowColor = backgroundWindowColor;
            return this;
        }

        public Builder setBackgroundViewColor(@Nullable int backgroundViewColor) {
            this.backgroundViewColor = backgroundViewColor;
            return this;
        }

        public Builder setStrokeColor(@Nullable int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder setStrokeWidth(@Nullable float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder setCornerRadius(@Nullable float cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setTextColor(@Nullable int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setProgressbarBackgroundColor(@Nullable int progressbarBackgroundColor) {
            this.progressbarBackgroundColor = progressbarBackgroundColor;
            return this;
        }

        public Builder setProgressColor(@Nullable int progressColor) {
            this.progressColor = progressColor;
            return this;
        }

        public Builder setProgressCornerRadius(@Nullable int progressCornerRadius) {
            this.progressCornerRadius = progressCornerRadius;
            return this;
        }

        public Builder setStyle(@Nullable int style) {
            this.style = style;
            return this;
        }

        public Builder setCircleProgressBarWidth(@Nullable int circleProgressBarWidth) {
            this.circleProgressBarWidth = circleProgressBarWidth;
            return this;
        }

        public Builder setCircleProgressBarBackgroundWidth(@Nullable int circleProgressBarBackgroundWidth) {
            this.circleProgressBarBackgroundWidth = circleProgressBarBackgroundWidth;
            return this;
        }

        public Builder setHorizontalProgressBarHeight(@Nullable int horizontalProgressBarHeight) {
            this.horizontalProgressBarHeight = horizontalProgressBarHeight;
            return this;
        }

    }

}
