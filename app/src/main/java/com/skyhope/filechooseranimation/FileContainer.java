package com.skyhope.filechooseranimation;

import android.graphics.drawable.Drawable;
/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 4/24/2019 at 12:13 PM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 4/24/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */


public class FileContainer {
    private Drawable appIcon;
    private String fileName;

    public FileContainer(Drawable appIcon, String fileName) {
        this.appIcon = appIcon;
        this.fileName = fileName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
