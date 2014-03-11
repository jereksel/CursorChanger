package com.xposed.jereksel.cursorchanger;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class CursorChanger implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
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

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {

        if (!resparam.packageName.equals("com.liveov.skm"))
            return;

        XModuleResources modRes2 = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.liveov.skm", "drawable", "pointer_arrow", modRes2.fwd(R.drawable.note_2));
        XposedBridge.log("TEST_2");
	}
}