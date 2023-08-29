package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;


    public Patika(int id, String name){
        this.id = id ;
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


    public static ArrayList<Patika> getlist(){

        ArrayList<Patika> patikaArrayList = new ArrayList<>();
        String query = "SELECT * FROM PATIKA";
        Patika obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                obj = new Patika(rs.getInt("ID"),rs.getString("NAME"));
                patikaArrayList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patikaArrayList;
    }

    public static boolean add(String name){
        String query = "INSERT INTO PATIKA (NAME) VALUES (?)";
        try {
            PreparedStatement prs = DBConnector.getInstance().prepareStatement(query);
            prs.setString(1,name);
            return prs.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }





    public static boolean update(String name, int id){
        String query = "UPDATE PATIKA SET NAME = ? WHERE ID = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }


    public static boolean delete(int id){
        String query = "DELETE FROM PATIKA WHERE ID = ?";
        ArrayList<Course> courseList = Course.getlistByPatika(id);
        for(Course c: courseList){
            Course.delete(c.getId());
        }
        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);

            return ps.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;

    }


    public static Patika getchFetch(int id){
        Patika obj = null;
        String query = "SELECT * FROM PATIKA WHERE ID = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                obj = new Patika(rs.getInt("id"),rs.getString("Name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }




}
