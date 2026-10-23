
package bean;

import java.io.Serializable;
import java.time.LocalDate;

public class FamilyBD implements Serializable {

    private String familyBirthdayId;
    private String userId;
    private String relation;
    private LocalDate birthday;

    public FamilyBD() {
    }

    public String getFamilyBirthdayId() {
        return familyBirthdayId;
    }

    public void setFamilyBirthdayId(String familyBirthdayId) {
        this.familyBirthdayId = familyBirthdayId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
