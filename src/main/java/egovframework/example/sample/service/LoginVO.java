package egovframework.example.sample.service;

import java.io.Serializable;

public class LoginVO  implements Serializable{

	private static final long serialVersionUID = -8274004534207618049L;

	/** 사용자 ID */
	private String userId;

	/** 로그인 아이디 */
	private String username;

    /** 로그인 비밀번호 */
    private String password;

    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

}
