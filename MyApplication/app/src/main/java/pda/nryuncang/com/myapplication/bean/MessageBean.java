package pda.nryuncang.com.myapplication.bean;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import pda.nryuncang.com.myapplication.http.RequestHelper;

/**
 * Created by kim on 2016/4/28.
 */
public class MessageBean {
    @SerializedName("status")
    private String status;
    @SerializedName("statusInfo")
    private String statusInfo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public static MessageBean creatMessageBean(JSONObject response){
        return RequestHelper.parseToGson(response.toString(), MessageBean.class);
    }

}
