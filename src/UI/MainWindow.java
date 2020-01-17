package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Core.Evaluator;
import Core.FileControler;
import Core.Generator;
import Core.Program;


public class MainWindow extends JFrame implements ActionListener,ChangeListener{
	private static final long serialVersionUID = 1L;
	
	JPanel panel = new JPanel();
	
	JTextArea area = new JTextArea();
	
	JScrollPane pane = new JScrollPane(area);
	
	JButton loadFile = new JButton("Load");
	JButton generate = new JButton("Generate");
	JButton ranGen	 = new JButton("<html>Generate<br/>randomly</html>");
	
	JLabel minLabel = new JLabel("Min. word length");
	JLabel maxLabel = new JLabel("Max. word length");
	JLabel noLabel	= new JLabel("Number of generated words");
	
	JSlider minLength = new JSlider(3,8,5);	
	JSlider maxLength = new JSlider(5,20,8);
	
	Integer[] tab = {10,20,50,100};
	JComboBox<Integer> noBox	= new JComboBox<>(tab);
	
	public MainWindow() {
		setSize(640, 640);
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.panel.setBackground(new Color(50, 93,110));
		setResizable(false);
		setContent();
		setVisible(true);
	}
	
	public void addToArea(String input) {
		area.append(input);
	}
	
	public void addToArea(char input) {
		String temp = new String(""+input);
		this.addToArea(temp);
	}
	
	private void setContent(){
		Rectangle rec = this.getBounds();
		area.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
		panel.add(pane); pane.setBounds
		(rec.width-Program.AREA_WIDTH+(20*Program.AREA_WIDTH)/320, (rec.height)/80,  (Program.AREA_WIDTH*7)/8, (rec.height*36)/40); 
		area.setEditable(false); area.setBackground(new Color(200,200,200));
		
		panel.add(loadFile); 
		loadFile.setBounds(rec.width/32, (rec.height)/80,  rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*5)/40); 
		loadFile.addActionListener(this);

		panel.add(minLabel);
		minLabel.setBounds(rec.width/16, (rec.height*15)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*1)/40);
		panel.add(minLength); 
		minLength.setBounds(rec.width/32, (rec.height*17)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*3)/40); 
		minLength.setEnabled(true); 
		minLength.addChangeListener(this); 
		minLength.setPaintTicks(true);
		minLength.setMinorTickSpacing(1);
		minLength.setMajorTickSpacing(2);
		minLength.setPaintLabels(true);
		minLength.setSnapToTicks(true);
		minLength.setBackground(this.panel.getBackground());		   							   

		panel.add(maxLabel);
		maxLabel.setBounds(rec.width/16, (rec.height*23)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*1)/40);
		panel.add(maxLength); maxLength.setBounds(rec.width/32, (rec.height*25)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*3)/40);
		maxLength.setEnabled(true); maxLength.addChangeListener(this);	
		maxLength.setPaintTicks(true);
		maxLength.setMinorTickSpacing(1);
		maxLength.setMajorTickSpacing(2);
		maxLength.setPaintLabels(true);
		maxLength.setSnapToTicks(true);
		maxLength.setBackground(this.panel.getBackground());	
				   							   
		panel.add(generate); generate.setBounds(rec.width/32, (rec.height*50)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*5)/40); 
		generate.setEnabled(false); 
		generate.addActionListener(this);
		
		panel.add(ranGen); ranGen.setBounds(rec.width/32, (rec.height*63)/80, rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), (rec.height*5)/40); 
		ranGen.addActionListener(this);
		
		panel.setLayout(null);
		setContentPane(panel);		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == loadFile) {
			String input = FileControler.fileToByteArray(this);
			if(input!=null) {
				Evaluator.set(input);
				if(input.length()>44) {
					Program.log("First 44 chars from loaded file:\n"+input.substring(0, 43));
				}
				else Program.log("Loaded file:\n"+input);
				this.generate.setEnabled(true);
				Evaluator.evaluate();
				Evaluator.clear();
			}
			else {
				Program.error("MainWindow.actionPerformed.loadFile:\nThe input from file was null.");
				this.generate.setEnabled(false);
			}
		}
		else
		if(source == generate) {
			try {
				Generator.generate((Integer)noBox.getSelectedItem(), minLength.getValue(), maxLength.getValue());
			}
			catch(Exception e) {
				Program.error("MainWindow.actionPerformed.generate\n"+e.toString());
			}
			generate.setEnabled(false);
			Program.sleep(100);
			generate.setEnabled(true);
			
		}
		else
		if(source==ranGen) {
			try {
				Generator.ranGen((Integer)noBox.getSelectedItem(), minLength.getValue(), maxLength.getValue());
			}
			catch(Exception e) {
				Program.error("MainWindow.actionPerformed.ranGen\n"+e.toString());
			}
			ranGen.setEnabled(false);
			Program.sleep(100);
			ranGen.setEnabled(true);
		}
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
		if(source == minLength) {
			int value = minLength.getValue();
			maxLength.setMinimum(value);
		}
		else
		if(source == maxLength) {
			int value = maxLength.getValue();
			minLength.setMaximum(value);
		}
	}
}
