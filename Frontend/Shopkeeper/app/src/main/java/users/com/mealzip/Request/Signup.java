package users.com.mealzip.Request;

public class Signup {
    String email,password;
    String number;

    public Signup(String email, String password, String number) {
        this.email = email;
        this.password = password;
        this.number = number;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
