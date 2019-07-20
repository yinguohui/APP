package com.example.ygh.app.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class OkHttpUtil {

    private static OkHttpClient okHttpClient = null;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null)
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/MovieMan"), 1024 * 1024 * 8))
                            .build();
            }
        }
        return okHttpClient;
    }

    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        getInstance().newCall(request).enqueue(callback);
    }

    public static void doPost(String url, Map<String, String> map, Callback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        for (String key : map.keySet()) {
            formBody.add(key, map.get(key));
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        getInstance().newCall(request).enqueue(callback);
    }

    public static void doPost(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("\"application/json; charset=utf-8\""), json);
        Request request = new Request.Builder().url(url).post(body).build();
        getInstance().newCall(request).enqueue(callback);
    }

    public static void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress, sum, total);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    public static void upload(String url, String path, String fileName,String userid, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(path));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userid",userid)
                .addFormDataPart("create",fileName,
                        RequestBody.create(MEDIA_TYPE, new File(path)));
        //发出请求参数
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        getInstance().newCall(request).enqueue(callback);
    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * 从下载连接中解析出文件名
     */
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {

        void onDownloadSuccess();

        void onDownloading(int progress, long current, long total);

        void onDownloadFailed();
    }

    public static void uploadmany(String url, Map<String,String> map, List<String> files, Callback callback) {
        //map ---文件路径：文件名
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (map!=null){
            for (Map.Entry m:map.entrySet()) {
               builder.addFormDataPart(m.getKey().toString(),m.getValue().toString());
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“create”作为后台接受多张图片的key
        if (files != null){
            for (String file : files) {
                builder.addFormDataPart("create", file, RequestBody.create(MEDIA_TYPE_PNG, new File(file)));
            }
        }

        //发出请求参数
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        getInstance().newCall(request).enqueue(callback);
    }
}

