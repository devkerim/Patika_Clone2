package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private int typeId;
    private Role role;

    public User(){

    }

    public User(int id, String name, String username, String password, int typeId){
        this.id=id;
        this.name=name;
        this.password=password;
        this.username = username;
        this.typeId = typeId;

        this.role = Role.getchFetch(this.typeId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String usarname) {
        this.username = usarname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static boolean add(String name, String username, String password, int typeId){
        boolean key=true;
        String sql = "INSERT INTO USERS(NAME, USERNAME, PASSWORD, TYPEID) VALUES (?,?,?,?)";
        User findUser = User.getchFetch(username);
        if (findUser!=null){
            Helper.showMessage("duplicate");
            return false;
        }
        try {
            PreparedStatement prs = DBConnector.getInstance().prepareStatement(sql);
            prs.setString(1,name);
            prs.setString(2,username);
            prs.setString(3,password);
            prs.setInt(4,typeId);
            int response = prs.executeUpdate();
            if(response==-1){
                Helper.showMessage("error");
            }

            key = response !=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return key;
    }





    public static ArrayList<User> getlist(){
    ArrayList<User> userlist = new ArrayList<>();
    String query = "SELECT * FROM USERS";
    User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String uname = rs.getString("Username");
                int typeID = rs.getInt("TypeID");
                obj = new User(id,name,uname,password,typeID);
                userlist.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userlist;


    }
    public static User getchFetch(String username, String password){
        User obj = null;
        String query = "SELECT * FROM USERS WHERE USERNAME = ? AND Password = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                switch(rs.getInt("TypeID")){
                    case 2:
                        obj = new Operator();
                        break;
                    default:
                        obj = new User();
                        break;
                }


                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUsername(rs.getString("Username"));
                obj.setPassword(rs.getString("Password"));
                obj.setTypeId(rs.getInt("TypeID"));




            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getchFetch(String username){
        User obj = null;
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                obj = new User();
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String uname = rs.getString("Username");
                int typeID = rs.getInt("TypeID");
                obj = new User(id,name,uname,password,typeID);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public static User getchFetch(int id){
        User obj =null;
        String query = "SELECT * FROM USERS WHERE ID = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                obj = new User();
                int id_d = rs.getInt("id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String uname = rs.getString("Username");
                int typeID = rs.getInt("TypeID");
                obj = new User(id_d,name,uname,password,typeID);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }


    public static boolean delete(int id){
        String query = "DELETE FROM USERS WHERE ID = ?";
        ArrayList<Course> courseList = Course.getlistByUser(id);
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



    public static boolean update(int id,String name, String uname, String pass, int type){
        String query = "UPDATE USERS SET NAME=?, USERNAME = ?, PASSWORD = ?, TYPEID = ? WHERE ID =?";
        User findUser = User.getchFetch(uname);
        if (findUser!=null && findUser.getId()!=id){
            Helper.showMessage("duplicate");
            return false;
        }
        if(type!=1 && type!=2 && type!=3){
            Helper.showMessage("err_role");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setInt(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userlist = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){

                int id = rs.getInt("id");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String uname = rs.getString("Username");
                int typeID = rs.getInt("TypeID");
                obj = new User (id,name,uname,password,typeID);
                userlist.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userlist;
    }


    public static String searchQuery(String name,String username, int typeId){

        String query = "SELECT  * FROM USERS WHERE USERNAME LIKE '%{{username}}%' AND NAME LIKE '%{{name}}%'";
        if(typeId!=0){query=query+"AND TYPEID LIKE '%"+typeId+"%'";}


        query = query.replace("{{username}}",username);
        query = query.replace("{{name}}",name);
        return query;
    }



}
