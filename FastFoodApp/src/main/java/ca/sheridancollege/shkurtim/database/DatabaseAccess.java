package ca.sheridancollege.shkurtim.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.shkurtim.beans.Menu;
import ca.sheridancollege.shkurtim.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void addUser(String email, String password) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO sec_user "
		+ "(email, encryptedPassword, enabled)"
		+ " values (:email, :encryptedPassword, 1)";
		parameters.addValue("email", email);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
	}
	
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = " INSERT INTO user_role (userId, roleId) "
		+ "VALUES (:userId, :roleId)";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
		}

	public User findUserAccount(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user where email=:email";
		parameters.addValue("email", email);
		ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}

	public List<String> getRolesById(Long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName" + " FROM user_role join sec_role "
				+ "WHERE user_role.roleId=sec_role.roleId" + " AND userId=:userId";
		parameters.addValue("userId", userId);
		List <Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {roles.add((String)row.get("roleName"));}
		return roles;
	}
	
	public List <Menu> getMenuList(){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select menuName, price from Menu;";
		
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Menu>(Menu.class));
	
	}
	
	public void insertCustomerOrder(Long userId, Long menuID) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Insert into CustomerOrder (userId, menuID) values(:userId, :menuID);";
		
		parameters.addValue("userId", userId);
		parameters.addValue("menuID", menuID);
		
		jdbc.update(query, parameters);
		
		
	}
	
	public List <Menu> getCustomerOrderList(Long userId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select menuName, price from Menu where menuID in(Select menuID from CustomerOrder where userId = :userId);";
		parameters.addValue("userId", userId);
		return jdbc.query(query, parameters, new BeanPropertyRowMapper<Menu>(Menu.class));
		
	}
	
	public Menu getMenuIDByName (String menuName){
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "Select * from menu where menuName=:menuName";
		parameters.addValue("menuName",menuName);
		ArrayList<Menu> menu = (ArrayList<Menu>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<Menu>(Menu.class));
			return menu.get(0);
	}
	}
