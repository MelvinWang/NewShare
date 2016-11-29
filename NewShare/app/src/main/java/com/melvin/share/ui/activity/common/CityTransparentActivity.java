package com.melvin.share.ui.activity.common;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import com.melvin.share.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import widget.OnWheelChangedListener;
import widget.WheelView;
import widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2016/2/16.
 */
public class CityTransparentActivity extends BaseActivity implements OnWheelChangedListener {
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    /**
     * 省的WheelView控件
     */
    private WheelView mProvince;
    /**
     * 市的WheelView控件
     */
    private WheelView mCity;
    /**
     * 区的WheelView控件
     */
    private WheelView mArea;

    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();
    /**
     * key - 区 values -  市省ID
     */
    protected Map<String, String> mRegionIdDatasMap = new HashMap<String, String>();
    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前省的ID
     */
    private String mCurrentProviceID = "";
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前市的ID
     */
    private String mCurrentCityID = "";
    /**
     * 当前区的名称
     */
    private String mCurrentAreaName = "";
    /**
     * 当前区的ID
     */
    private String mCurrentAreaID = "";

    private boolean areaChanged = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_city);

        initJsonData();

        mProvince = (WheelView) findViewById(R.id.id_province);
        mCity = (WheelView) findViewById(R.id.id_city);
        mArea = (WheelView) findViewById(R.id.id_district);

        initDatas();

        mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 添加change事件
        mProvince.addChangingListener(this);
        // 添加change事件
        mCity.addChangingListener(this);
        // 添加change事件
        mArea.addChangingListener(this);

        mProvince.setVisibleItems(9);
        mCity.setVisibleItems(9);
        mArea.setVisibleItems(9);
        updateCities();
        updateAreas();
    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        String[] mCitis = mCitisDatasMap.get(mCurrentProviceName);
        if (mCitis.length == 0) {
            mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, new String[]{""}));
            mArea.setCurrentItem(0);
            return;
        }
        ;
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mAreaDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        if (!areaChanged) {
            mCurrentAreaName = areas[0];
            mCurrentAreaID = mRegionIdDatasMap.get(mCurrentAreaName);
        }
        mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mArea.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
            return;
        }

        mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("regionList");
            mProvinceDatas = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("name");// 省名字
                String provinceRegionId = jsonP.getString("regionId");// 市的ID
                mRegionIdDatasMap.put(province, provinceRegionId);//省的名称和省的ID
                mProvinceDatas[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("regionList");
                } catch (Exception e1) {
                    continue;
                }

                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("name");// 市名字
                    String cityRegionId = jsonCity.getString("regionId");// 市的ID
                    mRegionIdDatasMap.put(city, cityRegionId);//市的名称和市的ID
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("regionList");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("name");// 区域的名称
                        String areaRegionId = jsonAreas.getJSONObject(k).getString("regionId");// 区域的名称
                        mRegionIdDatasMap.put(area, areaRegionId);//县的名称和县的ID
                        mAreasDatas[k] = area;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                }

                mCitisDatasMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");
            InputStreamReader reader = new InputStreamReader(is);
            int len = -1;
            char[] buf = new char[1024];
            while ((len = reader.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvince) {
            updateCities();
        } else if (wheel == mCity) {
            updateAreas();
        } else if (wheel == mArea) {
            areaChanged = true;
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
            mCurrentAreaID = mRegionIdDatasMap.get(mCurrentAreaName);
        }
    }


    public void showChoose(View view) {
//        Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.putExtra("result", mCurrentProviceName + mCurrentCityName + mCurrentAreaName + "-" + mRegionIdDatasMap.get(mCurrentProviceName) + "-" + mRegionIdDatasMap.get(mCurrentCityName));
        setResult(RESULT_OK, intent);
        finish();
    }



    @Override
    protected void initWindow() {
//        super.initWindow();
    }
}
