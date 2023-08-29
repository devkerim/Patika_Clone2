package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel wtop;
    private JLabel lbl_welcome;
    private JButton btn_exit;
    private JPanel pnl_user_list;
    private JTable tbl_user_list;
    private JScrollPane scrl_user_list;
    private JTextField fld_fullname;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JComboBox cmb_user_role;
    private JButton btn_add_user;
    private JTextField fld_delete_userid;
    private JButton btn_delete_user;
    private JTextField fld_srch_name;
    private JTextField fld_search_username;
    private JComboBox cmb_srch_role_id;
    private JButton btn_srch;
    private JPanel pnl_patika;
    private JScrollPane scrl_patika;
    private JTable tbl_patika_list;
    private JTextField fld_patika_add;
    private JButton btn_patika_add;
    private JPanel pnl_patika_add;
    private JPanel pnl_course;
    private JScrollPane scrl_course;
    private JTable tbl_course_list;
    private JTextField fld_course_add_name;
    private JTextField fld_course_add_lang;
    private JComboBox cmb_course_add_educator;
    private JComboBox cmb_course_add_patika;
    private JButton btn_course_add;
    private JLabel lbl_course_add_name;
    private JLabel lbl_course_add_lang;
    private JLabel lbl_course_add_educator;
    private JLabel lbl_course_add_patika;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;


    public OperatorGUI(Operator operator){
        Helper.setLayout();
        add(wrapper);
        setSize(1000,700);
        int x = Helper.screenCenterPoint("x",getSize());
        int y = Helper.screenCenterPoint("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        lbl_welcome.setText("Hoşgeldin ");


        ///USER List///
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0 || column==5){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID","AD SOYAD","KULLANICI ADI","PAROLA","ROL"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        loadUserModel();


        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                Role obj = Role.getchFetchByName(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString());
                int user_type = obj.getId();
                if(User.update(user_id,user_name,user_uname,user_password,user_type)){
                    Helper.showMessage("done");
                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_delete_userid.setText(selected_user_id);
            }
            catch(Exception exception){
                exception.getMessage();
            }
        });
        ///USER List///


        ///PATIKA List///
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleceted_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                UpdatePatikaGUI up = new UpdatePatikaGUI(getchFetch(seleceted_id));
                up.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadPatikaModel();
                        loadPatikaCombo();
                        loadCourseModel();
                    }
                });
            }
        });


        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.confirm("sure")){
                    int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                    if (Patika.delete(selected_id)) {
                        Helper.showMessage("done");
                        loadPatikaModel();
                        loadPatikaCombo();
                        loadCourseModel();
                    }else{
                        Helper.showMessage("error");
                    }

                }
            }
        });



        mdl_patika_list =new DefaultTableModel();
        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();


        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.getColumnModel().getColumn(0).setMinWidth(75);
        ///PATIKA List///


        ///Course List///
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID","DERS ADI", "PROGRAMLAMA DİLİ", "PATİKA", "Eğitmen Adı"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getColumnModel().getColumn(0).setMinWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadEducatorCombo();
        ///Course List///


        btn_add_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isComboBoxEmpty(cmb_user_role) || Helper.isFieldEmpty(fld_fullname) || Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_password)){
                    Helper.showMessage("fill");

                }else{
                    String name = fld_fullname.getText();
                    String username = fld_username.getText();
                    String password = fld_password.getText();
                    int typeID = cmb_user_role.getSelectedIndex();
                    if(User.add(name,username,password,typeID)){
                        Helper.showMessage("done");
                        loadUserModel();
                        loadPatikaCombo();
                        loadEducatorCombo();
                        fld_fullname.setText(null);
                        fld_username.setText(null);
                        fld_password.setText(null);
                    }
                }
            }
        });

        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI login = new LoginGUI();
            }
        });
        btn_delete_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_delete_userid)){
                    Helper.showMessage("fill");
                }
                else{
                    if(Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_delete_userid.getText());

                    if(User.delete(user_id)){
                        Helper.showMessage("done");
                        fld_delete_userid.setText(null);
                        loadUserModel();
                        loadPatikaCombo();
                        loadEducatorCombo();
                        loadCourseModel();
                    }
                    else{
                        Helper.showMessage("error");

                    }
                    }


                }
            }
        });
        btn_srch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = fld_srch_name.getText();
                String userName = fld_search_username.getText();
                int typeId = cmb_srch_role_id.getSelectedIndex();
                String query = User.searchQuery(name,userName,typeId);
                ArrayList<User> searchingUser = User.searchUserList(query);
                loadUserModel(searchingUser);
            }
        });
        btn_patika_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_patika_add)){
                    Helper.showMessage("fill");
                }
                else {
                    if (Patika.add(fld_patika_add.getText())){
                        Helper.showMessage("done");
                        loadPatikaModel();
                        loadPatikaCombo();
                        fld_patika_add.setText(null);
                    }else{
                        Helper.showMessage("error");
                    }

                }
            }
        });
        btn_course_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item patikaItem = (Item) cmb_course_add_patika.getSelectedItem();
                Item userItem = (Item) cmb_course_add_educator.getSelectedItem();
                if(Helper.isFieldEmpty(fld_course_add_name) || Helper.isFieldEmpty(fld_course_add_lang)){
                    Helper.showMessage("fill");
                }else{
                    if(Course.add(fld_course_add_name.getText(),userItem.getKey(),patikaItem.getKey(),fld_course_add_lang.getText())){
                        Helper.showMessage("done");
                    }else{
                        Helper.showMessage("error");

                    }
                    fld_course_add_name.setText(null);
                    fld_course_add_lang.setText(null);
                    loadCourseModel();
                }
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for(Course obj: Course.getlist()){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Patika obj: Patika.getlist()){
            i=0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public static void main(String[] args){
        Helper.setLayout();
        Operator op = new Operator();
        OperatorGUI opgui = new OperatorGUI(op);
    }


    public void loadUserModel(ArrayList<User> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(User obj:list){
            i=0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getRole().getName();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for(User obj:User.getlist()){
            int i=0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getRole().getName();
            mdl_user_list.addRow(row_user_list);
        }
    }


    public static Patika getchFetch(int id){
        Patika obj = null;
        String query = "SELECT * FROM PATIKA WHERE ID = ?";

        try {
            PreparedStatement ps = DBConnector.getInstance().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public void loadPatikaCombo(){
        cmb_course_add_patika.removeAllItems();
        for(Patika obj:Patika.getlist()){

            cmb_course_add_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_course_add_educator.removeAllItems();
        for(User obj: User.getlist()){
            if(obj.getTypeId()==1){
                cmb_course_add_educator.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }



}
