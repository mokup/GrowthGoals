package marashoft.growthgoals.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

/**
 * Created by Alessandro on 08/11/2017.
 */

public abstract class AbstractFragment extends Fragment {
    private DrawerLayout drawer;
    public void setMenu(DrawerLayout drawer){
        this.drawer=drawer;
    }
    protected void openCloseMenu(){
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            drawer.openDrawer(GravityCompat.START);
    }


    public DrawerLayout getMenu() {
        return drawer;
    }
}
