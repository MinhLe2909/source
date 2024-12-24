package tamhoang.ldpro4.ui.fragment;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.Vector;

import tamhoang.ldpro4.ui.fragment.Frag_NoRP3;
import tamhoang.ldpro4.ui.home.MyFragmentPagerAdapter;


public class Tab_Tinnhan extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    int i = 0;
    private MyFragmentPagerAdapter myViewpagerAdapter;
    private TabHost tabHost;
    View v;
    private ViewPager viewPager;

    /* loaded from: classes.dex */
    public class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.TabHost.TabContentFactory
        public View createTabContent(String tag) {
            View view = new View(this.mContext);
            view.setMinimumHeight(0);
            view.setMinimumWidth(0);
            return view;
        }
    }

    private void initializeTabHost(Bundle args) {
        TabHost tabHost = (TabHost) this.v.findViewById(R.id.tabhost);
        this.tabHost = tabHost;
        tabHost.setup();
        TabHost.TabSpec newTabSpec = this.tabHost.newTabSpec("Sửa tin");
        newTabSpec.setIndicator("Sửa tin");
        newTabSpec.setContent(new FakeContent(getActivity()));
        this.tabHost.addTab(newTabSpec);
        TabHost.TabSpec newTabSpec2 = this.tabHost.newTabSpec("Tin nhắn");
        newTabSpec2.setIndicator("Tin chi tiết");
        newTabSpec2.setContent(new FakeContent(getActivity()));
        this.tabHost.addTab(newTabSpec2);
        this.tabHost.setOnTabChangedListener(this);
    }

    private void initializeViewPager() {
        Vector vector = new Vector();
        vector.add(new Frag_SuatinNew());
        vector.add(new Frag_NoRP3());
        this.myViewpagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), vector);
        ViewPager viewPager = (ViewPager) this.v.findViewById(tamhoang.ldpro4.R.id.viewPager);
        this.viewPager = viewPager;
        viewPager.setAdapter(this.myViewpagerAdapter);
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(tamhoang.ldpro4.R.layout.frag_mo_report, container, false);
        initializeTabHost(savedInstanceState);
        initializeViewPager();
        this.tabHost.setCurrentTab(0);
        return this.v;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    public void onTabChanged(String tabId) {
        this.viewPager.setCurrentItem(this.tabHost.getCurrentTab());
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) this.v.findViewById(tamhoang.ldpro4.R.id.hScrollView);
        View currentTabView = this.tabHost.getCurrentTabView();
        horizontalScrollView.smoothScrollTo(currentTabView.getLeft() - ((horizontalScrollView.getWidth() - currentTabView.getWidth()) / 2), 0);
    }
}
