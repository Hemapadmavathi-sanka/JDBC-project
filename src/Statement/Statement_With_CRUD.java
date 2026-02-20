package Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statement_With_CRUD {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

         String url = "jdbc:postgresql://localhost:5432/Database_name";
       	String user = "username";
        String password = "password"; // change here


        try {
            /* STEP 1: Load Driver */
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver Loaded Successfully");

            /* STEP 2: Establish Connection */
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Database Connected");

            /* STEP 3: Create Statement */
            st = con.createStatement();

            /* ================= INSERT OPERATION ================= */
            String insertQry = "INSERT INTO public.sports_list(name,sport) VALUES('Hema','chess')";
            int insertCount = st.executeUpdate(insertQry);
            System.out.println("Inserted Rows: " + insertCount);

            /* ================= SELECT OPERATION ================= */
            String selectQry = "SELECT * FROM public.sports_list ORDER BY id ASC";
            rs = st.executeQuery(selectQry);

            System.out.println("\n Table Data:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name")+" | " +
                        rs.getString("sport")
                );
            }

            /* ================= UPDATE OPERATION ================= */
            String updateQry = "UPDATE public.sports_list SET sport='football' WHERE id=1";
            int updateCount = st.executeUpdate(updateQry);
            System.out.println("\nUpdated Rows: " + updateCount);

            /* ================= DELETE OPERATION ================= */
            String deleteQry = "DELETE FROM public.sports_list WHERE id=3";
            int deleteCount = st.executeUpdate(deleteQry);
            System.out.println("Deleted Rows: " + deleteCount);

            /* ================= EXECUTE METHOD DEMO ================= */
            String execQry = "UPDATE public.sports_list SET sport='chess' WHERE id=2";
            boolean result = st.execute(execQry);

            if (!result) {
                int count = st.getUpdateCount();
                System.out.println("Execute Update Count: " + count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();

                System.out.println("\n All Resources Closed Successfully");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}