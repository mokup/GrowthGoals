package marashoft.growthgoals.database.adapter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Alessandro on 01/11/2017.
 */

public class TextCheckViewHolder {

    private  int id;
    private CheckBox checkBox ;
    private EditText textView ;
    public TextCheckViewHolder() {}
    public TextCheckViewHolder(int id, EditText textView, CheckBox checkBox ) {
        this.checkBox = checkBox ;
        this.textView = textView ;
        this.id=id;

    }
    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public EditText getTextView() {
        return textView;
    }
    public void setTextView(EditText textView) {
        this.textView = textView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
