package marashoft.growthgoals.database.adapter;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import marashoft.growthgoals.OneDayFragment;

/**
 * Created by Alessandro on 01/11/2017.
 */

public class ChangeListAdapter  {


    private List<AdapterView> dataViews;
    private Fragment fragment;

    class AdapterView{
        ArrayAdapter adapter;
        int view;

        public AdapterView(ArrayAdapter adapter, int view) {
            this.adapter = adapter;
            this.view = view;
        }

        public ArrayAdapter getAdapter() {
            return adapter;
        }

        public int getView() {
            return view;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AdapterView that = (AdapterView) o;

            if (view != that.view) return false;
            return adapter != null ? adapter.equals(that.adapter) : that.adapter == null;

        }

        @Override
        public int hashCode() {
            int result = adapter != null ? adapter.hashCode() : 0;
            result = 31 * result + view;
            return result;
        }
    }


    public ChangeListAdapter(Fragment fragment) {
        this.fragment = fragment;

        dataViews=new ArrayList<AdapterView>();
    }


    public void addAdapter(ArrayAdapter aa,int view) {
        AdapterView av=new AdapterView(aa,view);
        if(!dataViews.contains(av)) dataViews.add(av);
    }

    public void refresh(){
        Log.d("CANGELIST","ci sono "+dataViews.size()+" adapter da aggiornare");
        for (AdapterView a:
             dataViews) {

            ((OneDayFragment)fragment).refreshView((GoalsAdapter) a.getAdapter(),a.getView());
        }

        if(fragment instanceof  OneDayFragment){
            ((OneDayFragment)fragment).viewLists();
        }
    }
}
