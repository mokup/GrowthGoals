package marashoft.growthgoals.database.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import marashoft.growthgoals.R;
import marashoft.growthgoals.database.DBHandler;
import marashoft.growthgoals.database.query.Goals;

/**
 * Created by Alessandro on 24/10/2017.
 */

public class GoalsAdapter extends ArrayAdapter {


    private Date date;
    private List dataSet1;
    private List dataSet2;
    private Context mContext;
    private ChangeListAdapter ca;

    public GoalsAdapter(Date date, ChangeListAdapter ca, List data1, List data2, Context context, int listview) {

        super(context, R.layout.daily_goals_item, data1);
        this.dataSet1 = data1;
        this.dataSet2 = data2;
        this.mContext = context;
        this.ca=ca;
        this.date=date;
        ca.addAdapter(this,listview);
    }



    @Override
    public int getCount() {
        return dataSet1.size();
    }

    @Override
    public TextCheckDataModel getItem(int position) {
        return (TextCheckDataModel) dataSet1.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final TextCheckDataModel dataModel = (TextCheckDataModel) this.getItem( position );
        CheckBox checkBox ;
        EditText textView ;


        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_goals_item, parent, false);
            textView = (EditText) convertView.findViewById(R.id.txtNameGoal);

            checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(new TextCheckViewHolder(dataModel.getId(),textView,checkBox));

            checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    TextCheckDataModel planet = (TextCheckDataModel) cb.getTag();
                    planet.setChecked( cb.isChecked() );





                    DBHandler db=new DBHandler(mContext);
                    if(Goals.archive(db,dataModel.getId(),cb.isChecked())) {

                        dataSet1.remove(planet);
                        dataSet2.add(planet);

                        if (ca != null) {

                            ca.refresh();
                        }
                    }

                }
            });


        } else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            TextCheckViewHolder viewHolder = (TextCheckViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox() ;
            textView = viewHolder.getTextView() ;
        }

        checkBox.setTag( dataModel );

        // Display planet data
        checkBox.setChecked( dataModel.isChecked() );
        textView.setText( dataModel.getText() );


/*
        View parentView=(View) checkBox.getParent();
        int parentId = parentView.getId();

        while(parentId!=R.id.listDailyGoalArchived && parentId!=R.id.listDailyGoal){
            parentView=(View) parentView.getParent();
            parentId = parentView.getId();
        }

*/
        if(!isToday(date) || parent.getId()==R.id.listDailyGoalArchived){
            textView.setTag(textView.getKeyListener());
            textView.setKeyListener(null);
            textView.setCursorVisible(false);
            textView.setFocusableInTouchMode(false);
            textView.setBackgroundColor(Color.TRANSPARENT);

        }




        return convertView;
    }


    private boolean isToday(Date dateApp){
        LocalDate dateToday=LocalDate.now();
        LocalDate dateFromApp=LocalDate.fromDateFields(dateApp);
        return dateToday.equals(dateFromApp);
    }

}