package marashoft.growthgoals.fragments.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import marashoft.growthgoals.R;
import marashoft.growthgoals.database.DBHandler;
import marashoft.growthgoals.database.query.Goals;
import marashoft.growthgoals.fragments.AbstractFragment;

/**
 * Created by Alessandro on 21/10/2017.
 */

public class DailyFragment extends AbstractFragment {


    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.daily_fragment, container, false);


        viewPager = (ViewPager)view.findViewById(R.id.view_pager);

        //Mi faccio restituire l'elenco dei giorni,settimane,mesi,anni da far vedere
        List<Date> listDate=new LinkedList<Date>();

        DBHandler db=new DBHandler(this.getContext());
        String minData = Goals.getAllDays(db);
        Date md=null;
        if(minData!=null){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

            try {
                md=sdf.parse(minData);
                listDate.add(md);
            } catch (ParseException e) {
                Log.e("ERROR","ERRORE nel parsing della data"+minData);
            }
        }
        else{
            md=LocalDate.now().toDate();
            listDate.add(md);
        }
        LocalDate cd=LocalDate.fromDateFields(md);
        LocalDate oggi=LocalDate.now();
        while(!cd.equals(oggi)){

            cd=cd.plusDays(1);
            listDate.add(cd.toDate());
        }


        viewPager.setAdapter(new PagerAdpater(getChildFragmentManager(),listDate,getMenu()));
        if(!listDate.isEmpty()) viewPager.setCurrentItem(listDate.size()-1);
//        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
