package Viewui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ihmproject.R;
import com.flag.myapplication.car.bean.ResultModel;
import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.StrUtil;
import com.flag.myapplication.car.utils.Xutils;
import com.google.gson.Gson;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {


    @ViewInject(R.id.tv_login)
    TextView tv_login;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;
    @ViewInject(R.id.et_pwd2)
    EditText et_pwd2;

    @ViewInject(R.id.ptyonghu)
    RadioButton ptyonghu;
    @ViewInject(R.id.glyonghu)
    RadioButton glyonghu;

    @ViewInject(R.id.guanfang)
    RadioButton guanfang;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(RegisterActivity.this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
//        try {
//            User user=  db.selector(User.class).findFirst();
//            if(user!=null)
//            {
//                Intent intent=new Intent(myContext, MainActivity.class);
//                intent.putExtra("user",user);
//                startActivity(intent);
//                finish();
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void initListener() {

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();

                if (StrUtil.isEmpty(et_phone.getText().toString())) {
                    Toast.makeText(myContext,"账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StrUtil.isEmpty(et_pwd.getText().toString())) {
                    Toast.makeText(myContext,"请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (StrUtil.isEmpty(et_pwd2.getText().toString())) {
                    Toast.makeText(myContext,"请再次输入密码", Toast.LENGTH_SHORT).show();

                    return;
                }


                if (!et_pwd.getText().toString().equals(et_pwd2.getText().toString())) {
                    Toast.makeText(myContext,"两次密码不一致", Toast.LENGTH_SHORT).show();

                    return;
                }

                User user=new User();
                user.setUsername(et_pwd.getText().toString());
                user.setPassword(et_phone.getText().toString());
                if(glyonghu.isChecked())
                {
                    user.setType(2);
                }
                if(ptyonghu.isChecked())
                {
                    user.setType(0);
                }
                if(guanfang.isChecked())
                {
                    user.setType(1);
                }


                loginhttpbend(user);


            }
        });
    }

    public void loginhttpbend(User user)
    {
        try {
            db.save(user);
        } catch (DbException e) {
            e.printStackTrace();
        }

        Intent intent=new Intent(myContext, LoginActivity.class);
        startActivity(intent);
        finish();

    }
    public void loginhttp(User user)
    {
        Gson gson = new Gson();
        String userjson = gson.toJson(user);
        Map<String,Object> map = new HashMap<>();
        map.put("userjson",userjson);
        Xutils.post("/adduser", map, new Xutils.GetDataCallback() {
            @Override
            public void success(String result) {

                Log.e("======", result);
                Gson gson = new Gson();//创建Gson对象  

                ResultModel<User> resultModel = ResultModel.fromJson(result, User.class);

                if (resultModel.getCode()==1) {//有该用户

                    Intent intent=new Intent(myContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                }
                Toast.makeText(myContext,resultModel.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failed(String... args) {
                Toast.makeText(myContext,"登陆失败", Toast.LENGTH_SHORT).show();
               // Log.e(args+);


            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
