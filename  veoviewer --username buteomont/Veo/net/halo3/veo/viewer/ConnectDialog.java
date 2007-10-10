package net.halo3.veo.viewer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by a for-profit company or business) then you should purchase a license -
 * please visit www.cloudgarden.com for details.
 */
public class ConnectDialog extends javax.swing.JDialog {
    private JPanel panelButton;

    private boolean canceled;

    private JButton btnCancel;

    private JButton btnOK;

    private JLabel lblPort;

    private JLabel lblPassword;

    private JLabel lblUser;

    private JLabel lblHost;

    private JTextField txtPort;

    private JTextField txtHost;

    private JTextField txtPassword;

    private JTextField txtUser;

	private JPanel contentjPanel = null;

	private JCheckBox autoLoginCheckBox = null;

    public ConnectDialog(JFrame owner) {
        super(owner, "Connect to Veo Camera", true);
        canceled = false;
        initGUI();
		initialize();
    }

    /**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(215, 237));
        this.setTitle("Log In");
        this.setContentPane(getContentjPanel());
			
	}

	public String getUser() {
        return txtUser.getText();
    }

    public String getPassword() {
        return txtPassword.getText();
    }

    public String getPort() {
        return txtPort.getText();
    }

    public String getHost() {
        return txtHost.getText();
    }

    public boolean wasCanceled() {
        return canceled;
    }

    /**
     * Initializes the GUI. Auto-generated code - any changes you make will
     * disappear.
     */
    public void initGUI() {
        try {
            txtUser = new JTextField();
            txtPassword = new JTextField();
            txtHost = new JTextField();
            txtPort = new JTextField();
            lblHost = new JLabel();
            lblUser = new JLabel();
            lblPassword = new JLabel();
            lblPort = new JLabel();
            panelButton = new JPanel();
            btnOK = new JButton();
            btnCancel = new JButton();

            this.setResizable(false);
            this.setSize(new java.awt.Dimension(287, 265));

            txtUser.setColumns(15);
            txtPassword.setColumns(15);
            txtHost.setColumns(15);
            txtPort.setColumns(15);
            lblHost.setText("Host");
            lblUser.setText("User");
            lblPassword.setText("Password");
            lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
            lblPassword.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
            lblPort.setText("Port");
            btnOK.setText("Connect");
            btnOK.setVisible(true);
            panelButton.add(btnOK);
            btnOK.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    btnOKActionPerformed(evt);
                }
            });

            btnCancel.setText("Cancel");
            btnCancel.setVisible(true);
            panelButton.add(btnCancel);
            btnCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    btnCancelActionPerformed(evt);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Auto-generated event handler method */
    protected void btnCancelActionPerformed(ActionEvent evt) {
        canceled = true;
        dispose();
    }

    /** Auto-generated event handler method */
    protected void btnOKActionPerformed(ActionEvent evt) {

        dispose();
    }

    /**
     * @param userName
     */
    public void setUser(String userName) {
        txtUser.setText(userName);
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        txtPassword.setText(password);

    }

    /**
     * @param hostname
     */
    public void setHost(String hostname) {
        txtHost.setText(hostname);

    }

    /**
     * @param port
     */
    public void setPort(String port) {
        txtPort.setText(port);
    }

	/**
	 * This method initializes contentjPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentjPanel()
		{
		if (contentjPanel==null)
			{
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.gridy = 4;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.insets = new Insets(40, 0, 0, 0);
			gridBagConstraints8.gridy = 5;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.insets = new Insets(0, 3, 0, 3);
			gridBagConstraints2.ipadx = 6;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			contentjPanel=new JPanel();
			contentjPanel.setLayout(new GridBagLayout());
			contentjPanel.add(lblUser, gridBagConstraints);
			contentjPanel.add(txtUser, gridBagConstraints1);
			contentjPanel.add(lblPassword, gridBagConstraints2);
			contentjPanel.add(txtPassword, gridBagConstraints3);
			contentjPanel.add(lblHost, gridBagConstraints4);
			contentjPanel.add(txtHost, gridBagConstraints5);
			contentjPanel.add(lblPort, gridBagConstraints6);
			contentjPanel.add(txtPort, gridBagConstraints7);
			contentjPanel.add(panelButton, gridBagConstraints8);
			contentjPanel.add(getAutoLoginCheckBox(), gridBagConstraints11);
			}
		return contentjPanel;
		}

	/**
	 * This method initializes autoLoginCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	public JCheckBox getAutoLoginCheckBox()
		{
		if (autoLoginCheckBox==null)
			{
			autoLoginCheckBox=new JCheckBox();
			autoLoginCheckBox.setText("Auto-Login");
			}
		return autoLoginCheckBox;
		}
}  //  @jve:decl-index=0:visual-constraint="10,10"