package users.com.mealzip.Request;

public class DetailRequest {
    String email,name,Shopname,college;
    boolean inCollege;

    public DetailRequest(String email, String name, String Shopname, String college, boolean inCollege) {
        this.email = email;
        this.name = name;
        this.Shopname = Shopname;
        this.college = college;
        this.inCollege = inCollege;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return Shopname;
    }

    public void setShopName(String Shopname) {
        this.Shopname = Shopname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public boolean isInCollege() {
        return inCollege;
    }

    public void setInCollege(boolean inCollege) {
        this.inCollege = inCollege;
    }
}
