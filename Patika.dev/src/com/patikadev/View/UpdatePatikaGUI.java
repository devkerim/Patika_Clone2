package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_patika_update;
    private JButton btn_patika_update;
    private JLabel lbl_patika_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika){
        add(wrapper);

        setSize(300,200);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        fld_patika_update.setText(patika.getName());
        btn_patika_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_patika_update)){
                    Helper.showMessage("fill");
                }
                else{
                    if(Patika.update(fld_patika_update.getText(),patika.getId())){
                        Helper.showMessage("done");
                    }
                    else{
                        Helper.showMessage("Error");
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args){
        Helper.setLayout();

    }





}
