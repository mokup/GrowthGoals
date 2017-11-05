package marashoft.growthgoals.database.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alessandro on 23/10/2017.
 */

public class Goal {
    private int id;
    private String name;
    private String description;
    private int type;
    private Date startDate;
    private Date endDate;
    private static final Map<String,String> field=new HashMap<String,String>();


    static {
        field.put("_id", "INTEGER PRIMARY KEY AUTOINCREMENT");
        field.put("_name", "text");
        field.put("_description", "text");
        field.put("_type", "integer");
        field.put("_start_date", "TEXT");
        field.put("_end_date", "TEXT");
        field.put("_archived", "INTEGER default 0");
    }

    public Goal(int id, String name, String description, int type, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public static String getTableName() {
        return "goal";
    }


    public static Map<String, String> getFields() {
        return field;
    }
}
