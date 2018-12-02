package com.kangwon.a356.kangwonunivapp.network;


/**
 * 네트워크 성공 여부 함수
 */
public class NetworkExecuteMessage {

    private int number;
    private String message;


    public NetworkExecuteMessage(int number, String message)
    {
        this.number = number;
        this.message = message;
    }


    @Override
    public String toString() {
        return "[ 코드 : "+number+" ] : "+ message;
    }
}
