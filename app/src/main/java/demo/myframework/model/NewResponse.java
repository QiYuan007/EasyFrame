package demo.myframework.model;

/**
 * @Author: lizhipeng
 * @Data: 16/5/24 上午10:13
 * @Description:
 */
public class NewResponse<T> implements IModel {
    protected int reCode;
    protected String message;
    private T data;

    public int getReCode() {
        return reCode;
    }

    public void setReCode(int reCode) {
        this.reCode = reCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewResponse{" +
                "reCode=" + reCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
