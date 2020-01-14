package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Core.Evaluator;
import Core.FileControler;
import Core.Program;


public class MainWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JPanel panel = new JPanel();
	
	JTextArea area = new JTextArea();
	
	JScrollPane pane = new JScrollPane(area);
	
	JButton loadFile = new JButton("Load");
	JButton evaluate = new JButton("Evaluate");
	
	
	public MainWindow() {
		setSize(640, 640);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.panel.setBackground(new Color(0, 43, 60));
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
		panel.add(pane); pane.setBounds        (rec.width-Program.AREA_WIDTH+(20*Program.AREA_WIDTH)/320, 
											   (rec.height)/80, 
											   (Program.AREA_WIDTH*7)/8, 
											   (rec.height*36)/40); 
		area.setEditable(false); area.setBackground(new Color(200,200,200));
		panel.add(loadFile); loadFile.setBounds(rec.width/32, 
											   (rec.height)/80, 
											    rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), 
											   (rec.height*5)/40); loadFile.addActionListener(this);
		panel.add(evaluate); evaluate.setBounds(rec.width/32, 
				   							   (rec.height*13)/80, 
				   							   rec.width-Program.AREA_WIDTH-(rec.width/16)+((20*Program.AREA_WIDTH)/320), 
				   							   (rec.height*5)/40); evaluate.setEnabled(false); evaluate.addActionListener(this);
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
				this.evaluate.setEnabled(true);
			}
			else {
				Program.error("MainWindow.actionPerformed.loadFile:\nThe input from file was null.");
				this.evaluate.setEnabled(false);
			}
		}
		else
		if(source == evaluate) {
			Evaluator.evaluate();
		}
	}
}
