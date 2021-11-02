package com.example.arvoregenealogica.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

    //lista de informações
    public static List<PackageInfo> getInstallApkList(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.PERMISSION_GRANTED);

        if (packages != null && packages.size() > 0) {
            return packages;
        } else {
            return new ArrayList<>();
        }
    }

    public static void startUninstall(Context context, final String pkg) {
        Uri packageURI = Uri.parse("package:" + pkg);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    private static final String SCHEME = "package";

    private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";

    private static final String APP_PKG_NAME_22 = "pkg";

    private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";

    private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

    public static void showInstalledAppDetails(Context context, String packageName, int... requestCode) {
        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);
        } else {
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, packageName);
        }

        if (requestCode.length > 0) {
            ((Activity) context).startActivityForResult(intent, requestCode[0]);
        } else {
            context.startActivity(intent);
        }

    }
}
