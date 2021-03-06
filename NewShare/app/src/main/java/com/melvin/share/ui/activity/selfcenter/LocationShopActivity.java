package com.melvin.share.ui.activity.selfcenter;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.melvin.share.R;
import com.melvin.share.Utils.MapUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityLocationShopBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商店定位,申请线下体验馆
 * 经    度    : 104.05638
 * 纬    度    : 30.628489
 */
public class LocationShopActivity extends BaseActivity implements LocationSource,
        AMapLocationListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter, AMap.OnMarkerClickListener {
    private ActivityLocationShopBinding binding;
    private Context mContext = null;

    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MarkerOptions markerOption;
    private TextView mLocationErrText;
    private LatLng latlng = new LatLng(30.628489, 104.05638);
    private String addressLongitude = "";//经    度
    private String addressLatitude = "";//纬    度

    @Override
    protected void initView() {
    }

    @Override
    protected void initMapView(Bundle savedInstanceState) {
        super.initMapView(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.location_shop_activity);
        mapView = binding.map;
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mLocationErrText = (TextView) findViewById(R.id.location_errInfo_text);
        mLocationErrText.setVisibility(View.GONE);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式w样式
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                String locationStr = MapUtils.getLocationStr(amapLocation);
                Log.i("哈哈", locationStr);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                askServer(locationStr, amapLocation);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }
    }

    private void askServer(String locationStr, AMapLocation amapLocation) {

        latlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
        onMapLoaded();

        binding.locatioAddress.setText(amapLocation.getAddress());
        addressLongitude = amapLocation.getLongitude() + "";
        addressLatitude = amapLocation.getLatitude() + "";
//        addMarkersToMap(30.628489, 104.05540, "店铺名1", "具体位置1");
//        addMarkersToMap(30.628489, 104.05434, "店铺名2", "具体位置2");
//        addMarkersToMap(30.628489, 104.05744, "店铺名3", "具体位置3");

    }

    /**
     * 下一步
     *
     * @param view
     */
    public void clickNext(View view) {
        if (TextUtils.isEmpty(addressLongitude)) {
            Utils.showToast(mContext, "定位失败，请重新定位");
            return;
        } else if (TextUtils.isEmpty(binding.detailAddress.getText().toString())) {
            Utils.showToast(mContext, "请输入店铺名称");
            return;
        }

        Intent intent = new Intent(mContext, UploadCertificateActivity.class);
        intent.putExtra("addressLongitude",addressLongitude);
        intent.putExtra("addressLatitude",addressLatitude);
        intent.putExtra("shopName",binding.detailAddress.getText().toString());
        intent.putExtra("shopLocation",true);
        startActivity(intent);

    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(double s1, double s2, String s3, String s4) {
        LatLng latLng = new LatLng(s1, s2);// 西安市经纬度
        markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(s3).snippet(s4);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),
                        R.mipmap.position_focus)));
        ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();
        markerOptionlst.add(markerOption);
        List<Marker> markerlst = aMap.addMarkers(markerOptionlst, true);

    }

    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(new LatLng(30.628489, 104.05540)).include(new LatLng(30.628489, 104.05434))
//                .include(new LatLng(30.628489, 104.05744)).include(latlng).build();
        LatLngBounds bounds = new LatLngBounds.Builder().include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
//            mLocationOption.setInterval(5000);

            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }


    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        ((ImageView) view.findViewById(R.id.badge))
                .setImageResource(R.mipmap.badge_sa);
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");

        }
    }


}
