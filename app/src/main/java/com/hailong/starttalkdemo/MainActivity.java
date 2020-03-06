package com.hailong.starttalkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hailong.starttalkdemo.adapter.FragmentsStatePager;
import com.qunar.im.ui.sdk.QIMSdk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        fragments.add(QIMSdk.getInstance().getConversationListFragment());
        fragments.add(QIMSdk.getInstance().getContactsFragment());
        FragmentsStatePager fragmentsStatePager = new FragmentsStatePager(this, fragments);
        viewPager.setAdapter(fragmentsStatePager);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvTitle.setText(tabLayout.getTabAt(position).getText());
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("会话");
                        tab.setIcon(R.drawable.msg_selector);
                        tvTitle.setText("会话");
                        break;
                    case 1:
                        tab.setText("联系人");
                        tab.setIcon(R.drawable.contact_selector);
                        tvTitle.setText("联系人");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
