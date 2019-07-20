package com.example.ygh.app.pro.publish.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.ygh.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener {
    private String url = "http://172.16.52.17:8080/UploadDemo4/UploadFile";
    private Button get_photo;
    private GridView gv;
    private List<Bitmap> imageUrl;
    private Bitmap bm;
//    private MyAdapter myAdapter;
    private Button up_load;
    private Intent intent;
    private String imagePath;
    private List<String> list;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtitle);
        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
//        myAdapter = new MyAdapter(imageUrl, bm, this);
//        gv.setAdapter(myAdapter);
    }

    private void initData() {
        imageUrl = new ArrayList<>();
        list = new ArrayList<>();
    }

    private void initView() {
        get_photo = (Button) findViewById(R.id.get_photo);
        get_photo.setOnClickListener(this);
        gv = (GridView) findViewById(R.id.gv);
        up_load = (Button) findViewById(R.id.up_load);
        up_load.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_photo:
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
            case R.id.up_load:
                upLoad(list);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                imagePath = c.getString(columnIndex);
                showImage(imagePath);
                c.close();
                break;

        }
    }

    private void upLoad(List<String> list) {
//        if (list != null) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            for (int i = 0; i < list.size(); i++) {
//
//                file = new File(list.get(i));
//
//                MultipartBody.Builder builder = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("img", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
//                RequestBody requestBody = builder.build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(requestBody)
//                        .build();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.e("TAG", "onFailure: " + e);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.e("TAG", "成功" + response);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//            }
//        }
    }

    private void showImage(String imagePath) {
        list.add(imagePath);
        bm = BitmapFactory.decodeFile(imagePath);
        Log.e("TAG", "showImage:---- " + imagePath);
        imageUrl.add(bm);
//        myAdapter.notifyDataSetChanged();
    }
}
