package com.l1j5.web.common.mvc.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.l1j5.web.common.mvc.model.dao.ChangePasswordDao;
import com.l1j5.web.common.mvc.model.dto.SaltedUser;

/**
 * Extends the baseline Spring Security JdbcDaoImpl and implements change password functionality.
 * 
 * 
 */
public class ChangePasswordDaoImpl extends JdbcDaoImpl implements ChangePasswordDao {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Autowired
//	private SaltSource saltSource;

	public void changePassword(String username, String password) {
		//UserDetails user = loadUserByUsername(username);
		//String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
		String encodedPassword = passwordEncoder.encodePassword(password, null);
		getJdbcTemplate().update(
			"UPDATE users SET password = ? WHERE username = ?",
			encodedPassword, username);
	}

	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new SaltedUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities, ((SaltedUser) userFromUserQuery).getSalt());
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                String salt = rs.getString(4);
                return new SaltedUser(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, salt);
            }
        });
	}
}
