package CallableStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CallableStatement_procedure {
	public static void main(String[]args) {
		Connection con = null;
		CallableStatement cs=null;
	try {
//		Step 1: load and register the driver
	Class.forName("org.postgresql.Driver");
	System.out.println("Driver is loaded and register successfully");

//    Step 2 : Establish the Connection between the java and database
	 String url = "jdbc:postgresql://localhost:5432/Database_name";
       	String user = "username";
        String password = "password"; // change here


	con = DriverManager.getConnection(url, user, password);
	System.out.println("Established connection between the java and database");

	if (con == null)
		System.out.println("Connection Not-Active");
	else
		System.out.println("Connection Active");
	 cs=con.prepareCall("CALL insert_sport(?,?)");
		cs.setString(1, "varshu");
		cs.setString(2, "volleyball");
        cs.execute();
        System.out.println("procedure is successfully completed");
	
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		
		if(con !=null)
		{
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(cs !=null)
		{
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}}

	}}

}
