package PreparedStatement;
import java.sql.*;
public class PreparedStatement_With_CRUD {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
 String url = "jdbc:postgresql://localhost:5432/Database_name";
      String user = "username";
       String password = "password"; // change here


        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);

            /* ================= INSERT OPERATION ================= */

            String insertQry = "INSERT INTO public.sports_list"
                    + "(name, sport) "
                    + "VALUES (?,?)";

            ps = con.prepareStatement(insertQry);

            ps.setString(1, "hema");
            ps.setString(2, "tennis");

            int insertRows = ps.executeUpdate();
            System.out.println("Inserted Rows: " + insertRows);

            ps.close();

            /* ================= SELECT OPERATION ================= */

            String selectQry = "SELECT * FROM public.sports_list";
            ps = con.prepareStatement(selectQry);
            rs = ps.executeQuery();

            System.out.println("\n sports_list Table Data:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("sport") 
                );
            }

            rs.close();
            ps.close();

            /* ================= UPDATE OPERATION ================= */

            String updateQry = "UPDATE public.sports_list SET name=? WHERE id=?";
            ps = con.prepareStatement(updateQry);

            ps.setString(1, "ranjith");
            ps.setInt(2, 10);

            int updateRows = ps.executeUpdate();
            System.out.println("\nUpdated Rows: " + updateRows);

            ps.close();

            /* ================= DELETE OPERATION ================= */

            String deleteQry = "DELETE FROM public.sports_list WHERE id=?";
            ps = con.prepareStatement(deleteQry);

            ps.setInt(1, 10);

            int deleteRows = ps.executeUpdate();
            System.out.println("Deleted Rows: " + deleteRows);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
