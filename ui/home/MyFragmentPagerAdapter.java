package tamhoang.ldpro4.ui.home;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/* loaded from: classes.dex */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments2) {
        super(fm);
        this.fragments = fragments2;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.fragments.size();
    }

    @Override // android.support.v4.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
}
