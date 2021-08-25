package com.example.statophobic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.statophobic.Fragments.ImageFragment;
import com.example.statophobic.Fragments.VideoFragment;

import com.example.statophobic.databinding.ActivityWhatsAppBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppActivity extends AppCompatActivity {
    private ActivityWhatsAppBinding binding ;
    private WhatsAppActivity activity;
    private viewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_whats_app);
        activity=this;

        initView();

    }

    private void initView() {
        adapter = new viewPagerAdapter(activity.getSupportFragmentManager(), activity.getLifecycle());
        adapter.addFragment(new ImageFragment(),"Images");
        adapter.addFragment(new VideoFragment(),"Videos");

        binding.ViewPager.setAdapter(adapter);
        binding.ViewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout,binding.ViewPager,(tab,position) -> {
            tab.setText(adapter.fragmentTittleList.get(position));

        }).attach();

        for(int i = 0;i<binding.tabLayout.getTabCount();i++){
            TextView tv = (TextView) LayoutInflater.from(activity).inflate(R.layout.custum_tab,null);
            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    class viewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTittleList = new ArrayList<>();


        public viewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment,String tittle){
            fragmentList.add(fragment);
            fragmentTittleList.add(tittle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}