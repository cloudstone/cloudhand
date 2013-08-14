package com.cloudstone.cloudhand.activity;

import java.util.ArrayList;
import java.util.List;

import com.cloudstone.cloudhand.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class OpenTableActivity extends FragmentActivity {
	private ViewPager viewPager;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private List<String> titleList    = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_table);
		
		viewPager = (ViewPager)findViewById(R.id.viewPager_open_table);
		fragmentList.add(new OpenTableFragment1());
        fragmentList.add(new OpenTableFragment1());
        fragmentList.add(new OpenTableFragment1());
		titleList.add(getString(R.string.order));
		titleList.add(getString(R.string.already));
		titleList.add(getString(R.string.other));
		
		viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
		
	}
	
	class myPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private List<String>   titleList;

        public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList){
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            return (fragmentList == null || fragmentList.size() == 0) ? null : fragmentList.get(arg0);
        }

        /**
         * 每个页面的title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return (titleList.size() > position) ? titleList.get(position) : "";
        }

        /**
         * 页面的总个数
         */
        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

}
