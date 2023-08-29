package com.patikadev.Helper;


import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int screenCenterPoint(String eksen, Dimension size){

        int point = 0;
        switch (eksen){
        case "x":
            point = (Toolkit.getDefaultToolkit().getScreenSize().width- size.width)/2;
            break;
        case "y":
            point = (Toolkit.getDefaultToolkit().getScreenSize().height- size.height)/2;
            break;
        default:
            point = 0;


        }

       return point;
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static boolean isComboBoxEmpty(JComboBox box){
        return box.getSelectedIndex()==0;
    }


    public static void showMessage(String str){
        otionPaneTR();
        String message;
        String title;
        switch(str) {
            case "fill":
                message = "Lütfen gerekli tüm alanları doldurunuz.";
                title = "Hata";
                break;
            case "done":
                message = "İşlem başarılı";
                title = "Sonuç";
                break;
            case "error":
                message = "Kullanıcı eklenemedi";
                title = "Hata";
                break;
            case "duplicate":
                message = "Bu kullanıcı adı daha önce alınmış. Lütfen farklı bir kullanıcı adı seçiniz.";
                title = "Hata";
                break;
            case "err_role":
                message = "Lütfen geçerli bir rol numarası giriniz.\n1-Educator\n2-Operator\n3-Student";
                title="hata";
                break;
            default:
                message=str;
                title = "Mesaj";
        }
        JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        otionPaneTR();
        String message = null;
        switch(str){
            case "sure":
                message = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
                break;
            default:
                message = str;
        }
        return JOptionPane.showConfirmDialog(null,message,"Son Kararın mı?",JOptionPane.YES_NO_OPTION)==0;

    }



    public static void otionPaneTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");

    }








}
