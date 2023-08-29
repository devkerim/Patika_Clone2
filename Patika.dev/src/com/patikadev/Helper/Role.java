package com.patikadev.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Role {
    private int id;
    private String name;



    public Role(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public static Role getchFetch(int id){
        Role obj = null;
        String query = "SELECT * FROM ROLES WHERE ID = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id_role = rs.getInt("ID");
                String name = rs.getString("ROLENAME");
                obj = new Role(id_role,name);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static Role getchFetchByName(String name){
        Role obj = null;
        String query = "SELECT * FROM ROLES WHERE ROLENAME = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id_role = rs.getInt("ID");
                String role_name = rs.getString("ROLENAME");
                obj = new Role(id_role,role_name);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }
}
