package BatchExecution;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class BatchExe_PreparedStatement {

    public static void main(String args[]) {

        Connection con = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psDelete = null;

        try {
            Class.forName("org.postgresql.Driver");

             String url = "jdbc:postgresql://localhost:5432/Database_name";
       		String user = "username";
        	String password = "password"; // change here


            con = DriverManager.getConnection(url, user, password);

            // ================= INSERT BATCH =================
            String insertQry = "INSERT INTO public.sports_list (name, sport) VALUES (?,?)";
            psInsert = con.prepareStatement(insertQry);
            psInsert.setString(1, "Hema");
            psInsert.setString(2, "football");
            psInsert.addBatch();
            
            psInsert.setString(1, "varshu");
            psInsert.setString(2, "football");
            psInsert.addBatch();

            int[] insertResult = psInsert.executeBatch();

            System.out.println("Inserted Rows: " + insertResult.length);

            // ================= UPDATE BATCH =================
            String updateQry = "UPDATE public.sports_list SET name=? WHERE id=?";
            psUpdate = con.prepareStatement(updateQry);

            psUpdate.setString(1, "narendra");
            psUpdate.setInt(2, 3);
            psUpdate.addBatch();

            psUpdate.setString(1, "ganga");
            psUpdate.setInt(2, 4);
            psUpdate.addBatch();

            int[] updateResult = psUpdate.executeBatch();

            System.out.println("Updated Rows: " + updateResult.length);

            // ================= DELETE BATCH =================
            String deleteQry = "DELETE FROM public.sports_list WHERE id=?";
            psDelete = con.prepareStatement(deleteQry);

            psDelete.setInt(1, 3);
            psDelete.addBatch();

            psDelete.setInt(1, 4);
            psDelete.addBatch();

            int[] deleteResult = psDelete.executeBatch();

            System.out.println("Deleted Rows: " + deleteResult.length);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (psInsert != null) psInsert.close();
                if (psUpdate != null) psUpdate.close();
                if (psDelete != null) psDelete.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
