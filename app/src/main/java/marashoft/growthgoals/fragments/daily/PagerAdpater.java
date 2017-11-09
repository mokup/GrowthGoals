package marashoft.growthgoals.fragments.daily;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import marashoft.growthgoals.fragments.daily.OneDayFragment;

public class PagerAdpater extends FragmentStatePagerAdapter {

    private List<Date> nItems;
    private DrawerLayout menu;



    public PagerAdpater(FragmentManager fm,List<Date> nItems,DrawerLayout menu) {
  super(fm);
   this.nItems=nItems;
        this.menu=menu;
 }
 
 /*
  * (non-Javadoc)
  * 
  * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
  */
 @Override
 public Fragment getItem(int position) {
     return OneDayFragment.newInstance(nItems.get(position),menu);
 }
 
 /*
  * (non-Javadoc)
  * 
  * @see android.support.v4.view.PagerAdapter#getCount()
  */
 @Override
 public int getCount() {
  // TODO Auto-generated method stub
  return nItems.size();
 }

 public CharSequence getPageTitle(int position) {
     SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
     return sdf.format(nItems.get(position));
 }
 
}