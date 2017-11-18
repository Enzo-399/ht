package cn.edu.hdu.entity;

public class LoginModel {

    static final String STATUS = "success";

    private Object data;

    public LoginModel() { }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginModel(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (this.status.equals(STATUS)) {
            return "{\"status\":\"" + this.status + "\",\"data\":{" + this.data + "\"}}";
        } else {
            return "{\"status\":\"" + this.status + "\",\"data\":{" + this.data + "}}";
        }
    }
}
