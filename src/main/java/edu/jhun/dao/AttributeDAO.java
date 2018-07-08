package edu.jhun.dao;

import edu.jhun.utils.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by hgw on 2018/7/3.
 */
public class AttributeDAO  extends BaseDAO{
    private Connection connection = DBHelper.getConnection();
    public String AttributeResult(String mumberID) {
        String sql = "SELECT PARA_LABEL, PARA_ID from model_para_info " +
                " WHERE PARA_TYPE=\'output_para\' AND MUID = ?";
        ResultSet rs = null;
        rs= query(connection,sql,mumberID);
        String Attributelist="";
        if (rs != null) {
            try {
                while (rs.next()) {
                    Attributelist+=rs.getString("PARA_LABEL")+"-"+rs.getString("PARA_ID")+";";
                }
                return Attributelist;
            } catch (SQLException e) {
                e.printStackTrace();
                return "AttributelistIsNull";
            }
        }
        return "AttributelistIsNull";

    }
}
