/*## Do not remove this comment block ##
 * Created on May 13, 2005
 * @author  Mark Homner www.JDevCom.de
 * @Version 0.1 
 * 
 */
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JApplet;


import net.halo3.veo.Veo;
import net.halo3.veo.VeoImage;
import net.halo3.veo.VeoImageHandler;

public class JDCVeo extends JApplet implements VeoImageHandler {

    private boolean bLoginDataOK;
    private boolean bLoginOK;
    private String sHost = "10.10.6.91";
    private int iPort;
    private String sUser = "admin";
    private String sPassword = "password";
    
    public Veo veo = null;
    private JImagePanel imgPanel;

    public void init() {
        Container container = this.getContentPane();
        container.setSize(new java.awt.Dimension(320, 290));
               
        this.imgPanel = new JImagePanel(this);
        
        this.bLoginDataOK = checkLoginData();
        this.bLoginOK = this.bLoginDataOK;
        this.getContentPane().setLayout(null);
        imgPanel.setBounds(0, 0, 320, 240);
        this.getContentPane().add(BorderLayout.PAGE_START, imgPanel);
                
        JDCControl controlPanel = new JDCControl(this);
        controlPanel.setBounds(0, 240, 320, 60);
        this.getContentPane().add(controlPanel);
        if(this.bLoginDataOK)
            login();
    }
    
    public boolean isLoginDataOk() {
        return this.bLoginDataOK;
    }
    
    public boolean isLoginOk() {
        return this.bLoginOK;
    }

    private boolean checkLoginData() {
        boolean bRetValue = false;
        try {
            this.sHost = getParameter("host");
            this.sUser = getParameter("user");
            this.sPassword = getParameter("password");
            this.iPort = Integer.parseInt(getParameter("port"));
            bRetValue = true;
        } catch (Exception e) {}
        return bRetValue;
    }

    private void login() {
        try {
            veo = new Veo(this.sHost, this.iPort);
            
            if (veo.login(this.sUser, this.sPassword)) {
                veo.stopStream(); // in case it was already running;
                veo.startStream();
                veo.selectStream(Veo.VEO_STREAM_320x240, 1);
                veo.setImageHandler(this);
                this.bLoginOK = true;
            } 
            else {
                this.bLoginOK = false;
            }
                
        } catch (Exception exc) {
         System.out.println(exc.getMessage());   
        }
    }

    public void processImage(VeoImage image) {
//        float fDiff;	
        this.imgPanel.setIcon(new ImageIcon(image.getImage()));
    }

    public void start() {
    }

    public void stop() {
        try {
            veo.stopStream();
            veo.shutdown();
        } catch (Exception e) {};
    }
}