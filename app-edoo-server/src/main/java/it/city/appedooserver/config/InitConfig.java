package it.city.appedooserver.config;

import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import java.util.Properties;

public class InitConfig {

    public static boolean isStart() {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("/application.properties").getInputStream());
            if (props.getProperty("spring.jpa.hibernate.ddl-auto").equals("update")) {
                return true;
            } else {
                String confirm = JOptionPane.showInputDialog("Ma'lumotlarni o'chirib yuborma! Keyin bilmay qoldim dema! Agar rostdan ham o'chirmoqchi bo'lsang. O'chirish kodi (Ketmon) :");
                if (confirm != null && confirm.equals("Ketmon")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
