package com.melvin.share.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentFactory;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/17
 * <p>
 * 描述：创建主页面的Adapter
 */
public class FragmentAdapter extends FragmentPagerAdapter {
	public final static int TAB_COUNT = 4;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		//  通过Fragment工厂  生产Fragment
		return FragmentFactory.createFragment(position);
	}

	@Override
	public int getCount() {
		return TAB_COUNT;
	}
}
