package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JTextField fld_username_login;
    private JTextField fld_password_login;
    private JButton btn_login;
    private JLabel lbl_username_login;
    private JLabel lbl_password_login;

    public LoginGUI(){
        add(wrapper);
        setSize(450,400);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_username_login) || Helper.isFieldEmpty(fld_password_login)){
                    Helper.showMessage("fill");
                }else{
                    User u = new User();
                    u = User.getchFetch(fld_username_login.getText(),fld_password_login.getText()) ;

                    if(u==null){
                        Helper.showMessage("Kullanıcı Bulunamadı");
                    }else{
                        switch(u.getTypeId()){
                            case 1:
                                Helper.showMessage("Educator");
                                dispose();
                                break;
                            case 2:
                                OperatorGUI opGUI = new OperatorGUI((Operator) u);
                                dispose();
                                break;
                            case 3:
                                Helper.showMessage("Student");
                                dispose();
                                break;


                        }
                    }
                }
            }

        });
    }

    public static void main(String[] args){
        LoginGUI login = new LoginGUI();
    }












}
