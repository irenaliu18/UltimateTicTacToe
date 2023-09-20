/*
 * Room chooser
 * Also contains FireBase stuff
 * 
 * Created 4-3-2021
 */

package views;

import ultimate_tictactoe.Main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.database.*;

/**
 * Page where players can choose a room; connects to Firebase to connect to rooms
 * @author katytsao
 *
 */
public class RoomChooser extends JPanel implements ActionListener {

	private Main router;
	private DatabaseReference ref;
	private JButton create, join;
	private JList<String> roomList;
	private DefaultListModel<String> model;
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new RoomChooser view
	 * @param router The Main object holding this RoomChooser
	 */
	public RoomChooser(Main router) {
		super();
		this.router = router;
		setLayout(new BorderLayout());
		model = new DefaultListModel<String>();
		roomList = new JList<String>();
		roomList.setModel(model);
		add(roomList, BorderLayout.CENTER);
		JLabel ah = new JLabel("Available Rooms");
		ah.setHorizontalAlignment(JLabel.CENTER);
		add(ah, BorderLayout.NORTH);
		
		JPanel ePanel = new JPanel();
		ePanel.setLayout(new GridLayout(1,5,15,15));
		create = new JButton("<html><center>Create<br>A Room</center></html>");
		create.addActionListener(this);
		join = new JButton("<html><center>Join<br>Room</center></html>");
		join.addActionListener(this);
		ePanel.add(create);
		ePanel.add(join);
		add(ePanel,BorderLayout.SOUTH);
		
		FileInputStream refreshToken;
	    try {
	    	refreshToken = new FileInputStream("ultimate_tictactoe_key.json");
	    	FirebaseOptions options = new FirebaseOptions.Builder()
	    			.setCredentials(GoogleCredentials.fromStream(refreshToken))
	    			.setDatabaseUrl("https://ultimate-tictactoe-f1e59-default-rtdb.firebaseio.com/")
	    			.build();

	    	FirebaseApp.initializeApp(options);
	    	ref = FirebaseDatabase.getInstance().getReference();
	    	ref.addChildEventListener(new DatabaseChangeListener());

	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Method required by ActionListener interface; responds to button clicks
	 */
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b == create) {
			String roomName = JOptionPane.showInputDialog("New room name: ");
			if (roomName == null || roomName.isEmpty()) {
				JOptionPane.showMessageDialog(RoomChooser.this, "Room creation fail - The room needs a name.");
				return;
			}
			if (model.contains(roomName)) {
				JOptionPane.showMessageDialog(RoomChooser.this, "Room creation fail - Room name already exists.");
				return;
			}
			ref.push().child(roomName);
			String passcode = JOptionPane.showInputDialog("Room passcode (optional): ");
			ref.child(roomName).child("passcode").setValueAsync(passcode);
			repaint();
		}
		else if(b==join) {
			String roomName = roomList.getSelectedValue();
			if(roomName==null) return;
			DatabaseReference roomRef = ref.child(roomName);
			
			roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
				public void onDataChange(DataSnapshot snap) {
					if (!snap.hasChildren()) return;
					String roomPass = snap.child("passcode").getValue(String.class);
					if(roomPass!=null) {
						String typedPass = JOptionPane.showInputDialog("Room passcode: ");
						if(!roomPass.equals(typedPass)) {
							JOptionPane.showMessageDialog(RoomChooser.this, "Sorry, incorrect passcode.");
							return;
						}
					}
					router.setGame(new Game(router, ref.child(roomName)));
					router.push("menu");
				}
				public void onCancelled(DatabaseError arg0) {}
			});
		}
		
	}
	
	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby
	 *
	 */
	class DatabaseChangeListener implements ChildEventListener {
		
		/**
		 * Runs each time something is added to the Firebase database
		 */
		public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
			SwingUtilities.invokeLater(new Runnable() {  // This threading strategy will work with Swing programs. Just put whatever code you want inside of one of these "runnable" wrappers.
				public void run() {
					String roomName = dataSnapshot.getKey();
					model.add(0, roomName);
				}
			});
		}

		// methods req by ChildEventListener
		public void onCancelled(DatabaseError arg0) {}
		public void onChildChanged(DataSnapshot arg0, String arg1) {}
		public void onChildMoved(DataSnapshot arg0, String arg1) {}
		public void onChildRemoved(DataSnapshot arg0) {}
	}

}
