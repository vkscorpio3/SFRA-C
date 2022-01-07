/**
 * cdwFrame.java
 *
 * This file was generated by MapForce 2021r2sp1.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */


package com.mapforce;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.altova.types.*;


public class cdwFrame extends JFrame implements com.altova.TraceTarget {
	
	JPanel			contentPane;
	TitledBorder	titledBorder1;
	Box 		jHeader = new Box(BoxLayout.X_AXIS);
	Box		jHeaderInfo = new Box(BoxLayout.Y_AXIS);
	Box 		jButtonPane = new Box(BoxLayout.X_AXIS);
	JScrollPane	jScrollPaneStructures	= new JScrollPane();
	JPanel		jPanelStructures		= new JPanel();
	JLabel		jIconLabel				= new JLabel();
	JLabel		jInfoLabel1				= new JLabel();
	JLabel		jInfoLabel2				= new JLabel();
	JLabel		jInfoLabel3				= new JLabel();
	JButton		jStartButton			= new JButton();
	JPanel		jPanel1					= new JPanel();
	JScrollPane	jTraceScrollPane		= new JScrollPane();
	JTextArea	jTraceTextArea			= new JTextArea();


	JLabel jproduct_feed2Label0 = new JLabel();
	JTextField jproduct_feed2TextField0 = new JTextField();

	JLabel jcatalog2Label1 = new JLabel();
	JTextField jcatalog2TextField1 = new JTextField();

	JLabel jproduct_alone2Label2 = new JLabel();
	JTextField jproduct_alone2TextField2 = new JTextField();

	JLabel jcatalog3Label3 = new JLabel();
	JTextField jcatalog3TextField3 = new JTextField();

	JLabel jcdwjson_22Label4 = new JLabel();
	JTextField jcdwjson_22TextField4 = new JTextField();

	JLabel jcatalog4Label5 = new JLabel();
	JTextField jcatalog4TextField5 = new JTextField();

	JLabel jcdwjson_23Label6 = new JLabel();
	JTextField jcdwjson_23TextField6 = new JTextField();

	JLabel jcatalog5Label7 = new JLabel();
	JTextField jcatalog5TextField7 = new JTextField();


	public cdwFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		jInfoLabel1.setText("THIS APPLICATION WAS GENERATED BY MapForce 2021r2sp1");
		jInfoLabel2.setForeground(Color.blue);
		jInfoLabel2.setText("http://www.altova.com/mapforce");
		jInfoLabel3.setText("Please check the input and output files, and press the Start button...");
		jHeaderInfo.add(jInfoLabel1,0);
		jHeaderInfo.add(jInfoLabel2,1);
		jHeaderInfo.add(jInfoLabel3,2);
		
		jIconLabel.setIcon(new ImageIcon(cdwFrame.class.getResource("mapforce.png")));
		jIconLabel.setText("");
				
		jHeader.add(jIconLabel);
		jHeader.add(Box.createHorizontalStrut(15));
		jHeader.add(jHeaderInfo);
		jHeader.add(Box.createGlue());
		
		jStartButton.setFont(new java.awt.Font("Dialog", 0, 11));
		jStartButton.setText("Start");
		jStartButton.addActionListener(new cdwFrame_jStartButton_actionAdapter(this));
		jButtonPane.add(Box.createHorizontalStrut(5));
		jButtonPane.add(jStartButton);
		jButtonPane.add(Box.createGlue());
				
		jScrollPaneStructures.setBorder(BorderFactory.createLineBorder(Color.black));
		jScrollPaneStructures.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneStructures.setAutoscrolls(true);
		jPanelStructures.setLayout(null);
		fillScrollPane();
		jScrollPaneStructures.getViewport().add(jPanelStructures, null);
		
		jTraceTextArea.setBackground(Color.white);
		jTraceTextArea.setForeground(Color.black);
		jTraceTextArea.setToolTipText("");
		jTraceTextArea.setText("");
		jTraceTextArea.setRows(20);
		
		jTraceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jTraceScrollPane.setViewportBorder(null);
		jTraceScrollPane.setAutoscrolls(true);
		jTraceScrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
		jTraceScrollPane.setDebugGraphicsOptions(0);
		jTraceScrollPane.setToolTipText("");
		jTraceScrollPane.setVerifyInputWhenFocusTarget(true);
		jTraceScrollPane.getViewport().add(jTraceTextArea, null);
		jTraceScrollPane.setPreferredSize(new Dimension(0, 200));
				
		contentPane = (JPanel)this.getContentPane();
		titledBorder1 = new TitledBorder("");
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		this.setSize(new Dimension(603, 511));
		this.setTitle("Mapforce Application");
		contentPane.add(jHeader, 0);
		contentPane.add(jScrollPaneStructures, 1);
		contentPane.add(jButtonPane, 2);
		contentPane.add(jTraceScrollPane, 3);
	}

	protected void fillScrollPane() {

		jcatalog2Label1.setText("Instance of catalog.xsd:");
		jcatalog2Label1.setBounds(new Rectangle(15, 60, 438, 23));
		jPanelStructures.add(jcatalog2Label1, null);
		jcatalog2TextField1.setText("catalog.xml");
		jcatalog2TextField1.setBounds(new Rectangle(15, 85, 438, 23));
		jcatalog2TextField1.setEditable(false);
		jPanelStructures.add(jcatalog2TextField1, null);

		jcatalog3Label3.setText("Instance of catalog.xsd:");
		jcatalog3Label3.setBounds(new Rectangle(15, 160, 438, 23));
		jPanelStructures.add(jcatalog3Label3, null);
		jcatalog3TextField3.setText("catalog.xml");
		jcatalog3TextField3.setBounds(new Rectangle(15, 185, 438, 23));
		jcatalog3TextField3.setEditable(false);
		jPanelStructures.add(jcatalog3TextField3, null);

		jcatalog4Label5.setText("Instance of catalog.xsd:");
		jcatalog4Label5.setBounds(new Rectangle(15, 260, 438, 23));
		jPanelStructures.add(jcatalog4Label5, null);
		jcatalog4TextField5.setText("../Finalcdw/catalog.xml");
		jcatalog4TextField5.setBounds(new Rectangle(15, 285, 438, 23));
		jcatalog4TextField5.setEditable(false);
		jPanelStructures.add(jcatalog4TextField5, null);

		jcatalog5Label7.setText("Instance of catalog.xsd:");
		jcatalog5Label7.setBounds(new Rectangle(15, 360, 438, 23));
		jPanelStructures.add(jcatalog5Label7, null);
		jcatalog5TextField7.setText("catalog.xml");
		jcatalog5TextField7.setBounds(new Rectangle(15, 385, 438, 23));
		jcatalog5TextField7.setEditable(false);
		jPanelStructures.add(jcatalog5TextField7, null);

		jPanelStructures.setLayout(null);
		jPanelStructures.setPreferredSize(new Dimension(385, 500));
		jPanelStructures.setSize(new Dimension(385, 500));
		jPanelStructures.setMinimumSize(new Dimension(385, 500));
		jPanelStructures.setMaximumSize(new Dimension(385, 500));
	}

	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	void jStartButton_actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jStartButton)) {
			jStartButton.setEnabled(false);
			jTraceTextArea.removeAll();
			jTraceTextArea.append("Started...\n");
			com.altova.TraceTarget ttc = this;


			try {

		try {
			MappingMapTocatalog MappingMapTocatalogObject = new MappingMapTocatalog();




			MappingMapTocatalogObject.registerTraceTarget(ttc);
	

			// run mapping
			//
			// you have different options to provide mapping input and output:
			//
			// files using file names (available for XML, text, and Excel):
			//   com.altova.io.FileInput(String filename)
			//   com.altova.io.FileOutput(String filename)
			//
			// streams (available for XML, text, and Excel):
			//   com.altova.io.StreamInput(java.io.InputStream stream)
			//   com.altova.io.StreamOutput(java.io.OutputStream stream)
			//
			// strings (available for XML and text):
			//   com.altova.io.StringInput(String xmlcontent)
			//   com.altova.io.StringOutput()	(call getContent() after run() to get StringBuffer with content)
			//
			// Java IO reader/writer (available for XML and text):
			//   com.altova.io.ReaderInput(java.io.Reader reader)
			//   com.altova.io.WriterOutput(java.io.Writer writer)
			//
			// DOM documents (for XML only):
			//   com.altova.io.DocumentInput(org.w3c.dom.Document document)
			//   com.altova.io.DocumentOutput(org.w3c.dom.Document document)
			// 
			// By default, run will close all inputs and outputs. If you do not want this,
			// call the following function:
			// MappingMapTocatalogObject.setCloseObjectsAfterRun(false);

			
			com.altova.io.Input product_feed2Source = com.altova.io.StreamInput.createInput("product-feed.json");
			com.altova.io.Output catalog2Target = new com.altova.io.FileOutput("catalog.xml");

			try {
				MappingMapTocatalogObject.run(
						product_feed2Source,
						catalog2Target);

			} finally {
				(product_feed2Source).close();
				catalog2Target.close();
			}

		} finally {
		}



				jTraceTextArea.append("Finished\n");
			} catch (Exception ex) {
				jTraceTextArea.append("ERROR: " + ex.getMessage());
			}

			try {

		try {
			MappingMapTocatalog2 MappingMapTocatalog2Object = new MappingMapTocatalog2();




			MappingMapTocatalog2Object.registerTraceTarget(ttc);
	

			// run mapping
			//
			// you have different options to provide mapping input and output:
			//
			// files using file names (available for XML, text, and Excel):
			//   com.altova.io.FileInput(String filename)
			//   com.altova.io.FileOutput(String filename)
			//
			// streams (available for XML, text, and Excel):
			//   com.altova.io.StreamInput(java.io.InputStream stream)
			//   com.altova.io.StreamOutput(java.io.OutputStream stream)
			//
			// strings (available for XML and text):
			//   com.altova.io.StringInput(String xmlcontent)
			//   com.altova.io.StringOutput()	(call getContent() after run() to get StringBuffer with content)
			//
			// Java IO reader/writer (available for XML and text):
			//   com.altova.io.ReaderInput(java.io.Reader reader)
			//   com.altova.io.WriterOutput(java.io.Writer writer)
			//
			// DOM documents (for XML only):
			//   com.altova.io.DocumentInput(org.w3c.dom.Document document)
			//   com.altova.io.DocumentOutput(org.w3c.dom.Document document)
			// 
			// By default, run will close all inputs and outputs. If you do not want this,
			// call the following function:
			// MappingMapTocatalog2Object.setCloseObjectsAfterRun(false);

			
			com.altova.io.Input product_alone2Source = com.altova.io.StreamInput.createInput("product-alone.json");
			com.altova.io.Output catalog3Target = new com.altova.io.FileOutput("catalog.xml");

			try {
				MappingMapTocatalog2Object.run(
						product_alone2Source,
						catalog3Target);

			} finally {
				(product_alone2Source).close();
				catalog3Target.close();
			}

		} finally {
		}



				jTraceTextArea.append("Finished\n");
			} catch (Exception ex) {
				jTraceTextArea.append("ERROR: " + ex.getMessage());
			}

			try {

		try {
			MappingMapTocatalog3 MappingMapTocatalog3Object = new MappingMapTocatalog3();




			MappingMapTocatalog3Object.registerTraceTarget(ttc);
	

			// run mapping
			//
			// you have different options to provide mapping input and output:
			//
			// files using file names (available for XML, text, and Excel):
			//   com.altova.io.FileInput(String filename)
			//   com.altova.io.FileOutput(String filename)
			//
			// streams (available for XML, text, and Excel):
			//   com.altova.io.StreamInput(java.io.InputStream stream)
			//   com.altova.io.StreamOutput(java.io.OutputStream stream)
			//
			// strings (available for XML and text):
			//   com.altova.io.StringInput(String xmlcontent)
			//   com.altova.io.StringOutput()	(call getContent() after run() to get StringBuffer with content)
			//
			// Java IO reader/writer (available for XML and text):
			//   com.altova.io.ReaderInput(java.io.Reader reader)
			//   com.altova.io.WriterOutput(java.io.Writer writer)
			//
			// DOM documents (for XML only):
			//   com.altova.io.DocumentInput(org.w3c.dom.Document document)
			//   com.altova.io.DocumentOutput(org.w3c.dom.Document document)
			// 
			// By default, run will close all inputs and outputs. If you do not want this,
			// call the following function:
			// MappingMapTocatalog3Object.setCloseObjectsAfterRun(false);

			
			com.altova.io.Input cdwjson_22Source = com.altova.io.StreamInput.createInput("product-feed 7.json");
			com.altova.io.Output catalog4Target = new com.altova.io.FileOutput("../Finalcdw/catalog.xml");

			try {
				MappingMapTocatalog3Object.run(
						cdwjson_22Source,
						catalog4Target);

			} finally {
				(cdwjson_22Source).close();
				catalog4Target.close();
			}

		} finally {
		}



				jTraceTextArea.append("Finished\n");
			} catch (Exception ex) {
				jTraceTextArea.append("ERROR: " + ex.getMessage());
			}

			try {

		try {
			MappingMapTocatalog4 MappingMapTocatalog4Object = new MappingMapTocatalog4();




			MappingMapTocatalog4Object.registerTraceTarget(ttc);
	

			// run mapping
			//
			// you have different options to provide mapping input and output:
			//
			// files using file names (available for XML, text, and Excel):
			//   com.altova.io.FileInput(String filename)
			//   com.altova.io.FileOutput(String filename)
			//
			// streams (available for XML, text, and Excel):
			//   com.altova.io.StreamInput(java.io.InputStream stream)
			//   com.altova.io.StreamOutput(java.io.OutputStream stream)
			//
			// strings (available for XML and text):
			//   com.altova.io.StringInput(String xmlcontent)
			//   com.altova.io.StringOutput()	(call getContent() after run() to get StringBuffer with content)
			//
			// Java IO reader/writer (available for XML and text):
			//   com.altova.io.ReaderInput(java.io.Reader reader)
			//   com.altova.io.WriterOutput(java.io.Writer writer)
			//
			// DOM documents (for XML only):
			//   com.altova.io.DocumentInput(org.w3c.dom.Document document)
			//   com.altova.io.DocumentOutput(org.w3c.dom.Document document)
			// 
			// By default, run will close all inputs and outputs. If you do not want this,
			// call the following function:
			// MappingMapTocatalog4Object.setCloseObjectsAfterRun(false);

			
			com.altova.io.Input cdwjson_23Source = com.altova.io.StreamInput.createInput("product-feed 7.json");
			com.altova.io.Output catalog5Target = new com.altova.io.FileOutput("catalog.xml");

			try {
				MappingMapTocatalog4Object.run(
						cdwjson_23Source,
						catalog5Target);

			} finally {
				(cdwjson_23Source).close();
				catalog5Target.close();
			}

		} finally {
		}



				jTraceTextArea.append("Finished\n");
			} catch (Exception ex) {
				jTraceTextArea.append("ERROR: " + ex.getMessage());
			}

			jStartButton.setEnabled(true);
		}
	}


	public void writeTrace(String info) {
		jTraceTextArea.append(info);
	}
}

class cdwFrame_jStartButton_actionAdapter
	implements java.awt.event.ActionListener {
	cdwFrame adaptee;

	cdwFrame_jStartButton_actionAdapter(cdwFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jStartButton_actionPerformed(e);
	}
}

