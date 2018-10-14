package com.ibm.restapi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * 'IP' COULUM IS BEIGN IGNORED THEREFORE WERE ARE ASSIGNING CONSTANT VALUE
 * 'TS' = AS PK;
 * 'LOC' = FOR QUERIES
 * 
 */

//For Database Connection to the online sql database
public class DatabaseCon {
    private Connection connection;
    public Connection getConnection() {
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://85.10.205.173:3306/ibm_test", "salman_n", "test_asdf");
            
        } catch (Exception ex) {
            Logger.getLogger(DatabaseCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    
    //Updating the database entry
    public void DoUpdate(String loc, String zip,String ts) {
        String q = "UPDATE `ibm_test`.`CorrInfo` SET ip='127.0.0.1', loc='"+loc+"', zip = '"+zip+"' WHERE ts='"+ts+"'";
        try {
            DatabaseCon db = new DatabaseCon();
            Connection con = db.getConnection();
            PreparedStatement s = con.prepareStatement(q);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //To Insert new data to the database
    public void DoInsert(String loc, String zip,String ts) {
        String q = "insert into `ibm_test`.`CorrInfo`(ip,loc,zip,ts) values(?,?,?,?)";
        try {
            DatabaseCon db = new DatabaseCon();
            Connection con = db.getConnection();
            PreparedStatement s = con.prepareStatement(q);
            s.setString(1,"127.0.0.1"); //Not Being Used
            s.setString(2, loc);
            s.setString(3, zip);
            s.setString(4, ts);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //Get data from the Database
    public ResultSet getData(String q) {
    	String que = "SELECT * FROM `ibm_test`.`CorrInfo` where zip='"+q+"' LIMIT 10";
        ResultSet rs = null;
        try {
        	Connection con = new DatabaseCon().getConnection();
            Statement s = con.createStatement();
            rs = s.executeQuery(que);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public boolean isNull(String ts) {
    	 ResultSet result = null;
    	 
    	 Connection con = new DatabaseCon().getConnection();
         try {
    	 Statement s = con.createStatement();
         result = s.executeQuery("select * from ibm_test.CorrInfo where ts='"+ts+"'");
         if(result.next()){
             return false;
         }
        else{
           return true;
           } 
         }
         catch(Exception e) {
        	 Logger.getLogger(DatabaseCon.class.getName()).log(Level.SEVERE, null, e);
         }
    	return true;
    }
}