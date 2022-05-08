import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class ChatPanell  extends JPanel{
	private JTextField txtMessage;
	private JTextArea txtMessages;
	private JFileChooser fc=new JFileChooser();
	private File file;
	
	
	Socket socket=null;
	BufferedReader bf=null;
	DataOutputStream os=null;
	OutputThread t=null;
	String sender;
	String receive;
	
	//FileInputStream fis = null;
	
	/**
	 * Create the panel.
	 */
	public ChatPanell(Socket s, String sender, String receive) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		txtMessage = new JTextField();
		panel.add(txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBackground(Color.GREEN);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtMessage.getText().trim().length()==0)return;
				try {
					os.writeBytes(txtMessage.getText());
					os.write(13);
					os.write(10);
					os.flush();
					txtMessages.append("\n"+sender+": "+txtMessage.getText());
					txtMessage.setText("");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		JPanel thisFrame=this;
		panel.add(btnSend);
		
		txtMessages = new JTextArea();
		JScrollPane jscrollpane=new JScrollPane(txtMessages);
		add(jscrollpane, BorderLayout.CENTER);
		this.socket = s;
		this.sender = sender;
		this.receive = receive;
		try {
			bf=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os=new DataOutputStream(socket.getOutputStream());
			t=new OutputThread(s, txtMessages, sender, receive);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public JTextArea getTxtMessages() {
		return this.txtMessages;
	}


}
