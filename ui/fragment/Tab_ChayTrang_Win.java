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
import tamhoang.ldpro4.ui.home.MyFragmentPagerAdapter;

/* loaded from: classes.dex */
public class Tab_ChayTrang_Win extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private Frag_ChayTrang_Acc_Win chayTrangAccFragment;
    private Frag_ChaytrangWin chayTrangFragment;
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

    private void initializeTabHost(Bundle savedInstanceState) {
        TabHost tabHost = (TabHost) this.v.findViewById(R.id.tabhost);
        this.tabHost = tabHost;
        tabHost.setup();
        TabHost.TabSpec newTabSpec = this.tabHost.newTabSpec("Chạy Trang");
        newTabSpec.setIndicator("Vào trang Win-Win");
        newTabSpec.setContent(new FakeContent(getActivity()));
        this.tabHost.addTab(newTabSpec);
        TabHost.TabSpec newTabSpec2 = this.tabHost.newTabSpec("Mã chạy");
        newTabSpec2.setIndicator("Tài khoản Win-Win");
        newTabSpec2.setContent(new FakeContent(getActivity()));
        this.tabHost.addTab(newTabSpec2);
        this.tabHost.setOnTabChangedListener(this);
    }

    private void initializeViewPager() {
        Vector vector = new Vector();
        Frag_ChaytrangWin frag_ChaytrangWin = new Frag_ChaytrangWin();
        this.chayTrangFragment = frag_ChaytrangWin;
        vector.add(frag_ChaytrangWin);
        Frag_ChayTrang_Acc_Win frag_ChayTrang_Acc_Win = new Frag_ChayTrang_Acc_Win();
        this.chayTrangAccFragment = frag_ChayTrang_Acc_Win;
        vector.add(frag_ChayTrang_Acc_Win);
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
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i2, float v2, int i1) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    public void onTabChanged(String s) {
        Frag_ChaytrangWin frag_ChaytrangWin;
        this.viewPager.setCurrentItem(this.tabHost.getCurrentTab());
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) this.v.findViewById(tamhoang.ldpro4.R.id.hScrollView);
        View currentTabView = this.tabHost.getCurrentTabView();
        horizontalScrollView.smoothScrollTo(currentTabView.getLeft() - ((horizontalScrollView.getWidth() - currentTabView.getWidth()) / 2), 0);
        String currentTabTag = this.tabHost.getCurrentTabTag();
        getActivity().getSupportFragmentManager();
        if (!currentTabTag.contains("Chạy Trang") || (frag_ChaytrangWin = this.chayTrangFragment) == null) {
            return;
        }
        frag_ChaytrangWin.refresh();
    }
}
