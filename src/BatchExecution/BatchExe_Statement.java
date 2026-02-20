package BatchExecution;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class BatchExe_Statement {
		public static void main(String[] args) {
			
			Connection con = null;
			Statement st = null;
			try {
				
				Class.forName("org.postgresql.Driver");
				
				   String url = "jdbc:postgresql://localhost:5432/Database_name";
       					 String user = "username";
        				String password = "password"; // change here

				    con = DriverManager.getConnection(url, user, password);
				    st = con.createStatement();
				   st.addBatch("update public.sports_list set name='devi' where id=3");
				   st.addBatch("insert into public.sports_list (name,sport) values "
				   		+ "('karthik','football') ");
				   st.addBatch("delete from public.sports_list  where id in (1,3,5) ");
				
				  int[] arr = st.executeBatch();
				  
				  for(int i=0;i<arr.length;i++) {
					  
					  System.out.println(arr[i]+" : rows affected");  
				  }
				  
			}catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}finally {
				
				
				if(con !=null)
				{
					try {
						con.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(st != null) {
					
					try {
						st.close();
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}


