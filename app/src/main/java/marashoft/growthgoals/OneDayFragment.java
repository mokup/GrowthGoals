package marashoft.growthgoals;

import android.database.Cursor;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import marashoft.growthgoals.database.DBHandler;
import marashoft.growthgoals.database.adapter.ChangeListAdapter;
import marashoft.growthgoals.database.adapter.GoalsAdapter;
import marashoft.growthgoals.database.adapter.TextCheckDataModel;
import marashoft.growthgoals.database.adapter.TextCheckViewHolder;
import marashoft.growthgoals.database.query.Goals;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OneDayFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private Date dateThis;
    final SimpleDateFormat sdfDB=new SimpleDateFormat("yyyy/MM/dd");
    private View viewFather;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OneDayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OneDayFragment newInstance(Date data) {
        OneDayFragment fragment = new OneDayFragment();
        Bundle args = new Bundle();
        args.putSerializable("data",data);
        fragment.setArguments(args);
        return fragment;
    }

    private void refreshData(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_oneday, container, false);
        viewFather=view;
       TextView tw=(TextView)view.findViewById(R.id.txtData);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

        final Date date = (Date) getArguments().getSerializable("data");
        dateThis=date;
        tw.setText(sdf.format(date));

        final boolean oggi=isToday(date);


        final List<TextCheckDataModel> listToDo=new ArrayList<TextCheckDataModel>();
        final List<TextCheckDataModel> listDone=new ArrayList<TextCheckDataModel>();
        final DBHandler db=new DBHandler(this.getContext());
        calculateData(db, listToDo, listDone);

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.linearLayout);
        ListView lwToDo=(ListView)view.findViewById(R.id.listDailyGoal);
        TextView emptyToDO = (TextView)view.findViewById(R.id.emptyDailyToDo);
        lwToDo.setEmptyView(emptyToDO);

        LinearLayout llArchived = (LinearLayout)view.findViewById(R.id.linearLayoutArchived);
        ListView lwDone=(ListView)view.findViewById(R.id.listDailyGoalArchived);
        TextView emptyDone = (TextView)view.findViewById(R.id.emptyDailyDone);
        lwDone.setEmptyView(emptyDone);

        ChangeListAdapter cla=new ChangeListAdapter(this);

        final GoalsAdapter gaToDo=new GoalsAdapter(date,cla,listToDo,listDone,getContext(),R.id.listDailyGoal);
        lwToDo.setAdapter(gaToDo);

        final GoalsAdapter gaDone=new GoalsAdapter(date,cla,listDone,listToDo,getContext(),R.id.listDailyGoalArchived);
        lwDone.setAdapter(gaDone);



        FloatingActionButton addGoal=(FloatingActionButton)view.findViewById(R.id.floatingAddDailyGoal);
        addGoal.setVisibility(isToday(date)?View.VISIBLE:View.GONE);
        addGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ITEMS","onclick");
                ListView lwToDo=null;
                View view=v;
                while(lwToDo==null){
                    lwToDo=(ListView)view.findViewById(R.id.listDailyGoal);
                    view=(View)view.getParent();
                }
                Log.d("ITEMS",lwToDo.getClass().getName());

                int n=lwToDo.getCount();
                Log.d("ITEMS",n+"");
                for(int i=0;i<n;i++){
                    TextCheckViewHolder item = (TextCheckViewHolder)lwToDo.getChildAt(i).getTag();
                    String goal = item.getTextView().getText().toString();
                    if(goal==null || goal.trim().equals("")) return ;
                    String msg=item.getId()==0?"inserted":"updated";
                    if(Goals.insertDailyGoal(db, item.getId(),goal,sdfDB.format(date),item.getCheckBox().isChecked())){
                        Toast.makeText(lwToDo.getContext(),"Succesfully "+msg+" goal",Toast.LENGTH_SHORT).show();
                    }

                }
                calculateData(db,listToDo,listDone);
                refreshView(gaToDo,R.id.listDailyGoal);
            }
        });





       if(listDone.isEmpty()) collapseArchived(null,view);
        else expandArchived(null,view);

        viewLists();

        return view;
    }

    private void calculateData(DBHandler db,List<TextCheckDataModel> listToDo, List<TextCheckDataModel> listDone) {
        final boolean oggi=isToday(dateThis);
        listToDo.clear();
        listDone.clear();
        Cursor data = Goals.getData4Day(db,sdfDB.format(dateThis));
        data.moveToFirst();
        while (data.isAfterLast() == false)
        {

            TextCheckDataModel dm=new TextCheckDataModel();
            dm.setId(data.getInt(data.getColumnIndex("_id")));
            dm.setChecked(data.getInt(data.getColumnIndex("_archived"))==1);
            dm.setText(data.getString(data.getColumnIndex("_name")));
            if(dm.isChecked()) listDone.add(dm);
            else listToDo.add(dm);
            data.moveToNext();
        }

        while(oggi && (listToDo.size()+listDone.size())<3){
            listToDo.add(new TextCheckDataModel(0,"",false));
        }
    }

    public void refreshView(GoalsAdapter adp,int id){
        ListView listView = (ListView)viewFather.findViewById(id);

        listView.setAdapter(adp);
        adp.notifyDataSetChanged();
    }

    public void viewLists(){
        boolean oggi=isToday(dateThis);
        ListView listView = (ListView)viewFather.findViewById(R.id.listDailyGoal);
        ListView listViewArch = (ListView)viewFather.findViewById(R.id.listDailyGoalArchived);
        TextView ntd = (TextView)viewFather.findViewById(R.id.textView3);
        if(!oggi){
            if(listView.getCount()==0) collapseTodo(null,viewFather);
            else expandTodo(null,viewFather);
        }
        else{
            if(listViewArch.getCount()==0) collapseArchived(null,viewFather);
            else expandArchived(null,viewFather);
        }
        ntd.setVisibility(listView.getCount()+listViewArch.getCount()==0?View.VISIBLE:View.GONE);
    }

    private boolean isToday(Date dateApp){
        LocalDate dateToday=LocalDate.now();
        LocalDate dateFromApp=LocalDate.fromDateFields(dateApp);
        return dateToday.equals(dateFromApp);
    }

    public void collapseArchived(View view, View father) {
        LinearLayout listView = (LinearLayout)father.findViewById(R.id.linearLayoutArchived);
        listView.setVisibility(View.GONE);

    }

    public void expandArchived(View view, View father) {


        LinearLayout listView = (LinearLayout)father.findViewById(R.id.linearLayoutArchived);
        listView.setVisibility(View.VISIBLE);


    }

    public void collapseTodo(View view, View father) {
        LinearLayout listView = (LinearLayout)father.findViewById(R.id.linearLayout);
        listView.setVisibility(View.GONE);

    }

    public void expandTodo(View view, View father) {
        LinearLayout listView = (LinearLayout)father.findViewById(R.id.linearLayout);
        listView.setVisibility(View.VISIBLE);

    }


    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
//    }
}
