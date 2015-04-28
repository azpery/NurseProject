package darkvador.nurseproject;

import android.support.v13.app.FragmentPagerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;

import java.util.List;


public class MyPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
