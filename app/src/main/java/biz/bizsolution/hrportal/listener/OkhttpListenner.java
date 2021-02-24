package biz.bizsolution.hrportal.listener;

public interface OkhttpListenner {
    void onSuccess(String response);
    void onError(String error);
}
