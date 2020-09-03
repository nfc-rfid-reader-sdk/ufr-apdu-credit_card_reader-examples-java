package apdu_credit_card;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



import static apdu_credit_card.UfrCoder.GetLibFullPath;

import apdu_credit_card.UFrEMV.afl_list_item_t;
import apdu_credit_card.UFrEMV.emv_tree_node_t;
import apdu_credit_card.UfrCoder.ERRORCODES;
import apdu_credit_card.UfrCoder.uFrFunctions;
import com.sun.jna.ptr.IntByReference;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.text.html.ListView;

import com.sun.jna.Native;

import javax.swing.text.Element;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static apdu_credit_card.UfrCoder.GetLibFullPath;
import static apdu_credit_card.UfrCoder.uFrFunctions;
import static apdu_credit_card.UFrEMV.EMVFunctions;
import javax.swing.JSeparator;
import com.sun.jna.Pointer;
public class window {
	
	uFrFunctions ufr;
	EMVFunctions emv = new EMVFunctions();

	private JFrame frmApduCreditCard;
	private JTextField textField;
	private JTextField txtReaderType;
	private JTextField txtPortInterface;
	private JTextField txtPortName;
	private JTextField txtArg;
	private JTextField txtCardNr;
	private JTable tableTransactions;
	
	
	private JRadioButton rbPSE1;
	private JRadioButton rbPSE2;
	JLabel lblStatus;
	
	JTextArea txtCheckPSE_Log;
	JTextArea txtCardEMVLog_Log;
	JTextArea txtReadParseEMV_Log;
	
	JCheckBox chkAdvanced;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frmApduCreditCard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
		try {
            ufr = (uFrFunctions) Native.loadLibrary(GetLibFullPath(false), uFrFunctions.class);
        } catch (UnsatisfiedLinkError e) {
            JOptionPane.showMessageDialog(null, "Unable to load library for path : " + GetLibFullPath(false));
        }
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmApduCreditCard = new JFrame();
		frmApduCreditCard.getContentPane().setBackground(Color.WHITE);
		frmApduCreditCard.setTitle("APDU Credit Card Java Example v1.0");
		frmApduCreditCard.setBounds(100, 100, 800, 545);
		frmApduCreditCard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnReaderOpen = new JButton("Reader Open");
		btnReaderOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReaderOpenActionPerformed(e);
			}
		});
		btnReaderOpen.setBackground(Color.WHITE);
		btnReaderOpen.setFont(new Font("Dialog", Font.BOLD, 12));
		btnReaderOpen.setBounds(10, 11, 120, 30);
		
		JButton btnReaderReset = new JButton("Reader Reset");
		btnReaderReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReaderResetActionPerformed(e);
			}
		});
		btnReaderReset.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReaderReset.setBackground(Color.WHITE);
		btnReaderReset.setBounds(140, 10, 120, 30);
		
		JButton btnReaderClose = new JButton("Reader Close");
		btnReaderClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReaderCloseActionPerformed(e);
			}
		});
		btnReaderClose.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReaderClose.setBackground(Color.WHITE);
		btnReaderClose.setBounds(270, 10, 120, 30);
		
		JLabel lblNewLabel = new JLabel("DLL Version:");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setBounds(400, 18, 75, 14);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setText("5.0.42");
		textField.setBounds(480, 15, 43, 20);
		textField.setColumns(10);
		
		chkAdvanced = new JCheckBox("Use Advanced options");
		
		chkAdvanced.setBackground(Color.WHITE);
		chkAdvanced.setBounds(10, 48, 179, 23);
		
		JLabel lblReaderType = new JLabel("Reader type:");
		lblReaderType.setEnabled(false);
		lblReaderType.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblReaderType.setBounds(10, 83, 75, 13);
		
		JLabel lblPortName = new JLabel("Port name:");
		lblPortName.setEnabled(false);
		lblPortName.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPortName.setBounds(140, 83, 64, 14);
		
		JLabel lblPortInterface = new JLabel("Port interface:");
		lblPortInterface.setEnabled(false);
		lblPortInterface.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPortInterface.setBounds(300, 83, 85, 14);
		
		JLabel lblArg = new JLabel("Arg:");
		lblArg.setEnabled(false);
		lblArg.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblArg.setBounds(430, 83, 25, 14);
		
		txtReaderType = new JTextField();
		txtReaderType.setEnabled(false);
		txtReaderType.setBounds(92, 79, 30, 20);
		txtReaderType.setColumns(10);
		
		txtPortInterface = new JTextField();
		txtPortInterface.setEnabled(false);
		txtPortInterface.setBounds(390, 79, 30, 20);
		txtPortInterface.setColumns(10);
		
		txtPortName = new JTextField();
		txtPortName.setEnabled(false);
		txtPortName.setBounds(214, 79, 67, 20);
		txtPortName.setColumns(10);
		
		txtArg = new JTextField();
		txtArg.setEnabled(false);
		txtArg.setBounds(462, 79, 150, 20);
		txtArg.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(533, 11, 241, 55);
		frmApduCreditCard.getContentPane().setLayout(null);
		panel.setLayout(null);
		
		rbPSE1 = new JRadioButton("PSE1");
		rbPSE1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPSE1.isSelected())
					rbPSE2.setSelected(false);
			}
		});
		rbPSE1.setBounds(30, 25, 94, 23);
		rbPSE1.setBackground(Color.WHITE);
		panel.add(rbPSE1);
		
		rbPSE2 = new JRadioButton("PSE2");
		rbPSE2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPSE2.isSelected())
					rbPSE1.setSelected(false);
			}
		});
		rbPSE2.setBounds(137, 25, 94, 23);
		rbPSE2.setBackground(Color.WHITE);
		panel.add(rbPSE2);
		frmApduCreditCard.getContentPane().add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("Select Payment System Environment");
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 11));
		lblNewLabel_2.setBounds(20, 5, 221, 14);
		panel.add(lblNewLabel_2);
		frmApduCreditCard.getContentPane().add(btnReaderOpen);
		frmApduCreditCard.getContentPane().add(btnReaderReset);
		frmApduCreditCard.getContentPane().add(btnReaderClose);
		frmApduCreditCard.getContentPane().add(chkAdvanced);
		frmApduCreditCard.getContentPane().add(lblReaderType);
		frmApduCreditCard.getContentPane().add(txtReaderType);
		frmApduCreditCard.getContentPane().add(lblPortName);
		frmApduCreditCard.getContentPane().add(txtPortName);
		frmApduCreditCard.getContentPane().add(lblPortInterface);
		frmApduCreditCard.getContentPane().add(txtPortInterface);
		frmApduCreditCard.getContentPane().add(lblArg);
		frmApduCreditCard.getContentPane().add(lblNewLabel);
		frmApduCreditCard.getContentPane().add(textField);
		frmApduCreditCard.getContentPane().add(txtArg);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(10, 158, 764, 301);
		frmApduCreditCard.getContentPane().add(tabbedPane);
		
		JPanel pnlTransactions = new JPanel();
		pnlTransactions.setBackground(Color.WHITE);
		tabbedPane.addTab("Transactions", null, pnlTransactions, null);
		pnlTransactions.setLayout(null);
		
		JButton btnGetLastTransaction = new JButton("Get last transaction");
		btnGetLastTransaction.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGetLastTransaction.setBackground(Color.WHITE);
		btnGetLastTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGetLastTransactionActionPerformed(e);
			}
		});
		btnGetLastTransaction.setBounds(10, 7, 150, 30);
		pnlTransactions.add(btnGetLastTransaction);
		
		JButton btnClearTransactions = new JButton("Clear");
		btnClearTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClearTransactionsActionPerformed(e);
			}
		});
		btnClearTransactions.setFont(new Font("Dialog", Font.BOLD, 12));
		btnClearTransactions.setBackground(Color.WHITE);
		btnClearTransactions.setBounds(350, 7, 90, 30);
		pnlTransactions.add(btnClearTransactions);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 739, 220);
		pnlTransactions.add(scrollPane);
		
		tableTransactions = new JTable();
		tableTransactions.setRowSelectionAllowed(false);
		tableTransactions.setFillsViewportHeight(true);
		scrollPane.setViewportView(tableTransactions);
		tableTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableTransactions.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"ATCounter", "Date", "Time", "Ammount", "Currency", "Terminal Country"
			}
		));
		tableTransactions.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableTransactions.getColumnModel().getColumn(0).setMinWidth(50);
		tableTransactions.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableTransactions.getColumnModel().getColumn(1).setMinWidth(75);
		tableTransactions.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableTransactions.getColumnModel().getColumn(2).setMinWidth(75);
		tableTransactions.getColumnModel().getColumn(3).setPreferredWidth(177);
		tableTransactions.getColumnModel().getColumn(3).setMinWidth(100);
		tableTransactions.getColumnModel().getColumn(4).setPreferredWidth(95);
		tableTransactions.getColumnModel().getColumn(4).setMinWidth(95);
		tableTransactions.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		JButton btnGetLastTenTransactions = new JButton("Get last 10 transactions");
		btnGetLastTenTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGetLastTenTransactionsActionPerformed(e);
			}
		});
		btnGetLastTenTransactions.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGetLastTenTransactions.setBackground(Color.WHITE);
		btnGetLastTenTransactions.setBounds(170, 7, 170, 30);
		pnlTransactions.add(btnGetLastTenTransactions);
		
		JPanel pnlCheckPSE = new JPanel();
		tabbedPane.addTab("Check if card supports selected PSE", null, pnlCheckPSE, null);
		pnlCheckPSE.setLayout(null);
		
		JButton btnCheckPSE_Read = new JButton("READ");
		btnCheckPSE_Read.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCheckPSEActionPerformed(e);
			}
		});
		btnCheckPSE_Read.setBackground(Color.WHITE);
		btnCheckPSE_Read.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCheckPSE_Read.setBounds(208, 240, 120, 30);
		pnlCheckPSE.add(btnCheckPSE_Read);
		
		JButton bntCheckPSE_Clear = new JButton("CLEAR");
		bntCheckPSE_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClearCheckPSEActionPerformed(e);
			}
		});
		bntCheckPSE_Clear.setBackground(Color.WHITE);
		bntCheckPSE_Clear.setFont(new Font("Dialog", Font.BOLD, 12));
		bntCheckPSE_Clear.setBounds(404, 240, 120, 30);
		pnlCheckPSE.add(bntCheckPSE_Clear);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 5, 739, 230);
		pnlCheckPSE.add(scrollPane_3);
		
		txtCheckPSE_Log = new JTextArea();
		scrollPane_3.setViewportView(txtCheckPSE_Log);
		
		JPanel pnlReadParseEMV = new JPanel();
		tabbedPane.addTab("Read and Parse EMV on card", null, pnlReadParseEMV, null);
		pnlReadParseEMV.setLayout(null);
		
		JButton btnReadParseEMV_Read = new JButton("READ");
		btnReadParseEMV_Read.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReadAndParseEMVActionPerformed(e);
			}
		});
		btnReadParseEMV_Read.setFont(new Font("Dialog", Font.BOLD, 12));
		btnReadParseEMV_Read.setBackground(Color.WHITE);
		btnReadParseEMV_Read.setBounds(208, 240, 120, 30);
		pnlReadParseEMV.add(btnReadParseEMV_Read);
		
		JButton btnReadParseEMV_Clear = new JButton("CLEAR");
		btnReadParseEMV_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClearReadAndParseEMVActionPerformed(e);
			}
		});
		btnReadParseEMV_Clear.setFont(new Font("Dialog", Font.BOLD, 12));
		btnReadParseEMV_Clear.setBackground(Color.WHITE);
		btnReadParseEMV_Clear.setBounds(404, 240, 120, 30);
		pnlReadParseEMV.add(btnReadParseEMV_Clear);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 5, 739, 230);
		pnlReadParseEMV.add(scrollPane_1);
		
		txtReadParseEMV_Log = new JTextArea();
		scrollPane_1.setViewportView(txtReadParseEMV_Log);
		
		JPanel pnlCardEMVLog = new JPanel();
		tabbedPane.addTab("Read and Parse EMV log on card", null, pnlCardEMVLog, null);
		tabbedPane.setBackgroundAt(3, Color.WHITE);
		pnlCardEMVLog.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 5, 739, 257);
		pnlCardEMVLog.add(scrollPane_2);
		
		txtCardEMVLog_Log = new JTextArea();
		scrollPane_2.setViewportView(txtCardEMVLog_Log);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 470, 764, 7);
		frmApduCreditCard.getContentPane().add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("STATUS:");
		lblNewLabel_1.setBounds(10, 475, 55, 14);
		frmApduCreditCard.getContentPane().add(lblNewLabel_1);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(75, 475, 342, 14);
		frmApduCreditCard.getContentPane().add(lblStatus);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 115, 354, 36);
		frmApduCreditCard.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPan = new JLabel("PAN:");
		lblPan.setBounds(13, 11, 35, 14);
		panel_1.add(lblPan);
		
		txtCardNr = new JTextField();
		txtCardNr.setBounds(55, 8, 185, 20);
		panel_1.add(txtCardNr);
		txtCardNr.setColumns(10);
		
		JButton btnGetPAN = new JButton("Get PAN");
		btnGetPAN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGetPANActionPerformed(e);
			}
		});
		btnGetPAN.setBackground(Color.WHITE);
		btnGetPAN.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGetPAN.setBounds(250, 5, 89, 26);
		panel_1.add(btnGetPAN);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 104, 764, 7);
		frmApduCreditCard.getContentPane().add(separator_1);
		
		chkAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblReaderType.setEnabled(!lblReaderType.isEnabled());
				lblPortName.setEnabled(!lblPortName.isEnabled());
				lblPortInterface.setEnabled(!lblPortInterface.isEnabled());
				lblArg.setEnabled(!lblArg.isEnabled());
				
				txtReaderType.setEnabled(!txtReaderType.isEnabled());
				txtPortName.setEnabled(!txtPortName.isEnabled());
				txtPortInterface.setEnabled(!txtPortInterface.isEnabled());
				txtArg.setEnabled(!txtArg.isEnabled());
				
			}
		});
	}
	
	//------------------------------------------------------------------------------
	
	private String ByteArrayToString(byte[] ch_array)
    {
        StringBuilder strB = new StringBuilder();
        
        for(int i = 0; i < ch_array.length; i++)
        {
            if(ch_array[i] == 0)
                break;

            strB.append((char)ch_array[i]);
        }
        
        return strB.toString();
    }
	
	public static byte[] stringToBytesASCII(String str) {
		 byte[] b = new byte[str.length()];
		 for (int i = 0; i < b.length; i++) {
		  b[i] = (byte) str.charAt(i);
		 }
		 return b;
		}
	
	//------------------------------------------------------------------------------
	
	private void btnReaderOpenActionPerformed(java.awt.event.ActionEvent evt) {
        
		int status = 0x54;
        String readerType = txtReaderType.getText();
        String portName = txtPortName.getText();
        String portInterface = txtPortInterface.getText();
        String arg = txtArg.getText();
        
        
        
        if(!chkAdvanced.isSelected())
        {
            status = ufr.ReaderOpen();
            
            //JOptionPane.showMessageDialog(null, status, "ReaderOpenEx format error:", JOptionPane.INFORMATION_MESSAGE);
             
            if(status == 0)
            {
            	ufr.ReaderUISignal(1,1);            	
            }
            
            lblStatus.setText(ufr.UFR_Status2String(status));
        }
        else
        {   
            if(readerType.equals("") || portName.equals("") || portInterface.equals("") || arg.equals(""))
            {
                JOptionPane.showMessageDialog(null, "You have to fill all parameters for ReaderOpenEx", "ReaderOpenEx format error:", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                int reader_type = Integer.parseInt(readerType);
                byte[] bportName = portName.getBytes(StandardCharsets.US_ASCII);
                int port_interface = 0;
            
                if(portInterface.equals("U"))
                {
                    port_interface = 85;
                }
                else if(portInterface.equals("T"))
                {   
                    port_interface = 84;
                }
                else
                {
                    port_interface = Integer.parseInt(portInterface);
                }
            
                byte[] bArg = arg.getBytes(StandardCharsets.US_ASCII);
            
                status = ufr.ReaderOpenEx(reader_type, bportName, port_interface, bArg);
            }
            
            if(status == 0)
            {
                ufr.ReaderUISignal(1,1);
                                
            }
            
            lblStatus.setText(ufr.UFR_Status2String(status));
        }
    }
	
	//------------------------------------------------------------------------------
	
	private void btnReaderCloseActionPerformed(java.awt.event.ActionEvent evt) {
		int status = 0x54;
		
		status = ufr.ReaderClose();
		lblStatus.setText(ufr.UFR_Status2String(status));
	}
	
	//------------------------------------------------------------------------------
	
	private void btnReaderResetActionPerformed(java.awt.event.ActionEvent evt) {
		int status = 0x54;
		
		status = ufr.ReaderReset();
		lblStatus.setText(ufr.UFR_Status2String(status));
	}
	
	//------------------------------------------------------------------------------
	
	private void btnGetPANActionPerformed(java.awt.event.ActionEvent evt)
	{		
		int status;
		
		String df_name = "";
		
		if (rbPSE1.isSelected())
		{
			df_name = "1PAY.SYS.DDF01";
		} else if (rbPSE2.isSelected())
		{
			df_name = "2PAY.SYS.DDF01";
		} else 
		{
			JOptionPane.showMessageDialog(null, "Select Payment System Environment first.");
			return;
		}
		
		status = ufr.SetISO14443_4_Mode();
		if (status == 0)
		{
			byte[] pan = new byte[128];
			status = ufr.EMV_GetPAN(df_name, pan);
			if (status == 0) 
			{
				
				String card_nr = ByteArrayToString(pan).replaceAll("....", "$0-");
				card_nr = card_nr.substring(0, card_nr.length()-1);
				txtCardNr.setText(card_nr);
				//System.out.println("PAN:=\n" + card_nr);
				lblStatus.setText(ufr.UFR_Status2String(status));
			} else 
			{
				txtCardNr.setText("");
				lblStatus.setText(ufr.UFR_Status2String(status));
			}
		} else 
		{
			txtCardNr.setText("");
			lblStatus.setText(ufr.UFR_Status2String(status));
		}
		
		ufr.s_block_deselect((byte)100);
	}
	
	//------------------------------------------------------------------------------
	
	private void btnGetLastTransactionActionPerformed(java.awt.event.ActionEvent evt) {
		btnClearTransactionsActionPerformed(evt);
		
		int status;
		
		String df_name = "";
		
		if (rbPSE1.isSelected())
		{
			df_name = "1PAY.SYS.DDF01";
		} else if (rbPSE2.isSelected())
		{
			df_name = "2PAY.SYS.DDF01";
		} else 
		{
			JOptionPane.showMessageDialog(null, "Select Payment System Environment first.");
			return;
		}
		
		status = ufr.SetISO14443_4_Mode();
		if (status == 0)
		{
			byte[] transaction = new byte[128];
			status = ufr.EMV_GetLastTransaction(df_name, transaction);
			if (status == 0) 
			{
				String lines[] = ByteArrayToString(transaction).split("\\r?\\n");
				String ATCounter = lines[0].split(":")[1].trim();
				String Date = lines[1].split(":")[1].trim();
				String Time = lines[2].split(":", 2)[1].trim();
				String Amount = lines[3].split(":")[1].trim();
				String Currency = lines[4].split(":")[1].trim();
				String Terminal_Country = lines[5].split(":")[1].trim();
				
				tableTransactions.setValueAt(ATCounter, 0, 0);
				tableTransactions.setValueAt(Date, 0, 1);
				tableTransactions.setValueAt(Time, 0, 2);
				tableTransactions.setValueAt(Amount, 0, 3);
				tableTransactions.setValueAt(Currency, 0, 4);
				tableTransactions.setValueAt(Terminal_Country, 0, 5);
				
				
				lblStatus.setText(ufr.UFR_Status2String(status));
			} else 
			{
				lblStatus.setText(ufr.UFR_Status2String(status));
			}
		}	
		else 
		{
			lblStatus.setText(ufr.UFR_Status2String(status));
		}
		
		ufr.s_block_deselect((byte)100);
	}
	
	//------------------------------------------------------------------------------
	
	private void btnGetLastTenTransactionsActionPerformed(java.awt.event.ActionEvent evt) {
		btnClearTransactionsActionPerformed(evt);
		txtCardEMVLog_Log.setText("");

		String df_name = "";
		
		if (rbPSE1.isSelected())
		{
			df_name = "1PAY.SYS.DDF01";
		} else if (rbPSE2.isSelected())
		{
			df_name = "2PAY.SYS.DDF01";
		} else 
		{
			JOptionPane.showMessageDialog(null, "Select Payment System Environment first.");
			return;
		}
		
		byte[] r_apdu = new byte[258];
		IntByReference Ne = new IntByReference((int)256);

		boolean head_attached = false;
		
		
		byte[] sw = new byte[2];
		byte[] sfi = new byte[1];
		byte[] log_records = new byte[1];
		short[] log_list_len = new short[1];
		byte[] log_list_data = null;
		byte[] aid = new byte[16];	
		byte[] aid_len = new byte[1];
		byte record = 0;
		byte cnt = 0;
		int ufr_status = 0x54;
		int[] emv_status = new int[1];

		
		String PseTitle = "";
		if (rbPSE2.isSelected())
		{
			PseTitle = "PSE2";
		} else 
		{
			PseTitle = "PSE1";
		}
		
		emv_tree_node_t head = new emv_tree_node_t();
		emv_tree_node_t temp = new emv_tree_node_t();
		emv_tree_node_t tail = new emv_tree_node_t();
		emv_tree_node_t log_list_item = new emv_tree_node_t();
		
		do
		{
			ufr_status = ufr.SetISO14443_4_Mode();
			if (ufr_status != 0) {
				txtCardEMVLog_Log.append("Error while switching into ISO 14443-3 mode, check uFR status.");
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			}
			
			txtCardEMVLog_Log.append(++cnt +". Issuing \"Select PSE\" command (" + PseTitle +")\n" + "  [C] 00 A4 04 00 ");			
			for (int i = 0; i < df_name.length(); i++)
			{
				txtCardEMVLog_Log.append( String.format("%02X", df_name.getBytes()[i]) + " ");
			}
			
			ufr_status = ufr.APDUTransceive((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, df_name.getBytes(), df_name.length(), r_apdu, Ne, (byte)1, sw);
			if (ufr_status != 0)
			{
				txtCardEMVLog_Log.append(" Error while executing APDU command, check uFR status.");
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			} else 
			{
				if (sw[0] != (byte)0x90)
				{
					txtCardEMVLog_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
					txtCardEMVLog_Log.append("\nCould not continue execution due to an APDU error.\n");
					ufr_status = ERRORCODES.UFR_APDU_TRANSCEIVE_ERROR.value;
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));					
					break;
				}
				
				if (Ne.getValue() > 0)
				{
					txtCardEMVLog_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
					txtCardEMVLog_Log.append(" [R] ");
					for (int i = 0; i < Ne.getValue(); i++)
					{
						txtCardEMVLog_Log.append(String.format("%02X ",r_apdu[i]));
					}
				}
				txtCardEMVLog_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
			}
			
			int[] Ne_ptr = new int[1];
			Ne_ptr[0] = Ne.getValue();
			
			head = emv.newEmvTag(head, r_apdu, Ne_ptr, false, emv_status);
			if (emv_status[0] != 0)
			{
				txtCardEMVLog_Log.append("\n EMV parsing error occurred.");
				ufr_status = emv_status[0];
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			}
			
			ufr_status = emv.getSfi(head, sfi);
			if (ufr_status == 0)
			{
				//There is SFI
				record = 1;
				do 
				{
					ufr_status = emv.emvReadRecord(r_apdu, Ne_ptr, sfi[0], record, sw);
					if (ufr_status == 0)
					{
						temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
						if (!head_attached)
						{
							head.next = tail = temp;
							head_attached = true;
						} 
						else 
						{
							tail.next = temp;
							tail = tail.next;
						}
						
						if (Ne_ptr[0] > 0)
						{
							txtCardEMVLog_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
							txtCardEMVLog_Log.append(" [R] ");
							for (int i = 0; i < Ne.getValue(); i++)
							{
								txtCardEMVLog_Log.append(String.format("%02X ",r_apdu[i]));
							}
						}
						txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
					} else 
					{
						if (sw[0] != 0x90)
						{
							txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
							txtCardEMVLog_Log.append(" There is no records");
						}
					}
					
					record++;
					cnt++;
					
				} while (ufr_status == 0);
			} 
			
			ufr_status = emv.getAid(head, aid, aid_len);
			if (ufr_status == 0)
			{
				Ne.setValue(256);
				txtCardEMVLog_Log.append(String.format("\n %d. Issuing \"Select the application\" command \n  [C] 00 A4 04 00 %02X", ++cnt, aid_len[0]));
				ufr_status = ufr.APDUTransceive((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, aid, aid_len[0], r_apdu, Ne, (byte)1, sw);
				if (ufr_status != 0)
				{
					txtCardEMVLog_Log.append(" Error while executing APDU command, check uFR status.");
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				} else 
				{
					if (sw[0] != (byte)0x90)
					{
						txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
						txtCardEMVLog_Log.append(" There is no records");
						break;
					}
					
					if (Ne.getValue() > 0)
					{
						txtCardEMVLog_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
						txtCardEMVLog_Log.append(" [R] ");
						for (int i = 0; i < Ne.getValue(); i++)
						{
							txtCardEMVLog_Log.append(String.format("%02X ",r_apdu[i]));
						}
					}
					txtCardEMVLog_Log.append(String.format("\n [SW] %02X %02X\n", sw[0], sw[1]));
				}
				
				Ne_ptr[0] = Ne.getValue();
				temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
				if (emv_status[0] != 0)
				{
					txtCardEMVLog_Log.append("\n EMV parsing error occurred.");
					ufr_status = emv_status[0];
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				if (head_attached == false)
				{
					head.next = tail = temp;
					head_attached = true;
				} else 
				{
					tail.next = temp;
					tail = tail.next;
				}
			
			}
			
			ufr_status = emv.getLogEntry(temp, sfi, log_records);
			if (ufr_status != 0)
			{
				txtCardEMVLog_Log.append("\n EMV parsing error occurred.");
				ufr_status = emv_status[0];
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			} else 
			{
				Ne.setValue(256);
				txtCardEMVLog_Log.append(String.format("\n%d. Issuing \"Get Log Format\" command:\n [C] 80 CA 9F 4F 00", ++cnt));
				ufr_status = ufr.APDUTransceive((byte)0x80, (byte)0xCA, (byte)0x9F, (byte)0x4F, null, 0, r_apdu, Ne, (byte)1, sw);
				if (ufr_status != 0) {
					break;
				} else
				{
					if (sw[0] != (byte) 0x90)
					{
						txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
						txtCardEMVLog_Log.append(" There is no records");
						break;
					}
					
					if (Ne.getValue() > 0)
					{
						txtCardEMVLog_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
						txtCardEMVLog_Log.append(" [R] ");
						for (int i = 0; i < Ne.getValue(); i++)
						{
							txtCardEMVLog_Log.append(String.format("%02X ",r_apdu[i]));
						}
					}
					txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
				}
				Ne_ptr[0] = Ne.getValue();
				temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
				if (emv_status[0] != 0)
				{
					txtCardEMVLog_Log.append("\n EMV parsing error occurred.");
					ufr_status = emv_status[0];
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				tail.next = temp;
				tail = tail.next;
				
				log_list_item = temp.tl_list_format;
				
				ufr_status = emv.getListLength(temp, log_list_len);
				if (ufr_status != 0)
				{
					txtCardEMVLog_Log.append("\n EMV parsing error occurred.");
					ufr_status = emv_status[0];
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				log_list_data = new byte[log_list_len[0] * log_records[0]];
				
				for (record = 1; record < log_records[0] + 1; record++)
				{
					txtCardEMVLog_Log.append(String.format("\n %d. Issuing \"Read Record\" command (record = %d, sfi = %d):\n  [C] 00 B2 %02X %02X 00", ++cnt, record, sfi[0], record, (sfi[0] << 3) | 4));
					ufr_status = emv.emvReadRecord(r_apdu, Ne_ptr, sfi[0], record, sw);
					if (ufr_status == 0) {
						if (Ne_ptr[0] > 0) {
							int index = (record-1) * log_list_len[0];
							System.arraycopy(r_apdu, 0, log_list_data, index, log_list_len[0]);
							txtCardEMVLog_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
							txtCardEMVLog_Log.append(" [R] ");
							for (int i = 0; i < Ne.getValue(); i++)
							{
								txtCardEMVLog_Log.append(String.format("%02X ",r_apdu[i]));
							}
						}
						txtCardEMVLog_Log.append(String.format("\n [SW] %02X %02X\n", sw[0], sw[1]));
						lblStatus.setText(ufr.UFR_Status2String(ufr_status));
						} else {
							if (sw[0] != (byte) 0x90) {
								txtCardEMVLog_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
								txtCardEMVLog_Log.append(" There is no records");
								break;
							}
						}
				}
			}
		} while (false);
		
		short[] ATCounter_pos = new short[1];
		short[] TransactionDate_pos = new short[1];
		short[] TransactionTime_pos = new short[1];
		short[] AmountAuthorised_pos = new short[1];
		short[] TransactionCurrency_pos = new short[1];
		short[] TerminalCountry_pos = new short[1];
		short[] ATCounter_len = new short[1];
		short[] TransactionDate_len = new short[1];
		short[] TransactionTime_len = new short[1];
		short[] AmountAuthorised_len = new short[1];
		short[] TransactionCurrency_len = new short[1];
		short[] TerminalCountry_len = new short[1];
		
		boolean ATCounter_exist = emv.isExistATCounter(log_list_item, ATCounter_pos, ATCounter_len);
		boolean TransactionDate_exist = emv.isExistTransactionDate(log_list_item, TransactionDate_pos, TransactionDate_len);
		boolean TransactionTime_exist = emv.isExistTransactionTime(log_list_item, TransactionTime_pos, TransactionTime_len);
		boolean AmountAuthorised_exist = emv.isExistAmountAuthorised(log_list_item, AmountAuthorised_pos, AmountAuthorised_len);
		boolean TransactionCurrency_exist = emv.isExistTransactionCurrency(log_list_item, TransactionCurrency_pos, TransactionCurrency_len);
		boolean TerminalCountry_exist = emv.isExistTerminalCountry(log_list_item, TerminalCountry_pos, TerminalCountry_len);
		
		String[] ATCounter = new String[log_records[0]];
		
		String[] TransactionDate = new String[log_records[0]];
		
		String[] TransactionTime = new String[log_records[0]];
		
		String[] Amount = new String[log_records[0]];
		
		String[] Currency = new String[log_records[0]];
		
		String[] TerminalCountry = new String[log_records[0]];
		
		
		if (log_list_data != null) //TODO: check this IF condition
		{
			
			byte[] log_list_temp = new byte[log_list_len[0] * log_records[0]];
			int log_list_ptr = 0;
			
			System.arraycopy(log_list_data, 0, log_list_temp, 0, log_list_data.length-1);
			
			
			
			for (int i = 0; i < log_records[0]; i++)
			{
				//System.out.println("NR: " + i);
				if (ATCounter_exist)
				{
					if (ATCounter_len[0] == 2)
					{	
						int ATC_1 = (short) ((log_list_temp[ATCounter_pos[0]]) << 8);
						int ATC_2 = Byte.toUnsignedInt(log_list_temp[ATCounter_pos[0] + 1]);
						ATCounter[i] = Integer.toString(ATC_1 + ATC_2);
						//System.out.println("ATCounter[" + i + "] = " + ATCounter[i]);
						
					}
				} else 
				{
					ATCounter[i] = "--";
				}
				
				if (TransactionDate_exist)
				{
					String transaction_day = String.format("%02X", log_list_temp[TransactionDate_pos[0] + 2]);
					String transaction_month = String.format("%02X", log_list_temp[TransactionDate_pos[0] + 1]);
					String transaction_year = String.format("%02X", log_list_temp[TransactionDate_pos[0]]);
					TransactionDate[i] = transaction_day + "." + transaction_month + ".20" + transaction_year;
				}
				else
				{
					TransactionDate[i] = "--.--.----";
				}
				
				if (TransactionTime_exist)
				{
					String transaction_seconds = String.format("%02X", log_list_temp[TransactionTime_pos[0]]);
					String transaction_minutes = String.format("%02X", log_list_temp[TransactionTime_pos[0] + 1]);
					String transaction_hours = String.format("%02X", log_list_temp[TransactionTime_pos[0] + 2]);
					TransactionTime[i] = transaction_seconds + ":" + transaction_minutes + ":" + transaction_hours;
				} else 
				{
					TransactionTime[i] = "--:--:--";
				}
				
				if (AmountAuthorised_exist) {
					
					byte[] amount_arr = new byte[AmountAuthorised_len[0]];
					System.arraycopy(log_list_temp, AmountAuthorised_pos[0], amount_arr, 0, AmountAuthorised_len[0]);
					float amount = emv.bin_bcd_to_ll(amount_arr, AmountAuthorised_len) / 100;
					Amount[i] = String.format("%13.2f", amount);
				} else 
				{
					Amount[i] = "";
				}
				
				if (TransactionCurrency_exist) {
					 
					byte[] currency_arr = new byte[AmountAuthorised_len[0]];
					System.arraycopy(log_list_temp, TransactionCurrency_pos[0], currency_arr, 0, TransactionCurrency_len[0]);
					long currency_index = emv.bin_bcd_to_ll(currency_arr, TransactionCurrency_len);
					Currency[i] = emv.findCurrencyIndexByNumCode((short) currency_index);
				
				} else 
				{
					Currency[i] = "---";
				}
				
				if (TerminalCountry_exist) {
					byte[] terminal_arr = new byte[TerminalCountry_len[0]];
					System.arraycopy(log_list_temp, TerminalCountry_pos[0], terminal_arr, 0, TerminalCountry_len[0]);
					long terminal_index = emv.bin_bcd_to_ll(terminal_arr, TerminalCountry_len);
					TerminalCountry[i] = emv.findCurrencyIndexByNumCode((short) terminal_index);
					
				
				} else 
				{
					TerminalCountry[i] = "---";
				}
				
				
				log_list_ptr += log_list_len[0];		
				System.arraycopy(log_list_data, log_list_ptr, log_list_temp, 0, log_list_data.length-log_list_ptr);
				
			
				tableTransactions.setValueAt(ATCounter[i], i, 0);
				tableTransactions.setValueAt(TransactionDate[i], i, 1);
				tableTransactions.setValueAt(TransactionTime[i], i, 2);
				tableTransactions.setValueAt(Amount[i], i, 3);
				tableTransactions.setValueAt(Currency[i], i, 4);
				tableTransactions.setValueAt(TerminalCountry[i], i, 5);
			}
			
			
		}
		
		//MessageBox.Show("Transactions command log printed out in \"Read and parse cards EMV log\" tab.");
		JOptionPane.showMessageDialog(null, "Transactions command log printed out in \"Read and parse EMV log on card\" tab.");
		ufr.s_block_deselect((byte)100);
		
	}
	
	//------------------------------------------------------------------------------
	
	private void btnClearTransactionsActionPerformed(java.awt.event.ActionEvent evt) {
		
		int row_count = tableTransactions.getRowCount();
		int col_count = tableTransactions.getColumnCount();
		
		for (int col = 0; col < col_count; col++)
		{
			for (int row = 0; row < row_count; row++)
			{
				tableTransactions.setValueAt("", row, col);
			}
		}
	}
	
	//------------------------------------------------------------------------------
	//------------------------------------------------------------------------------
	
	private void btnCheckPSEActionPerformed(java.awt.event.ActionEvent evt) {		
		txtCheckPSE_Log.setText("");
		
		String df_name = "";
		String PseTitle = "";
		if (rbPSE1.isSelected())
		{
			df_name = "1PAY.SYS.DDF01";
			PseTitle = "PSE1";
		} else if (rbPSE2.isSelected())
		{
			df_name = "2PAY.SYS.DDF01";
			PseTitle = "PSE2";
		} else 
		{
			JOptionPane.showMessageDialog(null, "Select Payment System Environment first.");
			return;
		}
		
		byte[] r_apdu = new byte[258];
		IntByReference Ne = new IntByReference((int)256);
		
		byte[] sw = new byte[2];
		byte[] sfi = new byte[1];
		byte record = 0;
		byte cnt = 0;
		int ufr_status = 0x54;
		int[] emv_status = new int[1];
		
		byte[] ascii_name = df_name.getBytes();
		
		emv_tree_node_t head = new emv_tree_node_t();
		emv_tree_node_t temp = new emv_tree_node_t();
		emv_tree_node_t tail = new emv_tree_node_t();
		
		
		do {
			
			ufr_status = ufr.SetISO14443_4_Mode();
			if (ufr_status != 0) {
				txtCheckPSE_Log.append(" Error while switching into ISO 14443-4 mode, check uFR status.");
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			}
			
			txtCheckPSE_Log.append(++cnt +". Issuing \"Select PSE\" command (" + PseTitle +")\n" + "  [C] 00 A4 04 00 ");			
			for (int i = 0; i < df_name.length(); i++)
			{
				txtCheckPSE_Log.append( String.format("%02X", ascii_name[i]) + " ");
			}
			
		
			ufr_status = ufr.APDUTransceive((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, df_name.getBytes(), df_name.length(), r_apdu, Ne, (byte)1, sw);
			if (ufr_status != 0)
			{
				System.out.println(" Error while executing APDU command, check uFR status.");				
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			} else 
			{
				if (sw[0] != (byte)0x90)
				{
					txtCheckPSE_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
					txtCheckPSE_Log.append("\nCould not continue execution due to an APDU error.\n");
					ufr_status = ERRORCODES.UFR_APDU_TRANSCEIVE_ERROR.value;
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				if (Ne.getValue() > 0)
				{
					txtCheckPSE_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
					txtCheckPSE_Log.append(" [R] ");
					for (int i = 0; i < Ne.getValue(); i++)
					{
						txtCheckPSE_Log.append(String.format("%02X ",r_apdu[i]));
					}
				}
				
				txtCheckPSE_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
				
			}
			
			int[] Ne_ptr = new int[1];
			Ne_ptr[0] = Ne.getValue();
			
			//emv.modifyTag(head);
			head = emv.newEmvTag(head, r_apdu, Ne_ptr, false, emv_status);
			
			if (emv_status[0] != 0)
			{
				txtCheckPSE_Log.append("EMV parsing error code:" + ufr.UFR_Status2String(emv_status[0]));
				ufr_status = emv_status[0];
				break;
			}
			
			ufr_status = emv.getSfi(head, sfi);
			if (ufr_status == 0)
			{
				cnt = 2;
				record = 1;
				do
				{
					txtCheckPSE_Log.append(String.format("\n %d. Issuing \"Read Record\" command (record = %d, sfi = %d):\n  [C] 00 B2 %02X %02X 00\n", ++cnt, record, sfi, record, (sfi[0] << 3) | 4));
					ufr_status = emv.emvReadRecord(r_apdu, Ne_ptr, sfi[0], record, sw);		
					if (ufr_status == 0)
					{
						temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
						ufr_status = emv_status[0];
						if (record == 1)
							head.next = tail = temp;
						else {
							tail.next = temp;
							tail = tail.next;
						}	
					} 
					else 
					{
						if (sw[0] != (byte)0x90)
						{
							txtCheckPSE_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
							txtCheckPSE_Log.append("\nCould not continue execution due to an APDU error.\n");
						}
						
						if (Ne.getValue() > 0)
						{
							txtCheckPSE_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
							txtCheckPSE_Log.append(" [R] ");
							for (int i = 0; i < Ne.getValue(); i++)
							{
								txtCheckPSE_Log.append(String.format("%02X ",r_apdu[i]));
							}
						}
						
						txtCheckPSE_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
						
					}
					
					record++;
					cnt++;
				} while (ufr_status == 0);			
			}
			ufr_status = 0;
			
			txtCheckPSE_Log.append("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
			txtCheckPSE_Log.append("          Card supports Payment System Environment: " + PseTitle);
			txtCheckPSE_Log.append("\n=============================================================================\n");

		} while(false);
		
		//emv.emvTreeCleanup();
		
		lblStatus.setText(ufr.UFR_Status2String(ufr_status));
		ufr.s_block_deselect((byte)100);
	}
	
	//------------------------------------------------------------------------------
	
	private void btnClearCheckPSEActionPerformed(java.awt.event.ActionEvent evt) {
		txtCheckPSE_Log.setText("");
	}
		
	//------------------------------------------------------------------------------
	//------------------------------------------------------------------------------
	
	private void btnReadAndParseEMVActionPerformed(java.awt.event.ActionEvent evt) {
		txtReadParseEMV_Log.setText("");
		
		String df_name = "";
		
		if (rbPSE1.isSelected())
		{
			df_name = "1PAY.SYS.DDF01";
		} else if (rbPSE2.isSelected())
		{
			df_name = "2PAY.SYS.DDF01";
		} else 
		{
			JOptionPane.showMessageDialog(null, "Select Payment System Environment first.");
			return;
		}
		
		byte[] r_apdu = new byte[258];
		IntByReference Ne = new IntByReference((int)256);
		
		byte[] sw = new byte[2];
		byte[] sfi = new byte[1];
		byte record = 0;
		byte cnt = 0;
		int ufr_status = 0x54;
		int[] emv_status = new int[1];
		boolean head_attached = false;
		byte[] aid = new byte[16];	
		byte[] aid_len = new byte[1];
		
		short[] gpo_data_field_size = new short[1];
		byte[] gpo_data_field = new byte[1024];
		byte[] afl_list_count = new byte[1];
		byte[] ascii_name = df_name.getBytes();
		
		String PseTitle = "";
		if (rbPSE2.isSelected())
		{
			PseTitle = "PSE2";
		} else 
		{
			PseTitle = "PSE1";
		}
		
		emv_tree_node_t head = new emv_tree_node_t();
		emv_tree_node_t temp = new emv_tree_node_t();
		emv_tree_node_t tail = new emv_tree_node_t();
		afl_list_item_t[] afl_list_item = new afl_list_item_t[1];
		
		do {
			
			ufr_status = ufr.SetISO14443_4_Mode();
			if (ufr_status != 0) {
				txtReadParseEMV_Log.append(" Error while switching into ISO 14443-4 mode, check uFR status.");
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			}
			
					
			txtReadParseEMV_Log.append(++cnt +". Issuing \"Select PSE\" command (" + PseTitle +")\n" + "  [C] 00 A4 04 00 ");
			for (int i = 0; i < df_name.length(); i++)
			{
				txtReadParseEMV_Log.append( String.format("%02X", ascii_name[i]) + " ");
			}
			
			ufr_status = ufr.APDUTransceive((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, df_name.getBytes(), df_name.length(), r_apdu, Ne, (byte)1, sw);
			if (ufr_status != 0)
			{
				System.out.println(" Error while executing APDU command, uFR status is: " + ufr.UFR_Status2String(ufr_status));				
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			} else 
			{
				if (sw[0] != (byte)0x90)
				{
					txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
					txtReadParseEMV_Log.append("\nCould not continue execution due to an APDU error.\n");
					ufr_status = ERRORCODES.UFR_APDU_TRANSCEIVE_ERROR.value;
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				if (Ne.getValue() > 0)
				{
					txtReadParseEMV_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
					txtReadParseEMV_Log.append(" [R] ");
					for (int i = 0; i < Ne.getValue(); i++)
					{
						txtReadParseEMV_Log.append(String.format("%02X ",r_apdu[i]));
					}
				}
				
				txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
			}
			
			int[] Ne_ptr = new int[1];
			Ne_ptr[0] = Ne.getValue();
			
			head = emv.newEmvTag(head, r_apdu, Ne_ptr, false, emv_status);
			
			if (emv_status[0] != 0)
			{
				//System.out.println("newEmvTag failed: " + ufr.UFR_Status2String(emv_status[0]));
				txtReadParseEMV_Log.append(" EMV parsing error occurred.");
				ufr_status = emv_status[0];
				lblStatus.setText(ufr.UFR_Status2String(ufr_status));
				break;
			}
			
			ufr_status = emv.getSfi(head, sfi);
			if (ufr_status == 0)
			{
				record = 1;
				do
				{
					txtReadParseEMV_Log.append(String.format("\n " + ++cnt + ". Issuing \"Read Record\" command (record = %d, sfi = %d):\n  [C] 00 B2 %02X %02X 00\n", cnt, record, sfi, record, (sfi[0] << 3) | 4));
					ufr_status = emv.emvReadRecord(r_apdu, Ne_ptr, sfi[0], record, sw);		
					if (ufr_status == 0)
					{
						temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
						if (record == 1)
							head.next = tail = temp;
						else {
							tail.next = temp;
							tail = tail.next;
						}
						if(Ne_ptr[0] > 0)
						{
							txtReadParseEMV_Log.append(" APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
							txtReadParseEMV_Log.append(" [R] ");
							for (int i = 0; i < Ne.getValue(); i++)
							{
								txtReadParseEMV_Log.append(String.format("%02X ",r_apdu[i]));
							}
						}
						txtReadParseEMV_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
						
					} 
					else 
					{
						if (sw[0] != 0x90)
						{
							txtReadParseEMV_Log.append(String.format(" [SW] %02X %02X\n", sw[0], sw[1]));
							txtReadParseEMV_Log.append(" There is no records");
						}
					}
					
					record++;
					cnt++;
				} while (ufr_status == 0);
				
			}
			
			ufr_status = emv.getAid(head, aid, aid_len);
			if (ufr_status == 0)
			{
				txtReadParseEMV_Log.append(String.format("\n %d. Issuing \"Select the application\" command \n  [C] 00 A4 04 00 %02X \n", ++cnt, aid_len[0]));
				Ne.setValue(256);				
				ufr_status = ufr.APDUTransceive((byte)0x00, (byte)0xA4, (byte)0x04, (byte)0x00, aid, aid_len[0], r_apdu, Ne, (byte)1, sw);
				if (ufr_status != 0)
				{
					System.out.println(" Error while executing APDU command, uFR status is: " + ufr.UFR_Status2String(ufr_status));				
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				} else 
				{
					if (sw[0] != (byte)0x90)
					{
						txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
						txtReadParseEMV_Log.append("\nCould not continue execution due to an APDU error.\n");
					}
					
					if (Ne.getValue() > 0)
					{
						txtReadParseEMV_Log.append(" APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
						txtReadParseEMV_Log.append(" [R] ");
						for (int i = 0; i < Ne.getValue(); i++)
						{
							txtReadParseEMV_Log.append(String.format("%02X ",r_apdu[i]));
						}
					}
					txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));				
				}
				
				Ne_ptr[0] = Ne.getValue();
				
				temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
				if (emv_status[0] != 0)
				{
					break;
				}
				if (head_attached == false)
				{
					head.next = tail = temp;
					head_attached = true;
				} else 
				{
					tail.next = temp;
					tail = tail.next;
				}
				txtReadParseEMV_Log.append(String.format("\n %d. Formating \"Get Processing Options\" instruction (checking PDOL)\n", ++cnt));
				emv_status[0] = emv.formatGetProcessingOptionsDataField(temp, gpo_data_field, gpo_data_field_size);
				if (emv_status[0] != 0)
				{
					ufr_status = emv_status[0];
					//System.out.println("EMV parsing error code: " + ufr.UFR_Status2String(ufr_status));		
					txtReadParseEMV_Log.append(" EMV parsing error occurred.");
					ufr_status = emv_status[0];
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				txtReadParseEMV_Log.append(String.format("\n %d. Issuing \"Get Processing Options\" command:\n  [C] 80 A8 00 00 %02X ", ++cnt, gpo_data_field_size[0]));
				for (int i = 0; i < gpo_data_field_size[0]; i++)
				{
					txtReadParseEMV_Log.append( String.format("%02X", gpo_data_field[i]) + " ");
				}
				
				Ne.setValue(256);				
				ufr_status = ufr.APDUTransceive((byte)0x80, (byte)0xA8, (byte)0x00, (byte)0x00, gpo_data_field, gpo_data_field_size[0], r_apdu, Ne, (byte)1, sw);
				if (ufr_status != 0)
				{
					txtReadParseEMV_Log.append(" Error while executing APDU command, uFR status is: " + ufr.UFR_Status2String(ufr_status));	
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				} else 
				{
					if (sw[0] != (byte)0x90)
					{
						txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
						txtReadParseEMV_Log.append("\nCould not continue execution due to an APDU error.\n");
						break;
					}
					
					if (Ne.getValue() > 0)
					{
						txtReadParseEMV_Log.append("\n APDU command executed: response data length = " + Ne.getValue() + " bytes\n");
						txtReadParseEMV_Log.append(" [R] ");
						for (int i = 0; i < Ne.getValue(); i++)
						{
							txtReadParseEMV_Log.append(String.format("%02X ",r_apdu[i]));
						}
					}
					txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));				
				}
				
				Ne_ptr[0] = Ne.getValue();
				temp = emv.newEmvTag(temp, r_apdu, Ne_ptr, false, emv_status);
				if (emv_status[0] != 0)
				{
					ufr_status = emv_status[0];
					txtReadParseEMV_Log.append("EMV parsing error: " + ufr.UFR_Status2String(ufr_status));
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				tail.next = temp;
				tail = tail.next;
				
				
				ufr_status = emv.getAfl(temp, afl_list_item, afl_list_count);
				if (ufr_status == ERRORCODES.EMV_ERR_TAG_NOT_FOUND.value)
				{
					ufr_status = emv.getAflFromResponseMessageTemplateFormat1(temp, afl_list_item, afl_list_count);
				}
				
				if (ufr_status != 0)
				{
					txtReadParseEMV_Log.append("EMV parsing error: " + ufr.UFR_Status2String(ufr_status));
					lblStatus.setText(ufr.UFR_Status2String(ufr_status));
					break;
				}
				
				while(afl_list_item[0] != null)
				{
					for (int r = afl_list_item[0].record_first[0]; r <= afl_list_item[0].record_last[0]; r++)
					{
						txtReadParseEMV_Log.append(String.format("\n %d. Issuing \"Read Record\" command (record = %d, sfi = %d):\n  [C] 00 B2 %02X %02X 00\n", ++cnt,
								   r, afl_list_item[0].sfi[0], r, (afl_list_item[0].sfi[0] << 3) | 4));
						
						ufr_status = emv.emvReadRecord(r_apdu, Ne_ptr, afl_list_item[0].sfi[0], (byte)r, sw);		
						if (ufr_status == 0)
						{ 
							int[] temp_Ne = new int[Ne_ptr.length];
							System.arraycopy(Ne_ptr, 0, temp_Ne, 0, Ne_ptr.length);
							
							byte[] temp_resp = new byte[r_apdu.length];
							System.arraycopy(r_apdu, 0, temp_resp, 0, r_apdu.length);
							
							temp = emv.newEmvTag(temp, temp_resp, temp_Ne, false, emv_status);
							if (emv_status[0] == 0)
							{
								tail.next = temp;
								tail = tail.next;
							}
							
							if (Ne_ptr[0] > 0)
							{
								txtReadParseEMV_Log.append(" APDU command executed: response data length = " + Ne_ptr[0] + " bytes\n");
								txtReadParseEMV_Log.append(" [R] ");
								for (int i = 0; i < Ne_ptr[0]; i++)
								{
									txtReadParseEMV_Log.append(String.format("%02X ",r_apdu[i]));
								}
							}
							txtReadParseEMV_Log.append("\n [SW] " + String.format("%02X %02X\n", sw[0], sw[1]));
							
						}
					}
					afl_list_item[0] = afl_list_item[0].next;
				}
			
				
			}
			
			ufr_status = 0;
			lblStatus.setText(ufr.UFR_Status2String(ufr_status));
		} while(false);
		
		
		ufr.s_block_deselect((byte)100);
		
	}
	
	//------------------------------------------------------------------------------
	
	private void btnClearReadAndParseEMVActionPerformed(java.awt.event.ActionEvent evt) {
		txtReadParseEMV_Log.setText("");
	}
}
