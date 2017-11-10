package marashoft.growthgoals.fragments.daily;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.joda.time.LocalDate;

import marashoft.growthgoals.R;
import marashoft.growthgoals.database.DBHandler;
import marashoft.growthgoals.database.adapter.ChangeListAdapter;
import marashoft.growthgoals.database.adapter.TextCheckDataModel;
import marashoft.growthgoals.database.adapter.TextCheckViewHolder;
import marashoft.growthgoals.database.query.Goals;
import marashoft.growthgoals.fragments.AbstractFragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OneDayFragment extends AbstractFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private Date dateThis;
    final SimpleDateFormat sdfDB=new SimpleDateFormat("yyyy/MM/dd");
    private View viewFather;
    private DrawerLayout drawer;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OneDayFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OneDayFragment newInstance(Date data,DrawerLayout layout) {
        OneDayFragment fragment = new OneDayFragment();
        fragment.setMenu(layout);
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
        final DrawerLayout drLayout= (DrawerLayout) getArguments().getSerializable("drawerLayout");
        dateThis=date;
        tw.setText(sdf.format(date));

        final boolean oggi=isToday(date);


        final List<TextCheckDataModel> listToDo=new ArrayList<TextCheckDataModel>();
        final List<TextCheckDataModel> listDone=new ArrayList<TextCheckDataModel>();
        final DBHandler db=new DBHandler(this.getContext());
        calculateData(db, listToDo, listDone);

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.linearLayout);
        RecyclerView lwToDo=(RecyclerView)view.findViewById(R.id.listDailyGoal);

        lwToDo.setLayoutManager(new LinearLayoutManager(this.getContext()));



        TextView emptyToDO = (TextView)view.findViewById(R.id.emptyDailyToDo);
        if(listToDo.isEmpty()){
            emptyToDO.setVisibility(View.VISIBLE);
            lwToDo.setVisibility(View.GONE);
        }
        else{
            emptyToDO.setVisibility(View.GONE);
            lwToDo.setVisibility(View.VISIBLE);
        }
//        lwToDo.setEmptyView(emptyToDO);



        LinearLayout llArchived = (LinearLayout)view.findViewById(R.id.linearLayoutArchived);
        ListView lwDone=(ListView)view.findViewById(R.id.listDailyGoalArchived);
        TextView emptyDone = (TextView)view.findViewById(R.id.emptyDailyDone);
        lwDone.setEmptyView(emptyDone);



        ImageButton dailyButton=(ImageButton)view.findViewById(R.id.imageDailyButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCloseMenu();

            }
        } );

        ImageButton addGoal=(ImageButton)view.findViewById(R.id.imageSaveButton);
        addGoal.setVisibility(isToday(date)?View.VISIBLE:View.GONE);
        addGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ITEMS","onclick");
                RecyclerView lwToDo=null;
                View view=v;
                while(lwToDo==null){
                    lwToDo=(RecyclerView)view.findViewById(R.id.listDailyGoal);
                    view=(View)view.getParent();
                }
                Log.d("ITEMS",lwToDo.getClass().getName());

                int n=lwToDo.getChildCount();
                Log.d("ITEMS",n+"");
                for(int i=0;i<n;i++){
                    EditText ev=(EditText)lwToDo.getChildAt(i).findViewById(R.id.txtNameGoal);

                    int id=ev.getTag()==null?0:(Integer)ev.getTag();
                    String goal = ev.getText().toString();
                    if(goal==null || goal.trim().equals("")) continue ;
                    String msg=id==0?"inserted":"updated";
                    if(Goals.insertDailyGoal(db, id,goal,sdfDB.format(date),false)){
                        Toast.makeText(lwToDo.getContext(),"Succesfully "+msg+" goal",Toast.LENGTH_SHORT).show();
                }

                }
                calculateData(db,listToDo,listDone);
//                refreshView(gaToDo,R.id.listDailyGoal);
            }
        });

        ChangeListAdapter cla=new ChangeListAdapter(this);

//        final GoalsAdapter gaToDo=new GoalsAdapter(date,cla,listToDo,listDone,getContext(),R.id.listDailyGoal);
        final CustomAdapter gaToDo=new CustomAdapter(listToDo);
        lwToDo.setAdapter(gaToDo);

        final GoalsAdapter gaDone=new GoalsAdapter(date,cla,listDone,listToDo,getContext(),R.id.listDailyGoalArchived);
        lwDone.setAdapter(gaDone);

        setSwipeForRecyclerView(lwToDo);

        return view;
    }

    private void setSwipeForRecyclerView(final RecyclerView mRecyclerView) {

        SwipeUtil swipeHelper = new SwipeUtil(0, ItemTouchHelper.LEFT, getActivity()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                CustomAdapter adapter = (CustomAdapter) mRecyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                CustomAdapter adapter = (CustomAdapter) mRecyclerView.getAdapter();
                if (adapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeHelper);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //set swipe label
        swipeHelper.setLeftSwipeLable("Archive");
        //set swipe background-Color
        swipeHelper.setLeftcolorCode(ContextCompat.getColor(getActivity(), R.color.colorAccent));

    }

    private AlertDialog AskOption(final DBHandler db, final RecyclerView.ViewHolder model)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this.getContext())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
//                        if(Goals.delete(db,model.getId())) {
//
//                            dataSet1.remove(model);
//                            dataset1.add(new TextCheckDataModel(0,"",false));
//
//                            if (ca != null) {
//
//                                ca.refresh();
//                            }
//                        }
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private Context getApplicationContext() {
        return this.getContext();
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

        int n=adp.getCount();

        View fv=(View)listView.getParent();

        if(fv.getVisibility()==View.GONE && n>0) fv.setVisibility(View.VISIBLE);
        else if(fv.getVisibility()==View.VISIBLE && n==0) fv.setVisibility(View.GONE);

        adp.notifyDataSetChanged();
    }

    public void viewLists(){
        boolean oggi=isToday(dateThis);
        RecyclerView listView = (RecyclerView)viewFather.findViewById(R.id.listDailyGoal);
        ListView listViewArch = (ListView)viewFather.findViewById(R.id.listDailyGoalArchived);
        TextView ntd = (TextView)viewFather.findViewById(R.id.textView3);
        if(!oggi){
            if(listView.getAdapter().getItemCount()==0) collapseTodo(null,viewFather);
            else expandTodo(null,viewFather);
        }
        else{
            if(listViewArch.getCount()==0) collapseArchived(null,viewFather);
            else expandArchived(null,viewFather);
        }
        ntd.setVisibility(listView.getAdapter().getItemCount()+listViewArch.getCount()==0?View.VISIBLE:View.GONE);
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
