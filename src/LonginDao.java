

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LonginDao {
	 public static Taikhoan getData(String user, String pass) {
	        try {
	            Taikhoan taikhoan = null;
	            Connection connection = DatabaseHepler.openConnection();
	            String sql = "select * from Dangnhap where Taikhoan like ? And Matkhau like ?";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            statement.setString(1, user);
	            statement.setString(2, pass);
	            ResultSet rs = statement.executeQuery();
	            while ((rs.next())){
	                taikhoan  = new Taikhoan();
	                taikhoan.setTaikhoan(rs.getString("Taikhoan"));
	                taikhoan.setMatkhau(rs.getString("Matkhau"));
	            }

	            connection.close();
	            statement.close();
	            return taikhoan;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	      return null;
	    }

}
