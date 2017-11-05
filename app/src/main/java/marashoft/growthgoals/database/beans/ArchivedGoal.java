package marashoft.growthgoals.database.beans;

import java.util.Date;

/**
 * Created by Alessandro on 23/10/2017.
 */

public class ArchivedGoal extends Goal {

    private Date archiveDate;

    public ArchivedGoal(int id, String name, String description, int type, Date startDate, Date endDate,Date archiveDate) {
        super(id,name,description,type,startDate,endDate);
        this.archiveDate=archiveDate;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }
}
