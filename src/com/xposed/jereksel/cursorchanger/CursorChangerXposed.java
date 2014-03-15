package com.xposed.jereksel.cursorchanger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedHelpers;
import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class CursorChangerXposed implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private static String MODULE_PATH = null;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, null);
        XResources.setSystemWideReplacement("android", "drawable", "pointer_arrow", modRes.fwd(R.drawable.note_2));
        XResources.setSystemWideReplacement("android", "drawable", "pointer_spot_anchor", modRes.fwd(R.drawable.note_2));
        XResources.setSystemWideReplacement("android", "drawable", "pointer_spot_hover", modRes.fwd(R.drawable.note_2));
        XResources.setSystemWideReplacement("android", "drawable", "pointer_spot_touch", modRes.fwd(R.drawable.note_2));
 
    }

	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("android"))
            return;
        
        findAndHookMethod("android.view.PointerIcon", lpparam.classLoader, "loadResource", Resources.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            	XposedHelpers.setIntField(param.thisObject, "mHotSpotX", 50);
            	XposedHelpers.setIntField(param.thisObject, "mHotSpotY", 50);
            }

    });
    }
}