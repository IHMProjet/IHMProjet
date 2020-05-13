package Viewui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.util.List;
import java.util.Map;

import Viewui.Activity.MainActivity;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.tv_regi)
    TextView tv_regi;
    @ViewInject(R.id.tv_login)
    TextView tv_login;
    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_pwd)
    EditText et_pwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(LoginActivity.this);
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void initData() {
        try {
            User user=  db.selector(User.class).where("zhuangt","=",1).findFirst();
            if(user!=null)
            {

                   Intent intent=new Intent(myContext, MainActivity.class);
                   intent.putExtra("user",user);
                   startActivity(intent);
                   finish();



            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void initListener() {

        tv_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();
                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)) {
                    Toast.makeText(myContext,"账号号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

//                startActivity(new Intent(myContext, MainActivity.class));
//                finish();
                loginhttpbendi( tempPhone , tempPwd);


            }
        });
    }



    public void loginhttpbendi(String username ,String password)
    {
        try {
            User li33st= Xutils.initDbConfiginit().selector(User.class).where("username","=" ,username)
                    .and("password","=",password).findFirst();
            if(li33st!=null)
            {
                li33st.setZhuangt(1);
                Xutils.initDbConfiginit().update(li33st);
                Intent intent=new Intent(myContext, MainActivity.class);
                intent.putExtra("user",li33st);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(myContext,"登陆失败", Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }



        public void loginhttp(String username ,String password)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        Xutils.post("/userlogin", map, new Xutils.GetDataCallback() {
            @Override
            public void success(String result) {

                Log.e("======", result);
                Gson gson = new Gson();//创建Gson对象  

                ResultModel<User> resultModel = ResultModel.fromJson(result, User.class);

                if (resultModel.getCode()==1) {//有该用户
                    User userBean = (User) resultModel.getData();
                  //  SharedPreferencesUtil.saveDataBean(LoginActivity.this, userBean, "user");
                  //  EventBus.getDefault().post(new EventMessage(EventMessage.LOGIN));
                    try {
                        db.save(userBean);
                        List<User> li33st= Xutils.initDbConfiginit().selector(User.class).findAll();
                        List<User> li3333st= Xutils.initDbConfiginit().selector(User.class).findAll();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    Intent intent=new Intent(myContext, MainActivity.class);
                    intent.putExtra("user",userBean);
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
//    Map<String,Object> map = new HashMap<>();
//map.put("pageNumber",page);
//map.put("typeid",typeid);
////如果请求不需要参数,传null
//// GetDataTask.post("app/types", null, new GetDataTask.GetDataCallback(){}
//GetDataTask.post("app/types", map, new GetDataTask.GetDataCallback() {
//        @Override
//        public void success(String response) {
//            Gson gson = new Gson(); //后台返回来的json格式，其他格式自己处理
//            Result result = gson.fromJson(response, Result.class);
//        }
//        @Override
//        public void failed(String... args) {
//        }
//    });

}
