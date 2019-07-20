package com.example.ygh.app.pro.mine.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.ygh.app.R;
import com.example.ygh.app.bean.AuthResult;
import com.example.ygh.app.bean.PayResult;
import com.example.ygh.app.bean.Result;
import com.example.ygh.app.bean.UserResult;
import com.example.ygh.app.pro.mine.view.adapter.TitleAdapter;
import com.example.ygh.app.util.OkHttpUtil;
import com.example.ygh.app.util.OrderInfoUtil2_0;
import com.example.ygh.app.util.SpUtil;
import com.example.ygh.app.util.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/*
* 我的钱包
* */

/**
 *  重要说明:
 *
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class WalletActivity extends AppCompatActivity implements View.OnClickListener{
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016092600598804";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088102177315052";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "wudora1106@sandbox.com";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC6uL+jtJRKbDCjVzHkGh0Y42DukjNKJIiEkdT5+6G9vgRuUFvofBB8Zo5Q/m1vSsDx1zzKNCbbpjewTNdk6VxIsZ0+ZWfvhuF3OIfsXp+Q/WoiQ7uu5eSUFs7NdbOKDdpymlPYo1SdyMXj+MKgUEmLjl+bJC1kVxeyLALjvb6zaQ5De0jAYuqFXFctIwfMdG8nFSBcMx6+kPwx0pB+WpHuDpcV68VmtXvoKBY0JnXpf8HzzhtDXPsuzPCUlQHDAbbrIU05Z1bEMLVtMlbrX0AVGJh0c/TqP4gUPCn8ZYjpcwZ2qEu7HSph6GXJbglY/VYThVIoKrHkMYUlKWclmAvBAgMBAAECggEBAILpHJMt+/nhuIgMjhIarOPSJxecsRD+ZChuBh/t6AwbuQeOl6F7uiM90/G9MASjKHtmKFysC8Jb2xdkCG/bnLPqsxAYELLCojjwDXwPK1iAE3VKf7+ztnqNMxihuOrFo65NvRNaD+0/4l5e/jVflPCfNTCUQK7k0Nw+Nxhs49LsVU+5oFdj1RtoJQ73PoFBfyWn9diX8LlW2q/4jcu8nh/zTBhUDpIRBA9Q1aJMDYXT1WWaKjeWkN3sHD3UAT9wirr56XF0nnvgG583mPDJDH+M9XjdlLjj2AIabWy4qRmK6i/+/6HcnhZolHtWbmbN8SKWT7gU/8b4XpMjPOVmArECgYEA7YMo4bzcXW3TLGAp16J+TZhUjAVGTHX6TNHmYDTqCd3+rIr9DHd64XmNmCNzkvnldchF3OPKnSNfQW2ACFmZ7NCbmUazJHCpPrM2pW/lVO/E8QPOD8yuScVYpUITj77XJaGnl8BcyOvH4XZPbOwSNWpTmQ8e2H3fIUWnd72ElIsCgYEAyUF/cTFd9yfR6AOpZVXujTQ7MzxwdTW29IM6W7iFq6w2ctL10MTMbnAApX5+KJ0uaj+MKsB3mWl4XpPnRUyDQY9AysXDgeg1EzNhNJbPZpXAeKEPAm4g2YrCALtx90g6HF07GDeobGjI+8GTSooiMmAZWf1oVlAw4rSvmWD5DmMCgYEAi6Lq7Bxe6kbhOlum/fG4k+IVAuUu+VD7QSK+cJ1rYzWyvrpQH343LkyKZIGjNyvYLbD68eyyeEQNgqff+AjFqCbU8/5O4AOkXQp7/DOW/qKghJoaEcviQ9jUTwRT/9TRJHyvzVX4tt93+iPWelEoO2MxlvD+z4ThI+yRkqJHShUCgYBQdPbV8b53CFxKPOyDro7ntHHWUdtDgG3b4np6IJKPxxEdZmkPRR3VKMGZZ7BX3D6qCy2mCbvGgPu2E7rpYWU32GU2q0k1rfdJ7ch2lbPkdwmzL/AoMI7BaU+Vq1osWZuhq0PsspOKg3mV7BVOP1rceL6p0zzbBMQGXeW1k6b9sQKBgQDE3tDkLq2o+D8Eijmt7lXYVMzHN/Rszy9QV3MWVfkhI7eLsF54yhGgHUsiZqdTMf28S+B2niy4IU85DYGevi+4u3/gM8lvte97Y+E807xPBb2uQHITJj8U62gBUAUeFyGHi9b9V8TKtkn0C2lBK8yWjsYx0hsC7P2Oagi0KNH6qg==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6;
    private Button zhifubao_pay;
    private String money="";
    private String score="";
    private Double paymoney=0.0;
    private TextView allmoney,tv_addlog;
    private Gson gson;
    private ImageView iv_back;
    private UserResult userResult;
    private UserResult.UserInfoBean list;
    private Map<String,String> map = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        init();
    }
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    allmoney.setText(list.getUserMoney());
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        addmoney();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(WalletActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(WalletActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(WalletActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    private void init() {
        checkBox1 = findViewById(R.id.radio1);
        checkBox2 = findViewById(R.id.radio2);
        checkBox3 = findViewById(R.id.radio3);
        checkBox4 = findViewById(R.id.radio4);
        checkBox5 = findViewById(R.id.radio5);
        checkBox6 = findViewById(R.id.radio6);
        iv_back = findViewById(R.id.iv_back);
        allmoney =findViewById(R.id.allmoney);
        tv_addlog = findViewById(R.id.tv_addlog);
        zhifubao_pay = findViewById(R.id.zhifubao_pay);

        gson = new Gson();
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        tv_addlog.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        allmoney.setOnClickListener(this);
        zhifubao_pay.setOnClickListener(this);
        map.put("userId",SpUtil.getString(WalletActivity.this,"userid",""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.radio1:
                paymoney = 20.0;
                map.put("userMoney",Double.parseDouble(money)+20+"");
                map.put("userScore",Integer.parseInt(score)+20+"");
                setCheckBox(checkBox1);
                break;
            case R.id.radio2:
                paymoney = 50.0;
                map.put("userMoney",Double.parseDouble(money)+50+"");
                map.put("userScore",Integer.parseInt(score)+50+"");
                setCheckBox(checkBox2);
                break;
            case R.id.radio3:
                paymoney = 100.0;
                map.put("userMoney",Double.parseDouble(money)+100+"");
                map.put("userScore",Integer.parseInt(score)+100+"");
                setCheckBox(checkBox3);
                break;
            case R.id.radio4:
                paymoney = 200.0;
                map.put("userMoney",Double.parseDouble(money)+200+"");
                map.put("userScore",Integer.parseInt(score)+200+"");
                setCheckBox(checkBox4);
                break;
            case R.id.radio5:
                paymoney = 300.0;
                map.put("userMoney",Double.parseDouble(money)+300+"");
                map.put("userScore",Integer.parseInt(score)+300+"");
                setCheckBox(checkBox5);
                break;
            case R.id.radio6:
                paymoney = 500.0;
                map.put("user_score",Double.parseDouble(score)+500+"");
                map.put("userMoney",Integer.parseInt(money)+500+"");
                setCheckBox(checkBox6);
                break;
            case R.id.zhifubao_pay:
                if (null==map.get("userScore")||null==map.get("userMoney")){
                    ToastUtil.showToast(WalletActivity.this,"请选择金额");
                    return;
                }
                payV2();
                break;
            case R.id.tv_addlog:
                startActivity(new Intent(WalletActivity.this,AddMoneyOrderActivity.class));
            case R.id.iv_back:
                finish();
                break;
        }
    }


    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,paymoney);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WalletActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(WalletActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtil.doGet("http://192.168.43.240:8080/user/userinfo?user_id="+map.get("userId"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromdata(response.body().string());
                }
            }
        });
    }
    //
    private void fromdata(String data){
        userResult = gson.fromJson(data,UserResult.class);
        list = userResult.getList();
        if (userResult.getStatus().equals("success")){
            money = list.getUserMoney();
            score = list.getUserScore()+"";
            handler.sendEmptyMessage(1);
        }
    }

    private void addmoney(){
        OkHttpUtil.doPost("http://192.168.43.240:8080/user/updatemoney", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    fromresult(response.body().string());
                }
            }
        });
    }
    private void  fromresult(String data){
        Result result = gson.fromJson(data,Result.class);
        if (result.getStatus().equals("success")){
            ToastUtil.showToast(WalletActivity.this,"支付成功");
        }else {
            ToastUtil.showToast(WalletActivity.this,result.getMessage());
        }
    }
    private void setCheckBox(CheckBox checkBox){
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        checkBox.setChecked(true);
    }
}
