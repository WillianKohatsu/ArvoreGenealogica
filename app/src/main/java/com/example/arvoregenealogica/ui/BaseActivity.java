package com.example.arvoregenealogica.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.arvoregenealogica.interfaces.ActivityListener;
import com.example.arvoregenealogica.ui.dialog.DefaultAlertDialog;
import com.example.arvoregenealogica.utils.AppManager;
import com.example.arvoregenealogica.utils.DisplayUtil;
import com.example.arvoregenealogica.utils.ToastMaster;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends Activity implements ActivityListener {

    private static final int REQUEST_TO_SETTING = 0;//跳转到系统设置权限页面

    protected String[] permissions = {};//需要请求的权限
    protected String[] refuseTips = {};//拒绝请求后的对话框提示

    private InputMethodManager manager;

    private boolean curIsShow = false;

    private DefaultAlertDialog permissionDialog;//获取权限对话框
    private int permissionPosition = 0;//当前请求的权限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.i(this.getLocalClassName());

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        setStatusBar();
    }

    @Override
    protected void onDestroy() {
        //Remover monitoramento de mudança de layout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutChangeListener);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(mLayoutChangeListener);
        }
        if (permissionDialog != null) {
            permissionDialog.dismissDialog();
        }
        super.onDestroy();
    }

    //Imersao
    private void setStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        window.getDecorView().setFitsSystemWindows(true);
    }

    //隐藏虚拟按键，并且全屏
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    //键盘显示隐藏回调
    protected void setOnKeyboardChangeListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
    }

    //layout改变监听
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

            int screenHeight = DisplayUtil.getScreenHeight();
            int heightDifference = screenHeight - (r.bottom - r.top);

            boolean isShow = heightDifference > screenHeight / 3;

            if (((!curIsShow && isShow) || curIsShow && !isShow)) {
                onkeyboardChange(isShow);
                curIsShow = isShow;
            }
        }
    };

    protected void setPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPermissionSuccess();
        } else {
            List<String> pTemp = new ArrayList<>();
            List<String> tTemp = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    pTemp.add(permission);
                    tTemp.add(refuseTips[i]);
                }
            }

            permissions = pTemp.toArray(new String[pTemp.size()]);
            refuseTips = tTemp.toArray(new String[tTemp.size()]);

            requestPermissions(0);
        }
    }

    private void requestPermissions(int index) {
        if (permissions.length > 0 && index >= 0 && index < permissions.length) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[index]}, index);
        } else if (permissions.length == 0 || index >= permissions.length) {
            onPermissionSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, String[] p, int[] grantResults) {
        if (grantResults != null && grantResults.length > 0) {
            permissionPosition = requestCode;
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (requestCode < refuseTips.length) {
                    permissionDialog = new DefaultAlertDialog(this);
                    permissionDialog.setTitle("Pedido de acesso");
                    permissionDialog.setMessage(refuseTips[requestCode]);
                    permissionDialog.setConfirmButton("Va para definir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppManager.showInstalledAppDetails(BaseActivity.this, getPackageName(), REQUEST_TO_SETTING);
                        }
                    });
                    permissionDialog.setCancelButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ToastMaster.toast("Sem permissao");
                        }
                    });
                } else {
                    ToastMaster.toast("Sem permissao");
                }
                permissionDialog.showDialog();
            } else {
                int nextRequest = permissionPosition + 1;
                requestPermissions(nextRequest);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_TO_SETTING == requestCode) {
            if (permissionPosition < permissions.length) {
                if (ContextCompat.checkSelfPermission(this, permissions[permissionPosition]) != PackageManager.PERMISSION_GRANTED) {
                    ToastMaster.toast("Sem perimssao");
                } else {
                    onPermissionSuccess();
                }
            }
        }
    }

    @Override
    public void onkeyboardChange(boolean isShow) {
    }

    @Override
    public void onPermissionSuccess() {
    }
}
