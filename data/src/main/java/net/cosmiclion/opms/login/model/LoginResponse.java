package net.cosmiclion.opms.login.model;

/**
 * Created by longpham on 9/16/2016.
 */
public class LoginResponse {
    private String success;
    private String sessionToken;
    private String message;
    private String uid;
    private String id;
    private String name;
    private String email;
    private String company;
    private String cid;
    private String admin;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success='" + success + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", message='" + message + '\'' +
                ", uid='" + uid + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", cid='" + cid + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
