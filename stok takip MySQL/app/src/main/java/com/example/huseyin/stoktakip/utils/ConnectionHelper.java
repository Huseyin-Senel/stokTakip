package com.example.huseyin.stoktakip.utils;

import android.os.StrictMode;
import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionHelper {
    public static Connection con = null;
    private static Statement st = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;


    public Connection connectionclass(){
        if(con==null){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection("jdbc:mysql://****IP Adress*****/stoktakip", "*****database user name****", "*****database password*****");
            }
            catch (Exception ex) {
                Log.e("connectionclass: ",ex.getMessage());
            }
        }
        return  con;
    }

    public int imageQuery(String query, byte[] resim) {
        Connection con;
        int result=-1;

        pstmt = null;
        try {
            con = connectionclass();
            if(con!=null){
                pstmt = con.prepareStatement(query);
                pstmt.setBinaryStream(1,new ByteArrayInputStream(resim),(int)resim.length);
                result = pstmt.executeUpdate();
            }
        }
        catch(Exception ex){
            Log.e("query: ",ex.getMessage());
        }
        finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException sqlEx) { } // ignore

                pstmt = null;
            }
            return result;
        }
    }

    public int intQuery(String query) {
        Connection con;
        int result=-1;

        st = null;
        try {
            con = connectionclass();
            if(con!=null){
                st = con.createStatement();
                result = st.executeUpdate(query);
            }
        }
        catch(Exception ex){
            Log.e("query: ",ex.getMessage());
        }
        finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException sqlEx) { } // ignore

                st = null;
            }
            return result;
        }
    }

    public List<Map<String, Object>> tableQuery(String query) {
        Connection con;

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = null;

        st = null;
        rs = null;
        try {
            con = connectionclass();
            if(con!=null){
                st = con.createStatement();
                rs = st.executeQuery(query);

                ResultSetMetaData metaData = rs.getMetaData();
                Integer columnCount = metaData.getColumnCount();

                while (rs.next()){
                    row = new HashMap<String, Object>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    resultList.add(row);
                }
            }
        }
        catch(Exception ex){
            Log.e("query: ",ex.getMessage());
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException sqlEx) { } // ignore

                st = null;
            }
            return resultList;
        }
    }
}
