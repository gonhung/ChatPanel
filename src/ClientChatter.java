import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClientChatter extends JFrame {
	private JPanel contentPane;
	private JTextField txtStaff;
	private JTextField txtServerIP;
	private JTextField txtServerPort;

	Socket mngsocket=null;
	String mngIP="";
	int mngPort=0;
	String staffname="";
	BufferedReader bf=null;
	DataOutputStream os=null;
	OutputThread t=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChatter frame = new ClientChatter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * String id, port, String
	 */
	public ClientChatter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Staff and server info.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Staff :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblNewLabel);
		
		txtStaff = new JTextField();
		panel_1.add(txtStaff);
		txtStaff.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Mng IP :");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblNewLabel_1);
		
		JFrame thisFrame=this;
		
		JButton bntConnected = new JButton("Connect");
		bntConnected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mngIP="localhost";
				mngPort=12340;
				staffname=txtStaff.getText();
				try {
					mngsocket=new Socket(mngIP, mngPort);
					if(mngsocket != null) {
						ChatPanell p=new ChatPanell(mngsocket, staffname, "server");
						thisFrame.getContentPane().add(p);
						p.getTxtMessages().append("Manager is running");
						p.updateUI();
						bf=new BufferedReader(new InputStreamReader(mngsocket.getInputStream()));
						os=new DataOutputStream(mngsocket.getOutputStream());
						os.writeBytes("Staff: "+staffname);
						os.write(13);os.write(10);
						os.flush();					
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}                                         
			}
		});
		bntConnected.setBackground(Color.GREEN);
		panel_1.add(bntConnected);
}

}
