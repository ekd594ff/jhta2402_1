package org.desk8432.project.util;

import java.util.regex.Pattern;

public class Regex {
    private String REGEXP_LIGHT_USER_EMAIL = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$";  // 언더바(_), 하이픈(-) 제외
    private String REGEXP_USER_ID = "^[a-z]{1}[a-z0-9]{5,10}+$";  // 아이디 영문 숫자 조합 6 ~ 10
    // 사용자 아이디에 대한 정규식 - 영문 숫자 조합 6~10자리
    private String REGEXP_USER_PW_TYPE1 = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,128}+$";
    // 사용자 패스워드에 대한 정규식 - 대소문자 + 숫자 + 특수문자 조합으로 10 ~ 128자리로 정의 한다.
    private String REGEXP_NICK_NAME = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$";
    //- 2자 이상 16자 이하, 영어 또는 숫자 또는 한글로 구성
    //* 특이사항 : 한글 초성 및 모음은 허가하지 않는다.
    public boolean regexEmail(String email){
        return Pattern.matches(REGEXP_LIGHT_USER_EMAIL, email);
    }
    public boolean regexPassword(String password){
        return Pattern.matches(REGEXP_USER_PW_TYPE1, password);
    }
    public boolean regexUsername(String username){return Pattern.matches(REGEXP_USER_ID, username);}
    public boolean regexNickname(String nickname) {return Pattern.matches(REGEXP_NICK_NAME, nickname);}
    public boolean regexAll(String username, String password,String email, String nickname) {
        return
                Pattern.matches(REGEXP_USER_PW_TYPE1, password) &&
                Pattern.matches(REGEXP_USER_ID, username) &&
                Pattern.matches(REGEXP_NICK_NAME, nickname) &&
                Pattern.matches(REGEXP_LIGHT_USER_EMAIL, email);
    }
}
