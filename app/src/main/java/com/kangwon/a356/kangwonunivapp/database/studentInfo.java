package com.kangwon.a356.kangwonunivapp.database;

import com.kangwon.a356.kangwonunivapp.database.datainterface.Message;

public class studentInfo {
    private String id;
    private String passwd;


    /**
     * 자동으로 데이터베이스에 존재하는 id와 passwd를 찾아 초기화한다.
     * 없을 경우 모든 id와 passwd는 null 값을 가진다.
     */
    public studentInfo()
    {

        id = null;
        passwd = null;

    }


    /**
     *
     * @param id 학번 이름
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     *
     * @param passwd 비밀번호 이름.
     */
    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }



    /**
     *
     * @return id가 존재할 경우 id를 반환한다.
     * @throws InformationNotFoundException 정보를 찾을 수 없을때 발생하는 예외.
     * 이 메소드는 id를 반환한다. 이는 자동 로그인에 사용된다.
     */
    public String getId() throws InformationNotFoundException
    {
        if(id == null)
            throw new InformationNotFoundException("there is no ID data");
        return id;
    }


    /**
     *
     * @return passwd가 존재할 경우 password를 반환한다.
     * @throws InformationNotFoundException
     * 이는 자동 로그인에 사용된다.
     */
    public String getPasswd() throws  InformationNotFoundException
    {
        if(passwd == null)
            throw new InformationNotFoundException("there is no passwd data.");
        return passwd;
    }

}
