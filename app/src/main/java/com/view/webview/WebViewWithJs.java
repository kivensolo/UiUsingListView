package com.view.webview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.kingz.customdemo.R;
import com.utils.ToastTools;
import com.utils.ZLog;

import org.json.JSONObject;

/**
 * Copyright(C) 2015, 北京视达科科技有限公司
 * All rights reserved.
 * author: King.Z
 * date:  2016/9/28 22:20
 * description:
 */
public abstract class WebViewWithJs extends WebView {

    private Context mContext;
    private String mNameOfJsCall = "JsExtApollo";
    private JsExtObject mJsExtObject = new JsExtObject();

    private Handler viewHandler = new Handler();


    public WebViewWithJs(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public WebViewWithJs(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public WebViewWithJs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setScaleX(1.0f);
        setScaleY(1.0f);
        getSettings().setJavaScriptEnabled(true);              //Enabling JavaScript
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setDefaultFontSize(30);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(false);
        //设置缩放
        inittialScale();

        setWebViewClient(new ZWebViewClient(){
            @Override
            public void onWebReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        addJavascriptInterface(mJsExtObject, mNameOfJsCall);  //Binding JavaScript code to Android code
        setBackgroundColor(getResources().getColor(R.color.lightslategray));
    }

    /**
     * 这里需要根据不同的分辨率设置不同的比例,比如
     * 5寸手机设置190  屏幕宽度 > 650   180
     * 4.5寸手机设置170  屏幕宽度>  500 小于 650  160
     * 4寸手机设置150  屏幕宽度>  450 小于 550  150
     * 3           屏幕宽度>  300 小于 450  120
     * 小于    300  100
     * 320×480  480×800 540×960 720×1280
     */
    private void inittialScale() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if (width > 650) {
            this.setInitialScale(190);
        } else if (width > 520) {
            this.setInitialScale(160);
        } else if (width > 450) {
            this.setInitialScale(140);
        } else if (width > 300) {
            this.setInitialScale(120);
        } else {
            this.setInitialScale(100);
        }
    }


    private class JsExtObject {
        public static final String TAG = "JsExtObject";
        private Context context;

        public JsExtObject() {
        }

        @JavascriptInterface
        public void log(String tag, String info) {
            Log.i(tag, info);
        }

        // If set targetSdkVersion to 17 or higher,
        // Must add the @JavascriptInterface annotation.
        @JavascriptInterface
        public void showToastByJs(String msg) {
            ZLog.i(TAG, "showToastByJs() msg=" + msg);
            ToastTools.getInstance().showMgtvWaringToast(context, msg);
        }

        @JavascriptInterface
        public void requestFocus() {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    setFocusable(true);
                    requestFocus();
                }
            });
        }

        // direction: 将焦点释放给当前webview在某个方向上的相邻元素，如无相邻元素，则焦点不变化
        public void releaseFocus(String direction) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    clearFocus();
                }
            });
        }

        /**
         * 发送JSONObject类型的信息
         */
        public void sendMessage(final String msg, final JSONObject extInfo) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    _internalOnReceiveMessage(msg, extInfo);
                }
            });
        }

        /**
         * 发送String类型的信息
         */
        public void sendMessage(final String msg, final String extInfo) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    _internalOnReceiveMessage(msg, extInfo);
                }
            });
        }

        public void closeBrowser(final String reason) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    onCloseBrowser(reason);
                }
            });
        }

        // 调整浏览器尺寸，调整后，浏览器保持在屏幕上居中位置
        public void resizeBrowser(final double width, final double height) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    onResizeBrowser(width, height);
                }
            });
        }

        public void moveBrowser(final double x, final double y) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    onMoveBrowser(x, y);
                }
            });
        }

        public void moveBrowserEx(final double x, final double y, final double width, final double height) {
            viewHandler.post(new Runnable() {
                @Override
                public void run() {
                    onMoveBrowser(x, y, width, height);
                }
            });
        }

        public String getBrowserPosition() {
            Rect rectInScreen = new Rect();
            getGlobalVisibleRect(rectInScreen);
            return String.format("%d,%d,%d,%d", rectInScreen.left, rectInScreen.top, rectInScreen.width(), rectInScreen.height());
        }
    }

    private void _internalOnReceiveMessage(String msg, Object info) {
        this.onReceiveMessage(msg, info);
    }

    //    public abstract void onSetHandler(String handlerName, JSCallback callback);
    public abstract void onReceiveMessage(String msg, Object info);
    public abstract void onCloseBrowser(String reason);
    public abstract void onShowBrowser(double width, double height);
    public abstract void onResizeBrowser(double width, double height);
    public abstract void onMoveBrowser(double x, double y);
    public abstract void onMoveBrowser(double x, double y, double width, double height);
}