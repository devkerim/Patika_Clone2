package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int userId;
    private int patikaID;
    private String name;
    private String lang;

    private Patika patika;
    private User educator;



    public Course(int id, int userId, int patikaId, String name, String lang){
        this.id=id;
        this.lang=lang;
        this.name=name;
        this.patikaID=patikaId;
        this.userId=userId;

        this.patika= Patika.getchFetch(this.patikaID);
        this.educator = User.getchFetch(this.userId);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPatikaID() {
        return patikaID;
    }

    public int getUserId() {
        return userId;
    }

    public String getLang() {
        return lang;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setPatikaID(int patikaID) {
        this.patikaID = patikaID;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Patika getPatika() {
        return patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }



    public static ArrayList<Course> getlist(){

        ArrayList<Course> courseArrayList = new ArrayList<>();
        String query = "SELECT * FROM Course";
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("ID");
                int user_id = rs.getInt("User_Id");
                int patika_id = rs.getInt("Patika_Id");
                String course_name = rs.getString("Name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id,patika_id, course_name, lang);
                courseArrayList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseArrayList;
    }

    public static boolean add(String name, int user_id, int patika_id, String lang){
        String query = "INSERT INTO COURSE(Name,User_Id,Patika_ID,Lang) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,user_id);
            pr.setInt(3,patika_id);
            pr.setString(4,lang);
            return pr.executeUpdate() !=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Course> getlistByUser(int user_id){

        ArrayList<Course> courseArrayList = new ArrayList<>();
        String query = "SELECT * FROM Course Where User_ID = "+user_id;
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("ID");
                int userID = rs.getInt("User_Id");
                int patika_id = rs.getInt("Patika_Id");
                String course_name = rs.getString("Name");
                String lang = rs.getString("lang");
                obj = new Course(id, user_id,patika_id, course_name, lang);
                courseArrayList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseArrayList;
    }

    public static ArrayList<Course> getlistByPatika(int patika_id){

        ArrayList<Course> courseArrayList = new ArrayList<>();
        String query = "SELECT * FROM Course Where Patika_Id = "+patika_id;
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("ID");
                int userID = rs.getInt("User_Id");
                int patikaID = rs.getInt("Patika_Id");
                String course_name = rs.getString("Name");
                String lang = rs.getString("lang");
                obj = new Course(id,patikaID,patika_id, course_name, lang);
                courseArrayList.add(obj);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseArrayList;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM Course WHERE ID = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            return ps.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
