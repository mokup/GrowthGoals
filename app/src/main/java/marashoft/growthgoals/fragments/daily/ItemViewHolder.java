package marashoft.growthgoals.fragments.daily;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import marashoft.growthgoals.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout regularLayout;
    public LinearLayout swipeLayout;
    public EditText listItem;
    public ImageButton undo;
    public int id;
 
    public ItemViewHolder(View view) {
        super(view);
 
        regularLayout = (LinearLayout) view.findViewById(R.id.regularLayout);
        listItem = (EditText) view.findViewById(R.id.txtNameGoal);
        swipeLayout = (LinearLayout) view.findViewById(R.id.swipeLayout);
        undo = (ImageButton) view.findViewById(R.id.undo);
        id=0;
    }
}