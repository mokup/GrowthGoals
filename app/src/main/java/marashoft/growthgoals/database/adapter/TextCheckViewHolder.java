package marashoft.growthgoals.database.adapter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Alessandro on 01/11/2017.
 */

public class TextCheckViewHolder {
    private  ImageButton imageButton;
    private  int id;
    private CheckBox checkBox ;
    private EditText textView ;
    public TextCheckViewHolder() {}
    public TextCheckViewHolder(int id, EditText textView, CheckBox checkBox, ImageButton imageButton ) {
        this.checkBox = checkBox ;
        this.textView = textView ;
        this.id=id;
        this.setImageButton(imageButton);
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

    public ImageButton getImageButton() {
        return imageButton;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }
}
