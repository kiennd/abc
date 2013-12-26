package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.jar.Attributes.Name;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.AbbreviationWord;
import model.KComment;
import model.KWord;
import constant.KConstant;

public class LibraryView extends JFrame {

	private JPanel contentPane;
	private JTextField txtKeyword;
	private JTable tblLearnData;
	private JTable tblWords;
	private JTable tblAbbreviation;
	private DefaultTableModel learnDataModel;
	private DefaultTableModel wordTblModel;
	private DefaultTableModel abbreviationModel;
	
	private JButton btnSaveLearn,btnImportFromtxt,btnRebuildData,btnSearch,btnsaveUnlabel,btnSaveAbbreviation,btnAddAbbreviation,btnRemoveAbbreviation;
	private JRadioButton rdbtnDecision,rdbtnNonDecision,rdbtnUnlabel;
	private JScrollPane scrollPane_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryView frame = new LibraryView();
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
	public LibraryView() {
		setBounds(100, 100, 778, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtKeyword = new JTextField();
		txtKeyword.setBounds(103, 76, 451, 28);
		contentPane.add(txtKeyword);
		txtKeyword.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(582, 77, 117, 29);
		contentPane.add(btnSearch);
		
		JLabel lblLibrary = new JLabel("Library");
		lblLibrary.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		lblLibrary.setBounds(43, 22, 92, 27);
		contentPane.add(lblLibrary);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(38, 122, 721, 268);
		contentPane.add(tabbedPane);
		// tab1
		JPanel learnDataPanel = new JPanel();
		tabbedPane.addTab("Learn Data", null, learnDataPanel, null);
		learnDataPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 6, 661, 178);
		learnDataPanel.add(scrollPane);
		
		Vector<String>learnDataColumnNames = new Vector<>();
		learnDataColumnNames.add("id");
		learnDataColumnNames.add("Comment");
		learnDataColumnNames.add("Status");
		this.learnDataModel = new DefaultTableModel(learnDataColumnNames, 0);
		tblLearnData = new JTable();
		tblLearnData.setModel(learnDataModel);
		tblLearnData.getColumnModel().getColumn(0).setMaxWidth(60);
		tblLearnData.getColumnModel().getColumn(2).setMaxWidth(80);
		
		
		scrollPane.setViewportView(tblLearnData);
		
		btnSaveLearn = new JButton("Save");
		btnSaveLearn.setBounds(16, 191, 68, 29);
		learnDataPanel.add(btnSaveLearn);
		
		btnImportFromtxt = new JButton("Import from *.txt");
		btnImportFromtxt.setBounds(92, 191, 141, 29);
		learnDataPanel.add(btnImportFromtxt);
		
		
		// tab2
		JPanel unlabelWordPanel = new JPanel();
		tabbedPane.addTab("Unlabel words", null, unlabelWordPanel, null);
		
		Vector<String> tblWordColumnNames = new Vector<>();
		tblWordColumnNames.add("id");
		tblWordColumnNames.add("Word");
		tblWordColumnNames.add("Type");
		tblWordColumnNames.add("Pos count");
		tblWordColumnNames.add("Neg count");
		tblWordColumnNames.add("Label");
		
		wordTblModel = new DefaultTableModel(tblWordColumnNames, 0);
		tblWords = new JTable();
		tblWords.setModel(wordTblModel);
		initTblWord(tblWords.getColumnModel());
		
		btnRebuildData = new JButton("Rebuild data");
		btnRebuildData.setBounds(24, 187, 117, 29);
	
		unlabelWordPanel.setLayout(null);
		unlabelWordPanel.add(btnRebuildData);
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(24, 11, 656, 174);
		unlabelWordPanel.add(scrollPane_2);
		
		
		scrollPane_2.setViewportView(tblWords);
		
		//tab 5
		JPanel abbreviationPanel = new JPanel();
		tabbedPane.addTab("Abbreviation lib", null, abbreviationPanel, null);
		abbreviationPanel.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(18, 6, 665, 171);
		abbreviationPanel.add(scrollPane_1);
		
		Vector<String> abbreviationColumnsName = new Vector<>();
		abbreviationColumnsName.add("id");
		abbreviationColumnsName.add("Abbreviation");
		abbreviationColumnsName.add("Original");
		abbreviationModel = new DefaultTableModel(abbreviationColumnsName, 0);
		
		tblAbbreviation = new JTable();
		tblAbbreviation.setModel(abbreviationModel);
		scrollPane_1.setViewportView(tblAbbreviation);
		
		btnSaveAbbreviation = new JButton("Save");
		btnSaveAbbreviation.setBounds(18, 189, 75, 29);
		abbreviationPanel.add(btnSaveAbbreviation);
		
		btnAddAbbreviation = new JButton("add");
		btnAddAbbreviation.setBounds(105, 189, 75, 29);
		abbreviationPanel.add(btnAddAbbreviation);
		
		 btnRemoveAbbreviation = new JButton("remove");
		btnRemoveAbbreviation.setBounds(192, 189, 104, 29);
		abbreviationPanel.add(btnRemoveAbbreviation);
		
		//tab 6
		JPanel statisticsPanel = new JPanel();
		tabbedPane.addTab("Statistics", null, statisticsPanel, null);
		
		btnsaveUnlabel = new JButton("Save");
		btnsaveUnlabel.setBounds(144, 187, 117, 29);
		unlabelWordPanel.add(btnsaveUnlabel);
		
		rdbtnDecision = new JRadioButton("Decision words");
		rdbtnDecision.setBounds(399, 188, 141, 23);
		unlabelWordPanel.add(rdbtnDecision);
		
		 rdbtnNonDecision = new JRadioButton("Non-Decision words");
		rdbtnNonDecision.setBounds(527, 188, 167, 23);
		unlabelWordPanel.add(rdbtnNonDecision);
		
		 rdbtnUnlabel = new JRadioButton("Unlabel");
		rdbtnUnlabel.setBounds(298, 188, 91, 23);
		unlabelWordPanel.add(rdbtnUnlabel);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnUnlabel);
		bg.add(rdbtnNonDecision);
		bg.add(rdbtnDecision);
		
	}
	
	public void initTblWord(TableColumnModel columnModel){
		columnModel.getColumn(0).setMaxWidth(60);
		columnModel.getColumn(1).setMaxWidth(400);
		
		columnModel.getColumn(2).setMaxWidth(80);
		columnModel.getColumn(3).setMaxWidth(80);
		columnModel.getColumn(4).setMaxWidth(80);
		columnModel.getColumn(5).setMaxWidth(200);
		
		JComboBox<String> comboLabel = new JComboBox<>();
		comboLabel.addItem(KConstant.UNLABEL);
		comboLabel.addItem(KConstant.DECISION_LABEL);
		comboLabel.addItem(KConstant.NON_DECISION_LABEL);
		columnModel.getColumn(5).setCellEditor(new DefaultCellEditor(comboLabel));
		
	}
	
	public void setTblLearnData(Vector<KComment> kstatuses){
		while(learnDataModel.getRowCount()>0){
			learnDataModel.removeRow(0);
		}
		int i = 1;
		for (KComment kStatus : kstatuses) {
			Vector<String> rowElements = new Vector<>();
			rowElements.addElement(i+"");
			rowElements.addElement(kStatus.getContent());
			rowElements.addElement(kStatus.getStatus()+"");
			learnDataModel.addRow(rowElements);
			i++;
		}
	}
	
	public void setTblWords(Vector<KWord> words){
		while(wordTblModel.getRowCount()>0){
			wordTblModel.removeRow(0);
		}
		for (KWord word : words) {
			Vector<String> rowElements = new Vector<>();
			rowElements.addElement(wordTblModel.getRowCount()+1+"");
			rowElements.addElement(word.getWord());
			rowElements.addElement(word.getType());
			rowElements.addElement(word.getCountpos()+"");
			rowElements.addElement(word.getCountneg()+"");
			rowElements.addElement(word.getLabel());
			wordTblModel.addRow(rowElements);
		}
	}
	
	
	
	public void addButtonActionListener(ActionListener act){
		this.btnSaveLearn.setActionCommand(KConstant.ACTION_COMMAND_SAVE_LEARN_DATA);
		this.btnSaveLearn.addActionListener(act);
		
		this.btnImportFromtxt.setActionCommand(KConstant.ACTION_COMMAND_IMPORT_LEARN_DATA_FROM_TXT);
		this.btnImportFromtxt.addActionListener(act);
		
		this.btnRebuildData.setActionCommand(KConstant.ACTION_COMMAND_REBUILD_WORDLIB);
		this.btnRebuildData.addActionListener(act);
		
		this.btnSearch.setActionCommand(KConstant.ACTION_COMMAND_SEARCH_LIB);
		this.btnSearch.addActionListener(act);
		
		this.btnsaveUnlabel.setActionCommand(KConstant.ACTION_COMMAND_SAVE_UNLABEL_WORD);
		this.btnsaveUnlabel.addActionListener(act);
		
		this.btnAddAbbreviation.setActionCommand(KConstant.ACTION_COMMAND_ADD_ABBREVIATION);
		this.btnAddAbbreviation.addActionListener(act);
		
		this.btnSaveAbbreviation.setActionCommand(KConstant.ACTION_COMMAND_SAVE_ABBREVIATION);
		this.btnSaveAbbreviation.addActionListener(act);

		this.btnRemoveAbbreviation.setActionCommand(KConstant.ACTION_COMMAND_REMOVE_ABBREVIATION);
		this.btnRemoveAbbreviation.addActionListener(act);
	}
	
	public void addRadioButtonActionListener(ActionListener act){
		this.rdbtnDecision.setActionCommand(KConstant.ACTION_COMMAND_DECISION_SELECTED);
		this.rdbtnDecision.addActionListener(act);
		
		this.rdbtnNonDecision.setActionCommand(KConstant.ACTION_COMMAND_NON_DECISION_SELECTED);
		this.rdbtnNonDecision.addActionListener(act);
		
		this.rdbtnUnlabel.setActionCommand(KConstant.ACTION_COMMAND_UNLABEL_SELECTED);
		this.rdbtnUnlabel.addActionListener(act);
		
	}
	
	public Vector<KComment> getLearnData(){
		Vector<KComment> comments = new Vector<>();
		for (int i = 0; i < learnDataModel.getRowCount(); i++) {
			String classifistatus = (String) learnDataModel.getValueAt(i, 2);
			if(classifistatus==null){
				continue;
			}
			if(classifistatus.equals("1")||classifistatus.equals("-1")){
				String comment = (String) learnDataModel.getValueAt(i, 1);
				KComment newComment = new KComment();
				newComment.setContent(comment);
				newComment.setStatus(Integer.parseInt(classifistatus));
				comments.addElement(newComment);
			}
		}
		return comments;
	}
	
	public Vector<KWord> getWordsData(){
		Vector<KWord> words = new Vector<>();
		for (int i = 0; i < wordTblModel.getRowCount(); i++) {
			words.addElement(rowToWord(i));
		}
		return words;
	}
	
	public Vector<KWord> getWordChanged(String currentRadio){
		Vector<KWord> words = new Vector<>();
		for (int i = 0; i < wordTblModel.getRowCount(); i++) {
			if(!wordTblModel.getValueAt(i, 5).equals(currentRadio)){				
				words.addElement(rowToWord(i));
			}
		}
		return words;
	}
	
	public KWord rowToWord(int i){
		String word = (String) wordTblModel.getValueAt(i, 1);
		String type = (String) wordTblModel.getValueAt(i, 2);
		String pos = (String) wordTblModel.getValueAt(i, 3);
		String neg = (String) wordTblModel.getValueAt(i, 4);
		String label = (String) wordTblModel.getValueAt(i, 5);
		KWord kword =  new KWord();
		kword.setWord(word);
		kword.setType(type);
		kword.setCountpos(Integer.parseInt(pos));
		kword.setCountneg(Integer.parseInt(neg));
		kword.setLabel(label);
		return kword;
	}
	

	
	public void setUnlabelSelected(){
		this.rdbtnUnlabel.setSelected(true);
	}
	
	public String getKeyword(){
		return this.txtKeyword.getText();
	}

	public void addRowAbbrevation(){
		this.abbreviationModel.addRow(new Vector<String>());
	}
	
	public Vector<AbbreviationWord> getAbbrevationData(){
		Vector<AbbreviationWord> abWords = new Vector<>();
		for (int i = 0; i < abbreviationModel.getRowCount(); i++) {
			AbbreviationWord abw = new AbbreviationWord();
			abw.setAbbreviation((String)abbreviationModel.getValueAt(i, 1));
			abw.setOriginal((String)abbreviationModel.getValueAt(i, 2));
			abWords.add(abw);
		}
		return abWords;
	}
	public void setTblAbbreviation(Vector<AbbreviationWord> abbreviation){
		while(abbreviationModel.getRowCount()>0){
			abbreviationModel.removeRow(0);
		}
		
		for (AbbreviationWord abbreviationWord : abbreviation) {
			Vector<String>row = new Vector<>();
			row.add(abbreviationModel.getRowCount()+1+"");
			row.add(abbreviationWord.getAbbreviation());
			row.add(abbreviationWord.getOriginal());
			abbreviationModel.addRow(row);
		}
	}
	
	public void removeAbbreviationRow(){
		int index = this.tblAbbreviation.getSelectedRow();
		this.abbreviationModel.removeRow(index);
	}
}
