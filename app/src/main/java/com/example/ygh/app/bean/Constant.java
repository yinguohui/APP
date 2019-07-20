package com.example.ygh.app.bean;

import com.example.ygh.app.util.FileUtils;

import java.io.File;

public class Constant {
    public static final String APP_NAME   = "PictureSelector";//app名称
    public static final String BASE_DIR   =  APP_NAME + File.separator;//PictureSelector/
    public static final String DIR_ROOT   = FileUtils.getRootPath() + File.separator + Constant.BASE_DIR;//文件夹根目录 /storage/emulated/0/PictureSelector/

    public static final int CANCEL = 0;//取消
    public static final int CAMERA = 1;//拍照
    public static final int ALBUM  = 2;//相册
}

