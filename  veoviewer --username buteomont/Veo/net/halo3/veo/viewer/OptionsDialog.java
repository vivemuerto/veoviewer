package net.halo3.veo.viewer;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import sun.beans.editors.FontEditor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JComboBox;

import org.buteomont.util.Pulse;

public class OptionsDialog extends JDialog
	{

	private static final long	serialVersionUID	=1L;
	private JPanel				jContentPane		=null;
	private JCheckBox enableServerCheckBox = null;
	private JPanel imageServerOptionsPanel = null;
	private JLabel serverPortOptionLabel = null;
	private JTextField serverPortTextField = null;
	private JPanel portOptionPanel = null;
	private JButton portOptionsOkButton = null;
	private JPanel optionsButtonsPanel = null;
	private JButton optionsCancelButton = null;
	private int serverPortNumber=0;
	private boolean cancelled=false;
	private boolean serverEnabled=false;
	private VeoViewer mainWindow=null;
	private JTabbedPane imageServerOptionsTabbedPane = null;
	private JPanel commonOptionPanel = null;
	private JPanel timestampOptionsPanel = null;
	private JCheckBox timestampOptionCheckBox = null;
	private JButton timestampColorButton = null;
	private FontEditor fontEditor = null;
	private JLabel fontLabel = null;
	private Font timestampFont;  //  @jve:decl-index=0:
	private JCheckBox allowMotionCheckBox = null;
	private JPanel autoMotionPanel = null;
	private JCheckBox autoHorizontalEnableCheckBox = null;
	private JLabel hRateLabel = null;
	private JComboBox autoVerticalRateComboBox = null;
	private JComboBox autoHorizontalRateComboBox = null;
	private JLabel vRateLabel = null;
	private JCheckBox autoVerticalEnableCheckBox = null;
	private boolean autoHorzMotion=false;
	private int autoHorzRate=0;
	private boolean autoVertMotion=false;
	private int autoVertRate=0;
	
	/**
	 * @param owner
	 */
	public OptionsDialog(Frame owner)
		{
		super(owner);
		setMainWindow((VeoViewer)owner);
		initialize();
		}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
		{
		this.setSize(373, 310);
		this.setTitle("Options");
		this.setContentPane(getJContentPane());
		Pulse hpulse=getMainWindow().getHorzPulse();
		Pulse vpulse=getMainWindow().getVertPulse();
		getAutoHorizontalEnableCheckBox().setSelected(hpulse!=null);
		getAutoVerticalEnableCheckBox().setSelected(vpulse!=null);
		if (hpulse!=null) getAutoHorizontalRateComboBox().setSelectedIndex(hpulse.getRate()-1);
		if (vpulse!=null) getAutoVerticalRateComboBox().setSelectedIndex(vpulse.getRate()-1);
		}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane()
		{
		if (jContentPane==null)
			{
			jContentPane=new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getImageServerOptionsTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getCommonOptionPanel(), BorderLayout.SOUTH);
			}
		return jContentPane;
		}

	/**
	 * This method initializes enableServerCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEnableServerCheckBox()
		{
		if (enableServerCheckBox==null)
			{
			enableServerCheckBox=new JCheckBox();
			enableServerCheckBox.setName("enableServerCheckBox");
			enableServerCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			enableServerCheckBox.setMnemonic(KeyEvent.VK_UNDEFINED);
			enableServerCheckBox.setText("Enable Image Server");
			enableServerCheckBox.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						serverPortOptionLabel.setEnabled(enableServerCheckBox.isSelected());
						getServerPortTextField().setEnabled(enableServerCheckBox.isSelected());
						}
				});
			}
		return enableServerCheckBox;
		}

	/**
	 * This method initializes imageServerOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getImageServerOptionsPanel()
		{
		if (imageServerOptionsPanel==null)
			{
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 4;
			gridBagConstraints21.gridheight = 1;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.anchor = GridBagConstraints.SOUTH;
			gridBagConstraints21.gridy = 2;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridwidth = 3;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.gridy = 0;
			serverPortOptionLabel = new JLabel();
			serverPortOptionLabel.setText("Web Port:");
			serverPortOptionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			serverPortOptionLabel.setPreferredSize(new Dimension(80, 15));
			serverPortOptionLabel.setEnabled(false);
			serverPortOptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			imageServerOptionsPanel=new JPanel();
			imageServerOptionsPanel.setLayout(new GridBagLayout());
			imageServerOptionsPanel.setName("imageServerOptionsPanel");
			imageServerOptionsPanel.add(getEnableServerCheckBox(), gridBagConstraints);
			imageServerOptionsPanel.add(getPortOptionPanel(), gridBagConstraints5);
			imageServerOptionsPanel.add(getTimestampOptionsPanel(), gridBagConstraints21);
			imageServerOptionsPanel.add(getAllowMotionCheckBox(), gridBagConstraints11);
			}
		return imageServerOptionsPanel;
		}

	/**
	 * This method initializes serverPortTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getServerPortTextField()
		{
		if (serverPortTextField==null)
			{
			serverPortTextField=new JTextField();
			serverPortTextField.setPreferredSize(new Dimension(40, 20));
			serverPortTextField.setEnabled(false);
			serverPortTextField.setHorizontalAlignment(JTextField.LEFT);
			}
		return serverPortTextField;
		}

	/**
	 * This method initializes portOptionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPortOptionPanel()
		{
		if (portOptionPanel==null)
			{
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(4);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridwidth = 2;
			portOptionPanel=new JPanel();
			portOptionPanel.setLayout(flowLayout);
			portOptionPanel.add(serverPortOptionLabel, null);
			portOptionPanel.add(getServerPortTextField(), null);
			}
		return portOptionPanel;
		}

	/**
	 * This method initializes portOptionsOkButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPortOptionsOkButton()
		{
		if (portOptionsOkButton==null)
			{
			portOptionsOkButton=new JButton();
			portOptionsOkButton.setText("Ok");
			portOptionsOkButton.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						setCancelled(false);
						setServerEnabled(getEnableServerCheckBox().isSelected());
						setServerPortNumber(Integer.parseInt(getServerPortTextField().getText()));
						setTimestampFont((Font)getFontEditor().getValue());
						dispose();
						updateMain();
						}
				});
			}
		return portOptionsOkButton;
		}

	protected void updateMain()
		{
		getMainWindow().collectOptions(this);
		}

	/**
	 * This method initializes optionsButtonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getOptionsButtonsPanel()
		{
		if (optionsButtonsPanel==null)
			{
			optionsButtonsPanel=new JPanel();
			optionsButtonsPanel.setLayout(new FlowLayout());
			optionsButtonsPanel.add(getOptionsCancelButton(), null);
			}
		return optionsButtonsPanel;
		}

	/**
	 * This method initializes optionsCancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOptionsCancelButton()
		{
		if (optionsCancelButton==null)
			{
			optionsCancelButton=new JButton();
			optionsCancelButton.setText("Cancel");
			optionsCancelButton.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						setCancelled(true);
						dispose();
						}
				});
			}
		return optionsCancelButton;
		}

	public boolean wasCancelled()
		{
		return cancelled;
		}

	public void setCancelled(boolean cancelled)
		{
		this.cancelled=cancelled;
		}

	public int getServerPortNumber()
		{
		return serverPortNumber;
		}

	public void setServerPortNumber(int serverPortNumber)
		{
		this.serverPortNumber=serverPortNumber;
		}

	public boolean isServerEnabled()
		{
		return serverEnabled;
		}

	public void setServerEnabled(boolean serverEnabled)
		{
		this.serverEnabled=serverEnabled;
		}

	public VeoViewer getMainWindow()
		{
		return mainWindow;
		}

	public void setMainWindow(VeoViewer mainWindow)
		{
		this.mainWindow=mainWindow;
		}

	public void show()
		{
		getEnableServerCheckBox().setSelected(isServerEnabled());
		getServerPortTextField().setText(getServerPortNumber()+"");
		serverPortOptionLabel.setEnabled(isServerEnabled());
		getServerPortTextField().setEnabled(isServerEnabled());
		pack();
		super.show();
		}

	/**
	 * This method initializes imageServerOptionsTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getImageServerOptionsTabbedPane()
		{
		if (imageServerOptionsTabbedPane==null)
			{
			imageServerOptionsTabbedPane=new JTabbedPane();
			imageServerOptionsTabbedPane.setName("Image Server");
			imageServerOptionsTabbedPane.addTab("Image Server", null, getImageServerOptionsPanel(), null);
			imageServerOptionsTabbedPane.addTab("Auto Motion", null, getAutoMotionPanel(), null);
			}
		return imageServerOptionsTabbedPane;
		}

	/**
	 * This method initializes commonOptionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCommonOptionPanel()
		{
		if (commonOptionPanel==null)
			{
			commonOptionPanel=new JPanel();
			commonOptionPanel.setLayout(new FlowLayout());
			commonOptionPanel.add(getOptionsButtonsPanel(), null);
			commonOptionPanel.add(getPortOptionsOkButton(), null);
			}
		return commonOptionPanel;
		}

	/**
	 * This method initializes timestampOptionsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTimestampOptionsPanel()
		{
		if (timestampOptionsPanel==null)
			{
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridy = 1;
			fontLabel = new JLabel();
			fontLabel.setText("Font:");
			fontLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			fontLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.gridwidth = 3;
			gridBagConstraints8.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 4;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			timestampOptionsPanel=new JPanel();
			timestampOptionsPanel.setLayout(new GridBagLayout());
			timestampOptionsPanel.setBorder(BorderFactory.createTitledBorder(null, "Timestamp", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			timestampOptionsPanel.add(getTimestampOptionCheckBox(), gridBagConstraints3);
			timestampOptionsPanel.add(getTimestampColorButton(), gridBagConstraints4);
			timestampOptionsPanel.add(getFontEditor(), gridBagConstraints8);
			timestampOptionsPanel.add(fontLabel, gridBagConstraints6);
			}
		return timestampOptionsPanel;
		}

	/**
	 * This method initializes timestampOptionCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	public JCheckBox getTimestampOptionCheckBox()
		{
		if (timestampOptionCheckBox==null)
			{
			timestampOptionCheckBox=new JCheckBox();
			timestampOptionCheckBox.setText("Show");
			timestampOptionCheckBox.addItemListener(new java.awt.event.ItemListener()
				{
				public void itemStateChanged(java.awt.event.ItemEvent e)
					{
					boolean show=timestampOptionCheckBox.isSelected();
					getTimestampColorButton().setEnabled(show);
					getFontEditor().setEnabled(show);
					fontLabel.setEnabled(show);
					}
				});
			}
		return timestampOptionCheckBox;
		}

	/**
	 * This method initializes timestampColorButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTimestampColorButton()
		{
		if (timestampColorButton==null)
			{
			timestampColorButton=new JButton();
			timestampColorButton.setText("Color...");
			timestampColorButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			timestampColorButton.setEnabled(false);
			timestampColorButton.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						}
				});
			}
		return timestampColorButton;
		}

	/**
	 * This method initializes fontEditor	
	 * 	
	 * @return sun.beans.editors.FontEditor	
	 */
	public FontEditor getFontEditor()
		{
		if (fontEditor==null)
			{
			fontEditor=new FontEditor();
			fontEditor.setLayout(new FlowLayout());
			}
		return fontEditor;
		}

	public Font getTimestampFont()
		{
		return timestampFont;
		}

	public void setTimestampFont(Font timestampFont)
		{
		this.timestampFont=timestampFont;
		}

	/**
	 * This method initializes allowMotionCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAllowMotionCheckBox()
		{
		if (allowMotionCheckBox==null)
			{
			allowMotionCheckBox=new JCheckBox();
			allowMotionCheckBox.setText("Allow motion control over web");
			allowMotionCheckBox.setMnemonic(KeyEvent.VK_UNDEFINED);
			allowMotionCheckBox.setEnabled(false);
			}
		return allowMotionCheckBox;
		}

	/**
	 * This method initializes autoMotionPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getAutoMotionPanel()
		{
		if (autoMotionPanel==null)
			{
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridy = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 2;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.gridy = 1;
			vRateLabel = new JLabel();
			vRateLabel.setText("Steps/Min");
			vRateLabel.setEnabled(false);
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.NONE;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.gridwidth = 1;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.NONE;
			gridBagConstraints13.gridy = 1;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.gridwidth = 2;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 0;
			hRateLabel = new JLabel();
			hRateLabel.setText("Steps/Min");
			hRateLabel.setEnabled(false);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 0;
			autoMotionPanel=new JPanel();
			autoMotionPanel.setLayout(new GridBagLayout());
			autoMotionPanel.setPreferredSize(new Dimension(367, 143));
			autoMotionPanel.add(getAutoHorizontalEnableCheckBox(), gridBagConstraints9);
			autoMotionPanel.add(hRateLabel, gridBagConstraints10);
			autoMotionPanel.add(getAutoVerticalRateComboBox(), gridBagConstraints13);
			autoMotionPanel.add(getAutoHorizontalRateComboBox(), gridBagConstraints12);
			autoMotionPanel.add(vRateLabel, gridBagConstraints14);
			autoMotionPanel.add(getAutoVerticalEnableCheckBox(), gridBagConstraints15);
			}
		return autoMotionPanel;
		}

	/**
	 * This method initializes autoHorizontalEnableCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAutoHorizontalEnableCheckBox()
		{
		if (autoHorizontalEnableCheckBox==null)
			{
			autoHorizontalEnableCheckBox=new JCheckBox();
			autoHorizontalEnableCheckBox.setText("Horizontal");
			autoHorizontalEnableCheckBox.addItemListener(new java.awt.event.ItemListener()
				{
					public void itemStateChanged(java.awt.event.ItemEvent e)
						{
						getAutoHorizontalRateComboBox().setEnabled(autoHorizontalEnableCheckBox.isSelected());
						hRateLabel.setEnabled(autoHorizontalEnableCheckBox.isSelected());
						setAutoHorzMotion(autoHorizontalEnableCheckBox.isSelected());
						}
				});
			}
		return autoHorizontalEnableCheckBox;
		}

	/**
	 * This method initializes autoHorizontalRateList	
	 * 	
	 * @return javax.swing.JList	
	 */

	/**
	 * This method initializes autoVerticalRateComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getAutoVerticalRateComboBox()
		{
		if (autoVerticalRateComboBox==null)
			{
			autoVerticalRateComboBox=new JComboBox();
			autoVerticalRateComboBox.setEnabled(false);
			autoVerticalRateComboBox.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						setAutoVertRate(autoVerticalRateComboBox.getSelectedIndex()+1);
						}
				});
			for (int i=1;i<61;i++)
				autoVerticalRateComboBox.addItem(""+i);
			}
		return autoVerticalRateComboBox;
		}

	/**
	 * This method initializes autoHorizontalRateComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getAutoHorizontalRateComboBox()
		{
		if (autoHorizontalRateComboBox==null)
			{
			autoHorizontalRateComboBox=new JComboBox();
			autoHorizontalRateComboBox.setEnabled(false);
			autoHorizontalRateComboBox.addActionListener(new java.awt.event.ActionListener()
				{
					public void actionPerformed(java.awt.event.ActionEvent e)
						{
						setAutoHorzRate(autoHorizontalRateComboBox.getSelectedIndex()+1);
						}
				});
			for (int i=1;i<61;i++)
				autoHorizontalRateComboBox.addItem(""+i);
			}
		return autoHorizontalRateComboBox;
		}

	/**
	 * This method initializes autoVerticalEnableCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAutoVerticalEnableCheckBox()
		{
		if (autoVerticalEnableCheckBox==null)
			{
			autoVerticalEnableCheckBox=new JCheckBox();
			autoVerticalEnableCheckBox.setText("Vertical");
			autoVerticalEnableCheckBox.addItemListener(new java.awt.event.ItemListener()
				{
					public void itemStateChanged(java.awt.event.ItemEvent e)
						{
						getAutoVerticalRateComboBox().setEnabled(autoVerticalEnableCheckBox.isSelected());
						vRateLabel.setEnabled(autoVerticalEnableCheckBox.isSelected());
						setAutoVertMotion(autoVerticalEnableCheckBox.isSelected());
						}
				});
			}
		return autoVerticalEnableCheckBox;
		}

	public boolean isAutoHorzMotion()
		{
		return autoHorzMotion;
		}

	public void setAutoHorzMotion(boolean autoHorzMotion)
		{
		this.autoHorzMotion=autoHorzMotion;
		}

	public int getAutoHorzRate()
		{
		return autoHorzRate;
		}

	public void setAutoHorzRate(int autoHorzRate)
		{
		this.autoHorzRate=autoHorzRate;
		}

	public boolean isAutoVertMotion()
		{
		return autoVertMotion;
		}

	public void setAutoVertMotion(boolean autoVertMotion)
		{
		this.autoVertMotion=autoVertMotion;
		}

	public int getAutoVertRate()
		{
		return autoVertRate;
		}

	public void setAutoVertRate(int autoVertRate)
		{
		this.autoVertRate=autoVertRate;
		}

	}  //  @jve:decl-index=0:visual-constraint="14,15"
