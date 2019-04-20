package com.github.bijoysingh.starter.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The View Pager Activity base for easy usage
 * Created by bijoy on 3/31/17.
 */

public abstract class ViewPagerActivity extends AppCompatActivity {

  protected ViewPager viewPager;
  protected ViewPagerAdapter viewPagerAdapter;

  /**
   * The id of the view pager
   *
   * @return the view pager resource id
   */
  protected abstract int getViewPagerResourceId();

  /**
   * The pages count
   *
   * @return the count
   */
  protected abstract int getPagesCount();

  /**
   * The page fragment for the position
   *
   * @param position the position
   * @return the fragment
   */
  protected abstract Fragment getPageFragment(int position);

  /**
   * Called on the page change
   *
   * @param position the position of the new page
   */
  protected abstract void onPageChanged(int position);

  /**
   * Sets the view pager and the adapter
   */
  protected void setViewPager() {
    viewPager = (ViewPager) findViewById(getViewPagerResourceId());
    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(viewPagerAdapter);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        onPageChanged(position);
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  /**
   * A simple pager adapter that represents ScreenSlidePageFragment objects, in
   * sequence.
   */
  private class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ViewPagerAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override
    public Fragment getItem(int position) {
      return getPageFragment(position);
    }

    @Override
    public int getCount() {
      return getPagesCount();
    }
  }
}
