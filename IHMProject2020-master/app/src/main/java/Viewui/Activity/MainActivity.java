package Viewui.Activity;
import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import org.osmdroid.config.Configuration;
import org.xutils.ex.DbException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ihmproject.R;
import com.flag.myapplication.car.bean.User;
import com.flag.myapplication.car.utils.Xutils;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;
import java.util.Objects;

import Viewui.Fragment.MapFragment;
import Viewui.Fragment.ModeDeDeplacementFragment;
import Interface.IButtonDrawerClickListener;
import Interface.IButtonMapListener;
import Viewui.LoginActivity;
import Viewui.RenzhengActivity;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IButtonDrawerClickListener, View.OnClickListener, IButtonMapListener {
    private Intent intent;
    private DrawerLayout drawerLayout;

    private MapFragment mapFragment;
    private boolean permissionGranted;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    private static final String TAG = "MainActivity";




    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(MainActivity.this, "com.lt.uploadpicdemo.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }




    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
            Log.d(TAG,"imagePath:"+imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(   getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()) );

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,
                R.string.naviguation_drawer_open, R.string.naviguation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView mainNavigationView = (NavigationView) findViewById(R.id.main_nav_view);

        try {
            User user=Xutils.initDbConfiginit().selector(User.class).findFirst();
            TextView textview= mainNavigationView.getHeaderView(0).findViewById(R.id.username);
            textview.setText(user.getUsername());
        }catch (DbException e){
            e.printStackTrace();
        }

        mainNavigationView.setNavigationItemSelectedListener(this);
        mainNavigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                initiateLanguageSpinner();
                return insets;
            }
        });

        if(savedInstanceState == null) startMapFragment();
        findViewById(R.id.zhong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switchLanguage(Locale.SIMPLIFIED_CHINESE);
              //  switchLanguage( "zh");

            }
        });
        findViewById(R.id.ying).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switchLanguage(Locale.ENGLISH);
              //  switchLanguage( "en");

            }
        });
        findViewById(R.id.fayu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLanguage(Locale.CANADA_FRENCH);
            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        // map.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        // map.onPause();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }/*else if(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).getTargetFragment() instanceof ModeDeDeplacementFragment){
            startMapFragment();
        }*/
        else if(Objects.equals(Objects.requireNonNull(getSupportActionBar()).getTitle(), "Mode de déplacement")){
            startMapFragment();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_transport_mode) {
            getSupportActionBar().setTitle("Mode de déplacement");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ModeDeDeplacementFragment()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(menuItem.getItemId() == R.id.SwitchGPS) {
            permissionGranted = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if (!permissionGranted) {

            }
            if (permissionGranted) {
            }
        }

        if(menuItem.getItemId() == R.id.renzheng) {
           startActivity(new Intent(MainActivity.this, RenzhengActivity.class));

        }


        if(menuItem.getItemId() == R.id.tuisong) {
            Toast.makeText(this, "推送成功", Toast.LENGTH_SHORT).show();
        }
        if(menuItem.getItemId() == R.id.tuichu) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            try {
                Xutils.initDbConfiginit().delete(User.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
            finish();
        }



        return true;
    }
    private void startMapFragment(){
        getSupportActionBar().setTitle(R.string.app_name);
        if(mapFragment==null)mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                mapFragment).commit();
    }
    private void initiateLanguageSpinner(){
        Spinner languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.languages));
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(languageSpinner != null && languageSpinner.getAdapter()==null)
            languageSpinner.setAdapter(languagesAdapter);
    }
    @Override
    public void onCloseModeTransportButtonClicked(View v) {
        startMapFragment();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void mapIntentButtonClicked(View v) {
        switch(v.getId()){
            case R.id.incidentButton :
                intent = new Intent(this, IncidentActivity.class);
                break;
            case R.id.accidentButton:
                intent = new Intent(this, AccidentActivity.class);

                break;
        }
        if(intent != null){
            intent.putExtra("longitude",mapFragment.getUserCurrentLongitude());
            intent.putExtra("latitude",mapFragment.getUserCurrentLatitude());
            // intent.putExtra("name",mapFragment.getLocationName());
        startActivity(intent);}
    }
}
