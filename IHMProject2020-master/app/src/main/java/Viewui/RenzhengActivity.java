package Viewui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ihmproject.R;
import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.Xutils;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activityrenzhen)
public class RenzhengActivity extends BaseActivity {


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
        x.view().inject(RenzhengActivity.this);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    User user=      Xutils.initDbConfiginit().selector(User.class).where("zhuangt","=","1").findFirst();
                    user.setZhuangt(-1);
                    Xutils.initDbConfiginit().update(user);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

    }



}
