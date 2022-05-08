import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ManagerChatter extends JFrame implements Runnable {
	public JPanel contentPane;
	public JTextField txtServerPort;
	//private JLabel lbMessage;
	
	public ServerSocket srvSocket=null;
	public BufferedReader br=null;
	public Thread t;
	private JTabbedPane tabbedPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerChatter frame = new ManagerChatter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerChatter() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//emptyborder là khoảng cách giứa các thức thể và đường viền
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lbMessage = new JLabel("Manager Port:");
		lbMessage.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lbMessage);
		
		txtServerPort = new JTextField();
		txtServerPort.setText("12340");
		panel.add(txtServerPort);
		txtServerPort.setColumns(10);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
//JTablePane() là một phần vùng chứa cho phép người dùng chuyển đổi giứa các trang bằng cách nhấp vào tab		
		
		this.setSize(600,300);
		int serverPort=Integer.parseInt(txtServerPort.getText());
		try {
			srvSocket=new ServerSocket(serverPort);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		t=new Thread(this);
		t.start();
	}
	
	public void run() {
		while(true) {
			try {
				Socket aStaffSocket=srvSocket.accept();
				if(aStaffSocket != null) {
					br=new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
					String S=br.readLine();
					int pos=S.indexOf(":");
					String staffName = S.substring(pos+1);
					ChatPanell p=new ChatPanell(aStaffSocket, "server", staffName);
					tabbedPane.add(staffName, p);
					p.updateUI();
				}
				Thread.sleep(100);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
