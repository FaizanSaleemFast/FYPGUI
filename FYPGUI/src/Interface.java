import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import DOM_Difference.Difference;
import DOM_Difference.PageParser;

public class Interface extends JFrame{
	

	private File file01 = null , file02 = null;
	private PageParser pageParser;
	private ArrayList<String> allCategories;
	
	JTextPane tp_v1, tp_v2;
	
	public File getFile01() {
		return file01;
	}

	public void setFile01(File file01) {
		this.file01 = file01;
	}

	public File getFile02() {
		return file02;
	}

	public void setFile02(File file02) {
		this.file02 = file02;
	}

	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(1200, 650));
		this.pack();
		getContentPane().setLayout(null);
		setDefaultLookAndFeelDecorated(true);
		setResizable(false);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e ){
			
		}
	
		
		
		JButton btnChooseVersion01 = new JButton("Browse Version 1");
		btnChooseVersion01.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnChooseVersion01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				file01 = openFileChooser();
				if(file01 != null){

					btnChooseVersion01.setText(file01.getName());
				}
			}
		});
		btnChooseVersion01.setBounds(164, 409, 250, 23);
		getContentPane().add(btnChooseVersion01);
		
		JButton btnChooseVersion02 = new JButton("Browse Version 2");
		btnChooseVersion02.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnChooseVersion02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file02 =  openFileChooser();
				if(file02 != null){

					btnChooseVersion02.setText(file02.getName());
				}
				
			}
		});
		
		btnChooseVersion02.setBounds(767, 409, 250, 23);
		getContentPane().add(btnChooseVersion02);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBackground(Color.GRAY);
		panelButtons.setBounds(10, 481, 231, 158);
		getContentPane().add(panelButtons);
		panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
		

		
	
		
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 448, 1180, 2);
		getContentPane().add(separator);
		
		
		DefaultListModel<String> listCategoryModel = new DefaultListModel<String>();
		
		JLabel lblActions = new JLabel("ACTIONS");
		lblActions.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblActions.setBounds(10, 456, 92, 14);
		getContentPane().add(lblActions);
		
		JLabel lblCategories = new JLabel("CATEGORIES");
		lblCategories.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCategories.setBounds(340, 461, 145, 14);
		getContentPane().add(lblCategories);

		
		List list_categories = new List();
		
		list_categories.setBounds(340, 481, 850, 159);
		getContentPane().add(list_categories);
	
		
		
		JButton btnFindDifference_1 = new JButton("Find Difference");
		btnFindDifference_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFindDifference_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				pageParser = new PageParser();
//				pageParser.setup();
//				
//				allCategories = pageParser.getAllDifferenceTypes();
//				
//				listCategoryModel.clear();
//				for (int i = 0; i < allCategories.size(); i++) {
//
//					list_categories.add(allCategories.get(i));
//				}
				
				
				if(file01 != null && file02!= null){

					pageParser = new PageParser();
					pageParser.setup(file01, file02);
					
					allCategories = pageParser.getAllDifferenceTypesCategories();
					
					listCategoryModel.removeAllElements();
					list_categories.removeAll();
					for (int i = 0; i < allCategories.size(); i++) {
	
						list_categories.add(allCategories.get(i));
					}
				}else{
					 JOptionPane.showMessageDialog(null, "Please choose files first");
				}
			}
		});
		panelButtons.add(btnFindDifference_1);
		
		JButton btnFindDifference = new JButton("Categorize Test Case");
		btnFindDifference.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelButtons.add(btnFindDifference);
		
		JButton btnAbc = new JButton("Repair");
		btnAbc.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelButtons.add(btnAbc);
		
		JLabel lblWebGuiTest = new JLabel("WEB GUI TEST CASE REPAIR TOOL");
		lblWebGuiTest.setBackground(new Color(135, 206, 250));
		lblWebGuiTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblWebGuiTest.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblWebGuiTest.setBounds(0, 11, 1200, 87);
		getContentPane().add(lblWebGuiTest);
		
		tp_v1 = new JTextPane();
		tp_v1.setBounds(10, 109, 589, 289);
		getContentPane().add(tp_v1);
		
		tp_v2 = new JTextPane();
		tp_v2.setBounds(604, 109, 586, 289);
		getContentPane().add(tp_v2);
		
		list_categories.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        List list = (List)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		        	//JOptionPane.showMessageDialog(null, "Please choose files first");
		        	
		        	UpdateVersionChanges(list.getSelectedIndex());
		            
		        } 
//		        else if (evt.getClickCount() == 3) {
//
//		            // Triple-click detected
//		            int index = list.locationToIndex(evt.getPoint());
//		        }
		    }
		});
		
		setVisible(true);
		centreWindow(this);
	}
	
	private void UpdateVersionChanges(int catID){
		
		tp_v1.setText("");
		tp_v2.setText("");
		
		ArrayList<Difference> differentElements = pageParser.getElementByDifferenceType(allCategories.get(catID));
		
		Set<Difference> hs = new HashSet<>();
		hs.addAll(differentElements);
		differentElements.clear();
		differentElements.addAll(hs);
		
		
		System.err.println(differentElements.size());
		
		for (int i = 0; i < differentElements.size(); i++) {
			if(differentElements.get(i).getTagV2() != null){
				tp_v1. setText(tp_v1.getText()+ differentElements.get(i).getTagV2().toString() + "\n\n");
			}
			if(differentElements.get(i).getTagV1() != null){
				tp_v2.setText(tp_v2.getText()+ differentElements.get(i).getTagV1().toString() + "\n\n");
			}		
			
		}
		
	}
	
	private File openFileChooser(){
		File selectedFile = null ;
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
         selectedFile = fileChooser.getSelectedFile();
          
          System.out.println(selectedFile.getName());
        }
        
        return selectedFile;
	}
	
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
}
