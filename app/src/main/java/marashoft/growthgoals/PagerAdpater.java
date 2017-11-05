package marashoft.growthgoals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PagerAdpater extends FragmentStatePagerAdapter {

    private List<Date> nItems;

 
 public PagerAdpater(FragmentManager fm,List<Date> nItems) {
  super(fm);
   this.nItems=nItems;
 }
 
 /*
  * (non-Javadoc)
  * 
  * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
  */
 @Override
 public Fragment getItem(int position) {
     return OneDayFragment.newInstance(nItems.get(position));
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