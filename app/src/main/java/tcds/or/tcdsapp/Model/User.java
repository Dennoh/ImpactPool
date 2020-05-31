package tcds.or.tcdsapp.Model;

public class User {
   // {"phonenumber":"1000","name":"Mason ","location":"Arusha"}
    private String phonenumber;
    private String name;
    private String location;
    private String error_msg; //it will empty if user return object

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
