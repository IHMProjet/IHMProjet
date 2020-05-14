package Viewui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ihmproject.R;
import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.Xutils;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


@ContentView(R.layout.activityrgaun)
public class RGuanenzhengActivity extends BaseActivity {


    @ViewInject(R.id.listuview)
    ListView listuview;
    List<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        x.view().inject(RGuanenzhengActivity.this);
        super.onCreate(savedInstanceState);

        try {
            users=      Xutils.initDbConfiginit().selector(User.class).where("renzhange","=","-1").findAll();

        } catch (DbException e) {
            e.printStackTrace();
        }
        listuview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return users.size();
            }

            @Override
            public Object getItem(int position) {
                return users.get(position);
            }

            @Override
            public long getItemId(int position) {
                return users.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView= LayoutInflater.from(RGuanenzhengActivity.this).inflate(R.layout.rgaunime,parent,false);
              TextView textView=  convertView.findViewById(R.id.nameren);
                textView.setText(users.get(position).getUsername()+"   "+users.get(position).getPassword());
                return convertView;
            }
        });


        listuview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                users.remove(position);
                Toast.makeText(RGuanenzhengActivity.this,"认证成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


    }



}
