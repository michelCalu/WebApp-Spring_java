package be.unamur.hermes.dataaccess.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.unamur.hermes.dataaccess.dto.UpdateUserAccount;
import be.unamur.hermes.dataaccess.entity.UserAccount;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert accountInserter;

    // queries
    private static final String updateQuery = //
	    "UPDATE t_user_accounts ua SET ua.%s = ? WHERE ua.userAccountID = ?";

    @Autowired
    public UserAccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
	super();
	this.jdbcTemplate = jdbcTemplate;
	this.accountInserter = new SimpleJdbcInsert(jdbcTemplate.getDataSource()).withTableName("t_user_accounts")
		.usingGeneratedKeyColumns("userAccountID");
    }

    @Override
    public long create(UserAccount citizenAccount) {
	Map<String, Object> params = new HashMap<>();
	params.put("roles", UserAccount.getPersistableAuthorities(citizenAccount));
	params.put("password", citizenAccount.getPassword());
	params.put("userStatus", citizenAccount.getStatus().getValue());
	return (Long) accountInserter.executeAndReturnKey(params);
    }

    @Override
    public void update(long userAccountID, UpdateUserAccount account) {
	if (account.getStatus() != null)
	    jdbcTemplate.update(String.format(updateQuery, "userStatus"), account.getStatus(), userAccountID);
	if (account.getPassword() != null)
	    jdbcTemplate.update(String.format(updateQuery, "password"), account.getPassword(), userAccountID);
    }
}
