import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * Created on 08.05.2005
 * JImagePanel.java
 * @author Mark Homner www.JDevCom.de
 */

class JImagePanel extends JPanel {
	ImageIcon icon = null;
	JDCVeo veoApplet;
	long lNew;
	long lOld;
	long lDiff;
	double dDiff;
	GradientPaint gradientPaintBg = null;
	private FontMetrics fm;
	private final Color cGray = new Color(200, 200, 200);
	private final String ERROR_LOGIN_DATA = "INVALID LOGIN DATA !";
	private final String ERROR_LOGIN = "CHECK LOGIN DATA !";
	
	public void setIcon(ImageIcon img){
		this.icon = img;
		this.repaint();
	}
		
	public JImagePanel(JDCVeo applet){
	    this.veoApplet = applet;
		StringBuffer sbTooltip = new StringBuffer();
        sbTooltip.append("<html>");
        sbTooltip.append("&nbsp;powered by <b>JDevCom</b> <sup style='font-size:11pt'>&copy;2005</sup>&nbsp;");
        sbTooltip.append("<BR>&nbsp;<b>J</b>ava <b>Dev</b>elopment Of <b>Com</b>ponents&nbsp;");
        sbTooltip.append("</html>");
        this.setToolTipText(sbTooltip.toString());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setSize(new Dimension(320,240));
	}
		
	public void paintComponent(Graphics g){
	    if(gradientPaintBg == null)
		    gradientPaintBg = new GradientPaint(0,this.getHeight(),Color.WHITE,this.getWidth(),this.getHeight(),
				cGray);
	    
	    if(!this.veoApplet.isLoginOk()) {
	        if(!this.veoApplet.isLoginDataOk())
	            paintErrorLoginData(g);
	        else
	            paintErrorLogin(g);
	        return;
	    }
	    if(this.icon != null)
			icon.paintIcon(this, g, (this.getWidth()-icon.getIconWidth())/2, (this.getHeight()-icon.getIconHeight())/2);
	    lNew = System.currentTimeMillis();
	    lDiff = lNew - lOld;
	    lDiff = (lDiff / 100);
	    if(lDiff != 0)
	        dDiff = 60.0 / lDiff;
	    
	    g.setColor(Color.RED);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.drawString((Math.rint(dDiff*100)/100. )+ " fps",0 ,this.getHeight()-4);
	    lOld = lNew;
	}
	
	private void paintErrorLoginData(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
								  RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(gradientPaintBg);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setFont(new Font("Tahoma",Font.BOLD,24));
		this.fm = g2.getFontMetrics();
		int iPosX = (this.getSize().width-fm.stringWidth(this.ERROR_LOGIN_DATA))/2;
		g2.setColor(Color.GRAY);
		g2.drawString(ERROR_LOGIN_DATA, iPosX,(this.getHeight())/2);
	}
	
	private void paintErrorLogin(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
								  RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(gradientPaintBg);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.setFont(new Font("Tahoma",Font.BOLD,24));
		this.fm = g2.getFontMetrics();
		int iPosX = (this.getSize().width-fm.stringWidth(this.ERROR_LOGIN))/2;
		g2.setColor(Color.GRAY);
		g2.drawString(ERROR_LOGIN, iPosX,(this.getHeight())/2);
	}
	
	
}