package be.unamur.hermes.dataaccess.repository;

import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.UserAccount;

public interface UserAccountRepository {

    long create(UserAccount citizenAccount);

    void update(long userAccountID, UpdateUserAccount update);

}
