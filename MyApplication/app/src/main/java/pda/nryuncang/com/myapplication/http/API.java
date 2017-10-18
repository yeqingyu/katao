package pda.nryuncang.com.myapplication.http;

import android.content.Context;

import pda.nryuncang.com.myapplication.utils.PreferencesUtils;

/**
 * 接口相关定义
 * Created by Wilk on 2015/6/23.
 */
public class API {
    /**
     * 测试环境test.nryuncang.com 120.27.193.1:8082
     */
    public static final String DEBUG_URL = "http://114.215.209.64:8080/wms/phone/";
    /**
     * 正式环境120.27.160.133:8080
     */
    //public static final String NORMAL_URL = "http://192.168.0.100/Service.svc/Tasks/";
//    public static final String NORMAL_URL = "http://192.168.92.253/Service.svc/Tasks/";
    /**
     * 永康
     */
//    public static final String NORMAL_URL = "http://192.168.16.20/Service.svc/Tasks/";

    /**
     * 临清
     */
    public static final String NORMAL_URL = "http://192.168.0.20/Service.svc/Tasks/";
    /**
     * 山东日照
     */
//    public static final String NORMAL_URL = "http://192.168.10.20/Service.svc/Tasks/";


    /**
     * 罗泾
     */
//    public static final String NORMAL_URL = "http://192.168.99.202/Service.svc/Tasks/";
    /**
     * 请求url拼接后缀
     */
    public static final String IP_URL = "http://%s/Service.svc/Tasks/";
    /**
     * 请求url前缀
     */
    public static String BASE_URL = NORMAL_URL;



    /**
     * 获得请求登录的url
     *
     * @return 拼接好的url
     */
//    public static String getLoginUrl() {
//        return BASE_URL + "ajaxLogin";
//    }

    public static String getPiecesManagerUrl() {
        return BASE_URL + "UpdatePortPackage";
    }

    public static String getLoginUrl() {
        return BASE_URL + "Login";
    }

    public static String getPiecesManagerUserUrl() {
        return BASE_URL + "UpdatePortPackageUser";
    }

//    public static String getPiecesManagerUrl() {
//        return BASE_URL + "ajaxPickTaskList";
//    }


}
