package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface UserAccountRepository {

    long create(UserAccount citizenAccount);

    void activate(long userAccountID);

}
