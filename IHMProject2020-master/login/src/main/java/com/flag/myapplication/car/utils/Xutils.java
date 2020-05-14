package com.flag.myapplication.car.utils;

import android.widget.ImageView;

import com.flag.myapplication.hotel.R;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.Map;

public class Xutils {
    private static DbManager db;

     public static  final String BASE_URL = "http://10.0.2.2:8080";
    //  public static  final String BASE_URL = "http://192.168.3.7:8080";


        public static void get(String url, Map<String, Object> parms, final GetDataCallback callback) {
            RequestParams params = new RequestParams(BASE_URL + url);
            if(parms!=null){
                for (String key : parms.keySet()) {
                    params.addParameter(key, parms.get(key));
                }
            }
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.success(result);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.failed();
                }
                @Override
                public void onCancelled(CancelledException cex) {}
                @Override
                public void onFinished() {}
            });
        }

        public static void post(String url, Map<String, Object> parms, final GetDataCallback callback) {
            RequestParams params = new RequestParams(BASE_URL + url);
            if(parms!=null){
                for (String key : parms.keySet()) {
                    params.addParameter(key, parms.get(key));
                }
            }
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if(callback!=null){
                        callback.success(result);
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    if(callback!=null){
                        callback.failed();
                    }
                }
                @Override
                public void onCancelled(CancelledException cex) {}
                @Override
                public void onFinished() {}
            });
        }
        /**上传文件*/
        public static void uplodFile(String url,  List<KeyValue> list, final GetDataCallback callback) {
            RequestParams params = new RequestParams(BASE_URL+url);
          //  params.setMultipart(true);
            params.setAsJsonContent(true);
            MultipartBody body = new MultipartBody(list, "UTF-8");
            params.setRequestBody(body);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    callback.success(result);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    callback.failed();
                }
                @Override
                public void onCancelled(CancelledException cex) {}
                @Override
                public void onFinished() {}
            });
        }
        /**回调接口*/
        public interface GetDataCallback {
            void success(String result);
            void failed(String... args);
        }






    public static DbManager  initDbConfiginit()
    {
        if(db==null) {


            DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                    .setDbName("xutils3_db") //设置数据库名
                    .setDbVersion(1) //设置数据库版本
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            db.getDatabase().enableWriteAheadLogging();
                            //开启WAL, 对写入加速提升巨大(作者原话)
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            //数据库升级操作
                        }
                    });
            db = x.getDb(daoConfig);
        }
        return db;
    }

    public static void display(ImageView imageView, String iconUrl, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(radius))
                .setIgnoreGif(false)
                .setUseMemCache(false)//设置使用缓存
                .setCrop(true)//是否对图片进行裁剪
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .build();

        x.image().bind( imageView, iconUrl+"?"+ getimestring(), imageOptions,new Callback.CacheCallback() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(Object result) {
                return false;
            }

        });
    }




        public  static String getimestring()

        {
            return System.currentTimeMillis()+"";
        }
}