package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class CitizenAccount {

    private int citizenLogin;
    private String citizenPwd;
    private UserStatus userStatus;

    public CitizenAccount() {
    }

    public CitizenAccount(int citizenLogin, String citizenPwd, UserStatus userStatus) {
        this.citizenLogin = citizenLogin;
        this.citizenPwd = citizenPwd;
        this.userStatus = userStatus;
    }

    public int getCitizenLogin() {
        return citizenLogin;
    }

    public void setCitizenLogin(int citizenLogin) {
        this.citizenLogin = citizenLogin;
    }

    public String getCitizenPwd() {
        return citizenPwd;
    }

    public void setCitizenPwd(String citizenPwd) {
        this.citizenPwd = citizenPwd;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CitizenAccount)) return false;
        CitizenAccount that = (CitizenAccount) o;
        return citizenLogin == that.citizenLogin &&
                Objects.equals(citizenPwd, that.citizenPwd) &&
                Objects.equals(userStatus, that.userStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(citizenLogin, citizenPwd, userStatus);
    }

    @Override
    public String toString() {
        return "CitizenAccount{" +
                "citizenLogin=" + citizenLogin +
                ", citizenPwd='" + citizenPwd + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
