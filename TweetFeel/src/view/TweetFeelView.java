package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.KComment;
import model.SearchInfo;
import constant.KConstant;

import javax.swing.JCheckBox;

public class TweetFeelView extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private JTable tblStatus;
	private JButton btnSearch, btnDetect, btnAddToLibrary, btnViewLibrary;
	private JComboBox methodCombo;
	private JTextField txtCount;
	private JCheckBox isTranslate, chckbxRemoveUrl,
			chckbxRemoveSpecialCharacter;
	private JLabel lblLanguage;
	private JComboBox languageCombo;
	private DefaultTableModel statusTableModel;
	private JCheckBox chckbxRemoveHtmlTag;
	private JButton btnViewChart;

	/**
	 * Create the frame.
	 */
	public TweetFeelView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKeyWord = new JLabel("Key word/Url");
		lblKeyWord.setBounds(57, 50, 106, 16);
		contentPane.add(lblKeyWord);

		txtSearch = new JTextField();
		txtSearch.setBounds(175, 44, 369, 28);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(47, 170, 117, 29);
		contentPane.add(btnSearch);

		JLabel lblNumberOfResult = new JLabel("Number of result");
		lblNumberOfResult.setBounds(56, 114, 107, 16);
		contentPane.add(lblNumberOfResult);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 211, 539, 152);
		contentPane.add(scrollPane);

		Vector<String> columnNames = new Vector<>();
		columnNames.addElement("stt");
		columnNames.addElement("User");
		columnNames.addElement("Status");
		columnNames.addElement("Classification");

		statusTableModel = new DefaultTableModel(columnNames, 0);

		tblStatus = new JTable();
		tblStatus.setModel(statusTableModel);
		tblStatus.getColumnModel().getColumn(0).setMaxWidth(40);
		tblStatus.getColumnModel().getColumn(1).setMaxWidth(160);
		tblStatus.getColumnModel().getColumn(3).setMaxWidth(160);

		scrollPane.setViewportView(tblStatus);

		btnAddToLibrary = new JButton("Add to learn data");
		btnAddToLibrary.setBounds(36, 375, 144, 29);
		contentPane.add(btnAddToLibrary);

		btnViewLibrary = new JButton("View library");
		btnViewLibrary.setBounds(192, 375, 117, 29);
		contentPane.add(btnViewLibrary);

		btnDetect = new JButton("Detect");
		btnDetect.setBounds(333, 375, 117, 29);
		contentPane.add(btnDetect);

		methodCombo = new JComboBox();
		methodCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Search API", "Stream", "VnExpress URL", "VnExpress RSS" }));
		methodCombo.setBounds(175, 74, 123, 27);
		contentPane.add(methodCombo);

		JLabel lblMethod = new JLabel("Method");
		lblMethod.setBounds(57, 78, 61, 16);
		contentPane.add(lblMethod);

		txtCount = new JTextField();
		txtCount.setBounds(175, 111, 83, 22);
		contentPane.add(txtCount);
		txtCount.setColumns(10);

		isTranslate = new JCheckBox("Translate to Vietnamese");
		isTranslate.setBounds(315, 74, 201, 23);
		contentPane.add(isTranslate);

		lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(57, 142, 61, 16);
		contentPane.add(lblLanguage);

		languageCombo = new JComboBox();
		languageCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Vietnamese", "English" }));
		languageCombo.setBounds(175, 138, 128, 27);
		contentPane.add(languageCombo);

		chckbxRemoveUrl = new JCheckBox("Remove url");
		chckbxRemoveUrl.setBounds(315, 95, 201, 23);
		contentPane.add(chckbxRemoveUrl);

		chckbxRemoveSpecialCharacter = new JCheckBox("Remove special character");
		chckbxRemoveSpecialCharacter.setBounds(315, 120, 201, 23);
		contentPane.add(chckbxRemoveSpecialCharacter);

		chckbxRemoveHtmlTag = new JCheckBox("Remove HTML TAG");
		chckbxRemoveHtmlTag.setBounds(315, 142, 201, 23);
		contentPane.add(chckbxRemoveHtmlTag);
		
		btnViewChart = new JButton("View chart");
		btnViewChart.setBounds(458, 375, 117, 29);
		contentPane.add(btnViewChart);
	}

	public void addButtonAction(ActionListener act) {
		this.btnSearch.setActionCommand(KConstant.ACTION_COMMAND_SEARCH);
		this.btnViewLibrary.setActionCommand(KConstant.ACTION_COMMAND_VIEW_LIB);
		this.btnAddToLibrary
				.setActionCommand(KConstant.ACTION_COMMAND_ADD_LEARN_DATA);
		this.btnDetect.setActionCommand(KConstant.ACTION_COMMAND_CLASSIFY);
		this.btnViewChart.setActionCommand(KConstant.ACTION_COMMAND_VIEW_CHART);
		
		
		this.btnAddToLibrary.addActionListener(act);
		this.btnDetect.addActionListener(act);
		this.btnSearch.addActionListener(act);
		this.btnViewLibrary.addActionListener(act);
		this.btnViewChart.addActionListener(act);
	}

	public SearchInfo getSearchInfo() {
		SearchInfo sf = new SearchInfo();
		sf.setKeyword(this.txtSearch.getText());
		int methodid = this.methodCombo.getSelectedIndex();
		String language = "";
		switch (languageCombo.getSelectedIndex()) {
		case 0:
			language = KConstant.LANGUAGE_VIETNAM;
			break;
		case 1:
			language = KConstant.LANGUAGE_ENGLISH;
			break;

		}
		sf.setRemoveHtmlTag(chckbxRemoveHtmlTag.isSelected());
		sf.setRemoveUrl(chckbxRemoveUrl.isSelected());
		sf.setRemoveSpecial(chckbxRemoveSpecialCharacter.isSelected());
		sf.setTranslated(isTranslate.isSelected());
		sf.setLanguage(language);
		sf.setType(methodid);
		if (txtCount.getText().length() > 0) {
			sf.setCount(Integer.parseInt(this.txtCount.getText()));
		}
		return sf;
	}

	public void setTblComment(Vector<KComment> kstatuses) {
		while (statusTableModel.getRowCount() > 0) {
			statusTableModel.removeRow(0);
		}
		Vector<String> rowElements;
		for (KComment kComment : kstatuses) {
			rowElements = new Vector<>();
			rowElements.addElement(statusTableModel.getRowCount() + 1 + "");
			rowElements.addElement(kComment.getUser());
			rowElements.addElement(kComment.getContent());
			//if(kComment.getStatus()!=0){
				rowElements.add(kComment.getStatus()+"");
			//}
			statusTableModel.addRow(rowElements);

//			for (KComment subComment : kComment.getSubComment()) {
//				rowElements = new Vector<>();
//				rowElements.add(statusTableModel.getRowCount() + 1 + "");
//				rowElements.add(subComment.getUser());
//				rowElements.add(subComment.getContent());
//				if(subComment.getStatus()!=0){
//					rowElements.add(subComment.getStatus()+"");
//				}
//				statusTableModel.addRow(rowElements);
//			}

		}
	}

	public void addRowTblStatus(KComment kStatus) {

		Vector<String> rowElements = new Vector<>();
		rowElements.addElement((statusTableModel.getRowCount() + 1) + "");
		rowElements.addElement(kStatus.getUser());
		rowElements.addElement(kStatus.getContent());
		statusTableModel.addRow(rowElements);

	}

	public void removeAllRowTblStatus() {
		while (statusTableModel.getRowCount() > 0) {
			statusTableModel.removeRow(0);
		}
	}

	public Vector<KComment> getLearnData() {
		Vector<KComment> comments = new Vector<>();
		for (int i = 0; i < statusTableModel.getRowCount(); i++) {
			String classifistatus = (String) statusTableModel.getValueAt(i, 3);
			if (classifistatus == null) {
				continue;
			}
			if (classifistatus.equals("1") || classifistatus.equals("-1")) {
				String user = (String) statusTableModel.getValueAt(i, 1);
				String comment = (String) statusTableModel.getValueAt(i, 2);
				KComment newComment = new KComment();
				newComment.setContent(comment);
				newComment.setUser(user);
				newComment.setStatus(Integer.parseInt(classifistatus));
				comments.add(newComment);
			}
		}
		return comments;
	}

	public Vector<KComment> getCommentData() {
		Vector<KComment> comments = new Vector<>();
		for (int i = 0; i < statusTableModel.getRowCount(); i++) {
			String user = (String) statusTableModel.getValueAt(i, 1);
			String classifistatus = (String) statusTableModel.getValueAt(i, 3);
			String comment = (String) statusTableModel.getValueAt(i, 2);
			KComment newComment = new KComment();
			newComment.setContent(comment);
			newComment.setUser(user);
			if(classifistatus!=null&& classifistatus.length()>0)
			newComment.setStatus(Integer.parseInt(classifistatus));
			comments.add(newComment);

		}
		return comments;
	}
	
	
}
