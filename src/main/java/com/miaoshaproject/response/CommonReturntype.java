package com.miaoshaproject.response;

public class CommonReturntype {
    private String status;
    private Object data;

    public static CommonReturntype create(Object result) {
        return CommonReturntype.create(result,"success");
    }
    public static CommonReturntype create(Object result,String status) {
        CommonReturntype type = new CommonReturntype();
        type.setStatus(status);
        type.setData(result);
        return type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
