package marashoft.growthgoals.database.adapter;

/**
 * Created by Alessandro on 31/10/2017.
 */

public class TextCheckDataModel {
    private String text;
    private boolean checked;
    private int id;


    public TextCheckDataModel(){}

    public TextCheckDataModel(int id,String text, boolean checked) {
        this.text = text;
        this.id=id;
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked() {
        checked = !checked ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextCheckDataModel that = (TextCheckDataModel) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
