package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import views.JFrameMain;
import views.dialogs.JNotificationDialog;

/**
 *
 * @author USER
 */
public class Service {
    
    private JFrameMain jFrameMain;

    public Service(JFrameMain jFrameMain) {
        this.jFrameMain = jFrameMain;
    }
    
    public String getDate(){
        LocalDate now = LocalDate.now();
        Locale spanishDate = new Locale("es", "ES");
        return now.format(DateTimeFormatter.ofPattern("'Hoy es' EEEE dd 'de' MMMM 'de' yyyy")).toUpperCase();
    }
    
    public void initNotification(String chain, int type){
        JNotificationDialog jmd = new JNotificationDialog(jFrameMain, true, type);
        jmd.getjLabel3().setText(chain);
        jmd.setVisible(true);
        
    }
    
}
