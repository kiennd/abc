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
import java.awt.Color;
import javax.swing.ImageIcon;

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
	private JButton btnManualAdd;
	private JLabel lblDesignAndCoding;



	/**
	 * Create the frame.
	 */
	public TweetFeelView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblKeyWord = new JLabel("Key word/Url");
		lblKeyWord.setForeground(Color.WHITE);
		lblKeyWord.setBounds(178, 116, 106, 16);
		contentPane.add(lblKeyWord);

		txtSearch = new JTextField();
		txtSearch.setBounds(296, 110, 291, 28);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);

		btnSearch = new JButton("Fetch Data");
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setIcon(new ImageIcon("images/rssicon.png"));
		transparent(btnSearch);
		btnSearch.setBounds(168, 236, 136, 29);
		contentPane.add(btnSearch);

		JLabel lblNumberOfResult = new JLabel("Number of result");
		lblNumberOfResult.setForeground(Color.WHITE);
		lblNumberOfResult.setBounds(177, 180, 107, 16);
		contentPane.add(lblNumberOfResult);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(122, 277, 574, 152);
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
		btnAddToLibrary.setBounds(111, 441, 173, 29);
		btnAddToLibrary.setIcon(new ImageIcon("images/addComment.png"));
		transparent(btnAddToLibrary);
		contentPane.add(btnAddToLibrary);

		btnViewLibrary = new JButton("View library");
		btnViewLibrary.setBounds(296, 441, 134, 29);
		btnViewLibrary.setIcon(new ImageIcon("images/library.png"));
		transparent(btnViewLibrary);
		contentPane.add(btnViewLibrary);

		btnDetect = new JButton("Detect");
		btnDetect.setBounds(440, 441, 117, 29);
		btnDetect.setIcon(new ImageIcon("images/tickIcon.png"));
		transparent(btnDetect);
		contentPane.add(btnDetect);

		methodCombo = new JComboBox();
		methodCombo.setModel(new DefaultComboBoxModel(new String[] {"Search API", "Stream", "VnExpress URL", "VnExpress RSS", "NgoiSao.net URL", "NgoiSao.net RSS", "News.Zing URL", "News.Zing RSS", "Haivl.com URL", "RingRing.vn URL", "RingRing.vn RSS"}));
		methodCombo.setBounds(296, 140, 123, 27);
		contentPane.add(methodCombo);

		JLabel lblMethod = new JLabel("Method");
		lblMethod.setForeground(Color.WHITE);
		lblMethod.setBounds(178, 144, 61, 16);
		contentPane.add(lblMethod);

		txtCount = new JTextField();
		txtCount.setBounds(296, 177, 83, 22);
		contentPane.add(txtCount);
		txtCount.setColumns(10);

		isTranslate = new JCheckBox("Translate to Vietnamese");
		isTranslate.setForeground(Color.WHITE);
		isTranslate.setBounds(436, 140, 201, 23);
		isTranslate.setOpaque(false);
		contentPane.add(isTranslate);

		lblLanguage = new JLabel("Language");
		lblLanguage.setForeground(Color.WHITE);
		lblLanguage.setBounds(178, 208, 61, 16);
		contentPane.add(lblLanguage);

		languageCombo = new JComboBox();
		languageCombo.setModel(new DefaultComboBoxModel(new String[] {
				"Vietnamese", "English" }));
		languageCombo.setBounds(296, 204, 128, 27);
		contentPane.add(languageCombo);

		chckbxRemoveUrl = new JCheckBox("Remove url");
		chckbxRemoveUrl.setForeground(Color.WHITE);
		chckbxRemoveUrl.setOpaque(false);
		chckbxRemoveUrl.setBounds(436, 161, 201, 23);
		contentPane.add(chckbxRemoveUrl);

		chckbxRemoveSpecialCharacter = new JCheckBox("Remove special character");
		chckbxRemoveSpecialCharacter.setForeground(Color.WHITE);
		chckbxRemoveSpecialCharacter.setBounds(436, 186, 201, 23);
		chckbxRemoveSpecialCharacter.setOpaque(false);
		contentPane.add(chckbxRemoveSpecialCharacter);

		chckbxRemoveHtmlTag = new JCheckBox("Remove HTML TAG");
		chckbxRemoveHtmlTag.setForeground(Color.WHITE);
		chckbxRemoveHtmlTag.setBounds(436, 208, 201, 23);
		chckbxRemoveHtmlTag.setOpaque(false);
		contentPane.add(chckbxRemoveHtmlTag);
		
		btnViewChart = new JButton("View chart");
		btnViewChart.setIcon(new ImageIcon("images/chartIcon.png"));
		btnViewChart.setBounds(570, 441, 136, 29);
		transparent(btnViewChart);
		contentPane.add(btnViewChart);
		
		btnManualAdd = new JButton("Manual Add");
		btnManualAdd.setForeground(Color.WHITE);
		btnManualAdd.setBounds(306, 236, 124, 29);
		btnManualAdd.setIcon(new ImageIcon("images/hand.png"));
		transparent(btnManualAdd);
		contentPane.add(btnManualAdd);
		
		LabelImageScalable label_2 = new LabelImageScalable();
		label_2.setBounds(354, 490, 20, 20);
		label_2.setImage("images/info.png");
		contentPane.add(label_2);
		
		lblDesignAndCoding = new JLabel("Design and code: Nguyễn Đức Kiên - D10CNPM1- PTIT");
		lblDesignAndCoding.setBounds(384, 496, 336, 14);
		contentPane.add(lblDesignAndCoding);
		
		LabelImageScalable lblNewLabel = new LabelImageScalable();
		lblNewLabel.setBounds(611, 11, 153, 132);
		lblNewLabel.setImage("images/twitter.gif");
		contentPane.add(lblNewLabel);
		
		LabelImageScalable label = new LabelImageScalable();
		label.setBounds(384, 11, 173, 56);
		label.setImage("images/vneLogo.png");
		contentPane.add(label);
		
		LabelImageScalable label_1 = new LabelImageScalable();
		label_1.setBounds(178, 11, 201, 82);
		label_1.setImage("images/zing.png");
		contentPane.add(label_1);
		
		LabelImageScalable backGround = new LabelImageScalable();
		backGround.setBounds(0, 0, 764, 510);
		backGround.setImage("images/bg.jpg");
		contentPane.add(backGround);
		
	}
	
	void transparent(JButton button){
//		button.setOpaque(false);
		button.setContentAreaFilled(false);
//		button.setBorderPainted(false);
	}

	public void addButtonAction(ActionListener act) {
		this.btnSearch.setActionCommand(KConstant.ACTION_COMMAND_SEARCH);
		this.btnViewLibrary.setActionCommand(KConstant.ACTION_COMMAND_VIEW_LIB);
		this.btnAddToLibrary
				.setActionCommand(KConstant.ACTION_COMMAND_ADD_LEARN_DATA);
		this.btnDetect.setActionCommand(KConstant.ACTION_COMMAND_CLASSIFY);
		this.btnViewChart.setActionCommand(KConstant.ACTION_COMMAND_VIEW_CHART);
		this.btnManualAdd.setActionCommand(KConstant.ACTION_COMMAND_MANUAL_COMMENT);
		
		this.btnManualAdd.addActionListener(act);
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
	
	public void addCommentRow(){
		Vector<String> v  = new Vector<>();
		v.add(statusTableModel.getRowCount()+1+"");
		this.statusTableModel.addRow(v);
	}
}
