package com.example.ygh.app.pro.publish.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ygh.app.R;
import com.example.ygh.app.pro.mine.view.ModifySexActivity;
import com.example.ygh.app.util.Bimp;
import com.example.ygh.app.util.FileUtils;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//发布话题
public class PublishedActivity extends AppCompatActivity implements View.OnClickListener{

	//上传图片
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private ImageView activity_img_exit;
	private Map<String,String> map = new HashMap<>();
	//编辑内容
	private EditText et_content;
	//发布
	private TextView activity_selectimg_send;
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 4:
                    ToastUtil.showToast(PublishedActivity.this,"发布失败");
                    break;
                case 2:
                    ToastUtil.showToast(PublishedActivity.this,"发布成功");
                    et_content.setText("");
                    finish();
                    break;

            }
        }
    };

	//初始化
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
		Init();
	}


	public void Init() {
		activity_img_exit = findViewById(R.id.activity_img_exit);
		activity_img_exit.setOnClickListener(this);
		et_content = findViewById(R.id.et_content);
		noScrollgridview = findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(PublishedActivity.this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		activity_selectimg_send =  findViewById(R.id.activity_selectimg_send);
		activity_selectimg_send.setOnClickListener(this);
		map.put("id",SpUtil.getString(PublishedActivity.this,"userid",""));
	}

	//发布点击按钮事件
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.activity_selectimg_send:
				showNormalDialog();
				break;
			case R.id.activity_img_exit:
				Bimp.bmp.clear();
				Bimp.max = 0;
				Bimp.drr.clear();
                adapter.notifyDataSetChanged();
				finish();
				break;
		}
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	//底部弹窗
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

//			Button bt1 = (Button) view
//					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
//			bt1.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					photo();
//					dismiss();
//				}
//			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishedActivity.this,
							TestPicActivity.class);
					startActivity(intent);
//					finish();
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

//	private static final int TAKE_PICTURE = 0x000000;
//	private String path = "";
//	public void photo() {
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File file = new File(Environment.getExternalStorageDirectory()
//				+ "/myimage/", String.valueOf(System.currentTimeMillis())
//				+ ".jpg");
//		path = file.getPath();
//		Uri imageUri;
//		if (Build.VERSION.SDK_INT >= 24) {
//			imageUri = FileProvider.getUriForFile(PublishedActivity.this, "com.example.ygh.app.fileprovider", file);
//		} else {
//			imageUri = Uri.fromFile(file);
//		}
//		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);
//	}

//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		case TAKE_PICTURE:
//			if (Bimp.drr.size() < 9 && resultCode == -1) {
//				Bimp.drr.add(path);
//			}
//			break;
//		}
//	}
	private void showNormalDialog(){
		/* @setIcon 设置对话框图标
		 * @setTitle 设置对话框标题
		 * @setMessage 设置对话框消息提示
		 * setXXX方法返回Dialog对象，因此可以链式设置属性
		 */
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(PublishedActivity.this);
		normalDialog.setTitle("发布主题");
		normalDialog.setMessage("确定发布内容呢?");
		normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						upDataTitle();
					}
				});
		normalDialog.setNegativeButton("关闭",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		// 显示
		normalDialog.show();
	}
	private  void upDataTitle(){
		String content = et_content.getText().toString();
		if (content.equals("")){
			ToastUtil.showToast(PublishedActivity.this,"内容不能为空");
			return;
		}
		map.put("content",content);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		List<String> list = new ArrayList<String>();
		List<byte[]> imglist = new ArrayList<>();
		for (int i = 0; i < Bimp.drr.size(); i++) {
			String Str = Bimp.drr.get(i).substring(
					Bimp.drr.get(i).lastIndexOf("/") + 1,
					Bimp.drr.get(i).lastIndexOf("."));
			list.add(FileUtils.SDPATH+Str+".JPEG");
			Bimp.bmp.get(i).compress(Bitmap.CompressFormat.PNG, 100, baos);
			imglist.add(baos.toByteArray());
		}
		// 高清的压缩图片全部就在  list 路径里面了
		// 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
		// 完成上传服务器后 .........
		OkHttpUtil.uploadmany("http://192.168.43.240:8080/title/test", map, list, new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
                handler.sendEmptyMessage(4);
			}
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()){
				    handler.sendEmptyMessage(2);
					Bimp.bmp.clear();
					Bimp.max = 0;
					Bimp.drr.clear();
				}else {
					handler.sendEmptyMessage(4);
				}
			}
		});
	}
}
