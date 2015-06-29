package com.l1j5.web.common.mvc.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.l1j5.web.common.mvc.model.dao.SecurityManagerDao;
import com.l1j5.web.common.mvc.model.dto.L1j5User;

/**
 * Extends the baseline Spring Security JdbcDaoImpl and load login user from custom user table.
 * 
 */
public class SecurityManagerDaoImpl extends JdbcDaoImpl implements SecurityManagerDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }
        L1j5User customUser = (L1j5User)userFromUserQuery;
        
        return new L1j5User(
        		returnUsername
        		, userFromUserQuery.getPassword()
        		, userFromUserQuery.isEnabled()
        		, true
        		, true
        		, true
        		, combinedAuthorities
        		, customUser.getName()
        		, customUser.getCellPhone()
        		, customUser.getEmail()
//        		, customUser.getPosition()
//        		, customUser.getExtension()
//       		, customUser.getMfCode()
//        		, customUser.getMfName()
//        		, customUser.getDtCode()
//        		, customUser.getDtName()
//        		, customUser.getWorkNo()
//        		, customUser.getCeoName()
        		, customUser.getAddress()
//        		, customUser.getTel()
 //       		, customUser.getFax()
        		);
	}
	
	@Override
	public List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("cid");
                String password = rs.getString("password");
                boolean enabled = rs.getBoolean("enabled");
                String name = rs.getString("name");
                String cellPhone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                return new L1j5User(
                		username
                		, password
                		, enabled
                		, true
                		, true
                		, true
                		, loadUserAuthorities(username)
                		, name
                		, cellPhone
                		, email
                		, address

                		);
            }
        });
	}
	
	public int changePassword(String username, String oldPassword, String newPassword) {
		L1j5User user = (L1j5User)loadUserByUsername(username);
		String oldEncodedPassword = passwordEncoder.encodePassword(oldPassword, null);
		String currPassword = user.getPassword();
		int result =  -1;
		if(currPassword.equals(oldEncodedPassword)) {
			String encodedPassword = passwordEncoder.encodePassword(newPassword, null);
			result = getJdbcTemplate().update("UPDATE member SET password = ?, updated_dttm = NOW() WHERE cid = ?", encodedPassword, username);
		} 
		
		return result;
	}
	
	public int joinOut(String username, String password) {
		String encodedPassword = passwordEncoder.encodePassword(password, null);
		L1j5User user = (L1j5User)loadUserByUsername(username);
		int result =  -1;
		if(encodedPassword.equals(user.getPassword())) {
			result = getJdbcTemplate().update("UPDATE users SET leave_dttm = NOW(), enabled = 0, updated_dttm = NOW(), updater_id = ? WHERE cid = ?", 
					user.getUsername(), user.getUsername());
		}
		return result;
	}
	
}
