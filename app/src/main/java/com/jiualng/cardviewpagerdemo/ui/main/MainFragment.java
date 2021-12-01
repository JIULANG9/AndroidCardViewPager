package com.jiualng.cardviewpagerdemo.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiualng.cardviewpagerdemo.adapter.ViewPagerAdapter;
import com.jiualng.cardviewpagerdemo.ui.home.HomeFragment;
import com.jiualng.widget.CardViewpager;
import com.jiualng.customview.R;
import com.jiualng.widget.interfac.CardViewpagerTabClickListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment  {

    private MainViewModel mViewModel;

    private CardViewpager cardViewpager;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        cardViewpager = view.findViewById(R.id.cardViewpager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        List<String> tabList = new ArrayList<>();
        tabList.add("tab1");
        tabList.add("tab2");
        tabList.add("tab3");

        //创建适配器
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), fragments);

        cardViewpager.setTabTextList(tabList)
                .setViewPagerAdapter(viewPagerAdapter)
                .addTabSelectedListener(new CardViewpagerTabClickListener() {
                    @Override
                    public void tabSelectedListener(int tabPosition) {

                    }
                });
    }
}