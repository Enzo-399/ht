package cn.edu.hdu.entity;

public class DingdanModel {

    public DingdanModel() { }

    private Object data;
    /**
     * 返回status
     */
    private String status;

    private int total;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public DingdanModel(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"status\":\"" + this.status +
                "\",\"total\":\"" + getTotal() + "\",\"data\":" + this.data+ "}";
    }
}
