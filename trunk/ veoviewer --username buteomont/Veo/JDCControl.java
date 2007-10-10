/*
 * Created on 07.05.2005
 * JDCControlPanel.java
 * @author Mark Homner
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.halo3.veo.Veo;

public class JDCControl extends JPanel implements MouseListener{
    JDCVeo veoApplet;
    JButton[] aButton;
    private final Color cGray = new Color(200, 200, 200);
    private String sPowerdBy = "powered by www.JDevCom.de"; // Do not remove
    boolean bShowControlPanel = false;
    private FontMetrics fm;
    private ImageIcon controlIcon;
    GradientPaint gradientPaintBg = null;
    GradientPaint gradientPaintText = null;
    private String sDate;
    Font font = new Font("Tahoma",Font.BOLD,12);
    
    public JDCControl(JDCVeo applet) {
        
        this.veoApplet = applet;
        
        sDate = dateToString(new GregorianCalendar(), false);
        this.setLayout(null);
        this.setSize(320, 50);
        //this.addMouseListener(this);
        if(!this.veoApplet.isLoginDataOk()) return;
        controlIcon = new ImageIcon(this.getClass().getResource("control.gif"));
        aButton = new JButton[4];
        aButton[0] = new JButton();
        aButton[1] = new JButton();
        aButton[2] = new JButton();
        aButton[3] = new JButton();
        initButtons();
    }
    
    public void mouseClicked(MouseEvent evt) {
        if(!evt.getSource().getClass().getName().equals("javax.swing.JButton"))
            return;
        
        int iDirection = Integer.parseInt(((JButton)evt.getSource()).getActionCommand());
        byte full = (byte)(evt.getClickCount()== 2 ? 0x1 : 0x0);
        switch(iDirection) {
	        case 0 : moveCamera(Veo.VEO_MOVE_UP | full);break;
	        case 1 : moveCamera(Veo.VEO_MOVE_RIGHT | full);break;
	        case 2 : moveCamera(Veo.VEO_MOVE_DOWN | full);break;
	        case 3 : moveCamera(Veo.VEO_MOVE_LEFT | full);break;
        }
    }
    
    private void moveCamera(int direction) {
        try {
            if(this.veoApplet.veo != null)
                this.veoApplet.veo.moveCamera((byte)direction);
        } 
        catch (IOException e) {};
    }
   
    public void mouseEntered(MouseEvent arg0) {
    }
    
    public void mouseExited(MouseEvent arg0) {
    }
    
    public void mousePressed(MouseEvent arg0) {
    }
       
    public void mouseReleased(MouseEvent arg0) {
    }
    
    public String dateToString(GregorianCalendar date,boolean shortweekdays){ 
		int month = date.get(Calendar.MONTH)+ 1;
		int day =  date.get(Calendar.DAY_OF_MONTH);
		String s;
		if(shortweekdays)
			s = (new DateFormatSymbols(Locale.UK)).getShortWeekdays()[date.get(Calendar.DAY_OF_WEEK)] + ", ";
		else
			s = (new DateFormatSymbols(Locale.UK)).getWeekdays()[date.get(Calendar.DAY_OF_WEEK)] + ", ";
		s += (day < 10 ? "0"+day : ""+day) + "." + (month < 10 ? "0"+month : ""+month) +"." +date.get(Calendar.YEAR);
		return s;
	}
    
    private void initButtons() {
        int left = 260;
        int top = 0;
        aButton[0].setBounds(left + 24,top + 0,14,14);
        aButton[1].setBounds(left + 48,top + 24,14,14);
        aButton[2].setBounds(left + 24,top + 48,14,14);
        aButton[3].setBounds(left + 0,top + 24,14,14);
        JButton b;
        Insets insets = new Insets(0,0,0,0);
        for(int i=0; i < aButton.length; i++) {
            b = aButton[i];
            b.setMargin(insets);
            b.setRolloverEnabled(false);
            b.setActionCommand("" + i);
            b.addMouseListener(this);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setOpaque(false);
            b.setBorderPainted(false);
            b.setBackground(new Color(Color.TRANSLUCENT));
            b.setFocusable(false);
            b.setContentAreaFilled(false);
            this.add(b);
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
								  RenderingHints.VALUE_ANTIALIAS_ON);
		if(gradientPaintBg == null)
		    gradientPaintBg = new GradientPaint(0,this.getHeight(),Color.WHITE,this.getWidth(),this.getHeight(),
				cGray);
		if(gradientPaintText == null)
		    gradientPaintText =new GradientPaint(0,this.getHeight(), Color.BLUE, this.getWidth(), this.getHeight(),
	           Color.RED );
		
		g2.setPaint(gradientPaintBg);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setFont(font);
		fm = g.getFontMetrics(g.getFont());
		
		g2.setPaint(gradientPaintText);
		
		int x = (this.getSize().width-fm.stringWidth(this.sPowerdBy))/2 - 25;
		g2.drawString(this.sPowerdBy, x,(this.getHeight())/2 - 10);
		
		x = (this.getSize().width-fm.stringWidth(this.sDate))/2 - 25;
		g2.drawString(this.sDate, x,(this.getHeight())/2 +10);
		
		if(!this.veoApplet.isLoginOk()) return;
		this.controlIcon.paintIcon(this, g2, this.getWidth()-controlIcon.getIconWidth(),0);
	 }
    
    public void update(Graphics g) {
        paintComponent(g);
    }
}
