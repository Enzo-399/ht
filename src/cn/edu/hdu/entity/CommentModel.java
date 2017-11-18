package cn.edu.hdu.entity;

public class CommentModel {

    public CommentModel() {}

    private Object data;
    /**
     * 返回status
     */
    private String status;

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

    public CommentModel(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (this.status.equals("success")) {
            return "{\"status\":\"" + this.status + "\",\"data\":{" + this.data + "\"}}";
        } else {
            return "{\"status\":\"" + this.status + "\",\"data\":{" + null + "}}";
        }
    }
}
