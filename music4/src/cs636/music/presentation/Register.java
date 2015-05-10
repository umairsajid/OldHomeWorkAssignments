package cs636.music.presentation;

import static cs636.music.dao.DBConstants.HSQLDB_URL;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cs636.music.dao.DbDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.User;

/**
 *
 *  Register a site user into User table
 *  @author Chung-Hsien (Jacky) Yu
 */
public class Register
{
	private DbDAO db;
	private UserDAO userdb;
	
	public Register(String dbUrl, String usr, String passwd) throws Exception 
	{
		if (dbUrl == null)
			dbUrl = HSQLDB_URL;  // default to HSQLDB
  			usr = "sa";
  			passwd = "";
		if (dbUrl.contains("oracle")) {
		} else if (dbUrl.contains("mysql")) {
		} else if (dbUrl.contains("hsqldb")) {
			usr = "sa";
            passwd = "";
		} else throw new SQLException("Unknown DB URL pattern in Register constructor");
		try { 
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("pizza2el");
			db = new DbDAO(emf);
			userdb = new UserDAO(db);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void registerUser(String fname, String lname, String emailaddr) throws Exception{
		try { 
			db.startTransaction();
			User user = new User();
			user.setFirstname(fname);
			user.setLastname(lname);
			user.setEmailAddress(emailaddr);
			userdb.insertUser(user);
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) 
	{
		if (args.length != 3) {
			System.out.println("usage:java <dbURL> <user> <passwd> ");
			System.exit(1);
		}
		try {
			String dbUrl = args[0];
			String usr = args[1];
			String pw = args[2];
			System.out.println("dbUrl = " + dbUrl + " usr = " + usr + " pw = " + pw );
			Register test = new Register(dbUrl, usr, pw);
			test.registerUser("Jacky", "Yu", "csyu@cs.umb.edu");
			System.out.println("Run complete, exiting");
		} catch (Exception e) {
			System.err.println("Error in run: " + e + "Cause: " + e.getCause());
		}
	}
}