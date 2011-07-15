package org.vanb.viva.gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;

import net.miginfocom.swing.*;

import org.vanb.viva.*;
import org.vanb.viva.utils.*;

public class VIVAGUI
{

    private JFrame frmViva;
    private JTextField patternFileField;
    private JTextField inputFilesField;
    private JButton btnLoadPattern;
    private JButton btnSavePattern;
    private JButton btnParsePattern;
    private JButton btnTestInput;
    private JButton btnSaveAsPattern;
    private JButton btnLoadInput;
    private JButton btnClearOutput;

    private VIVA viva;
    private JTextArea patternText;
    private JScrollPane patternPane;
    private JTextArea outputsText;
    private JScrollPane outputsPane;
    private JLabel lblViva;
    
    private JFileChooser patternFileChooser;   
    private JFileChooser inputFileChooser;
    
    private class PatternTextDocumentListener implements DocumentListener
    {
        public void setButtons()
        {
            btnSavePattern.setEnabled( true );    
            btnSaveAsPattern.setEnabled( true );    
            btnParsePattern.setEnabled( true );    
            btnTestInput.setEnabled( false );    
        }
        
        public void changedUpdate( DocumentEvent de )
        {
            setButtons();   
        }
        
        public void insertUpdate( DocumentEvent de )
        {
            setButtons();     
        }
        
        public void removeUpdate( DocumentEvent de )
        {
            setButtons();   
        }        
    }
    
    private class Appender implements Runnable
    {
        private String text;
        
        public Appender( String t )
        {
            text = t;
        }
        
        public void run()
        {
            outputsText.append( text );
        }
    }
    
    private class OutputsWriter extends Thread
    {
        private InputStream in;   
        
        public OutputsWriter( InputStream stream )
        {
            in = stream;
        }
        
        public void run()
        {
            for(;;)
            {
                try
                {
                    char ch = (char)in.read();
                    EventQueue.invokeLater( new Appender( "" + ch ) );
                }
                catch( IOException ioe )
                {
                    System.err.println( "IO Exception in OutputsWriter. " + ioe.getMessage() );
                    break;
                }
            }
        }
    }
    
    private PatternTextDocumentListener listener = new PatternTextDocumentListener();

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                try
                {
                    VIVAGUI window = new VIVAGUI();
                    window.frmViva.setVisible( true );
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the application.
     */
    public VIVAGUI()
    {
        initialize();
    }

    private void parse()
    {
        ps.println();
        ps.println( "<<< Parsing Pattern >>>");
        boolean success = viva.setPattern( 
            new ByteArrayInputStream( patternText.getText().getBytes() ) );
        btnParsePattern.setEnabled( !success );
        btnTestInput.setEnabled( success );
        ps.println( "<<< DONE Parsing Pattern >>>");
    }
    
    private PipedInputStream is;
    private PipedOutputStream os;
    private PrintStream ps;
    OutputsWriter writer;

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        viva = new VIVA();
        
        try
        {
            os = new PipedOutputStream();
            is = new PipedInputStream( os );
        }
        catch( IOException ioe )
        {
            System.err.println( "ACK!! " + ioe.getMessage() );   
        }
        
        writer = new OutputsWriter( is );
        writer.start();
        ps = new PrintStream( os );
        viva.setOutputStream( ps );
        
        patternFileChooser = new JFileChooser();
        patternFileChooser.setFileFilter( new FileNameExtensionFilter( "VIVA Pattern files", "viva" ) );
        patternFileChooser.setDialogTitle( "VIVA Pattern File" );
        
        inputFileChooser = new JFileChooser();
        inputFileChooser.setMultiSelectionEnabled( true );
        inputFileChooser.setDialogTitle( "Judge Input File(s)" );

        frmViva = new JFrame();
        frmViva.setTitle( "VIVA" );
        frmViva.setBounds( 100, 100, 1442, 682 );
        frmViva.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frmViva.getContentPane().setLayout( new MigLayout("", "[108px][-2.00px][761.00px][725px,grow,fill]", "[27px][][5px][23px][][6px][116px][][473px,grow,fill]") );

        JLabel lblPatternFile = new JLabel( "Pattern File:" );
        frmViva.getContentPane().add( lblPatternFile, "cell 0 0,alignx right,growy" );

        patternFileField = new JTextField();
        patternFileField.setBackground(Color.WHITE);
        patternFileField.setEditable(false);
        frmViva.getContentPane().add( patternFileField, "cell 2 0,growx,aligny center" );
        patternFileField.setColumns( 10 );

        patternPane = new JScrollPane();
        patternPane.setViewportBorder( new BevelBorder( BevelBorder.LOWERED, null, null, null, null ) );
        frmViva.getContentPane().add( patternPane, "cell 3 0 1 8,grow" );

        patternText = new JTextArea();
        patternPane.setViewportView( patternText );
        patternText.setFont( new Font( "Courier New", Font.PLAIN, 14 ) );
        patternText.getDocument().addDocumentListener( listener );
        
        btnLoadPattern = new JButton( "Load" );
        btnLoadPattern.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent arg0 )
            {
                patternFileChooser.setApproveButtonText( "Load" );
                patternFileChooser.showOpenDialog( frmViva );
                File patternFile = null;
                try
                {
                    patternFile = patternFileChooser.getSelectedFile();
                    if( patternFile!=null )
                    {
                        FileReader fr = new FileReader( patternFile );
                        patternText.read( fr, "VIVA Pattern" );
                        patternText.getDocument().addDocumentListener( listener );
                        patternFileField.setText( patternFile.getAbsolutePath() );
                        ps.println( "Successfully loaded Pattern file " + patternFile.getAbsolutePath() );
                        parse();
                    }
                }
                catch( FileNotFoundException fnfe )
                {
                    ps.println( "Unable to find Pattern file: " + patternFile.getName() );
                }
                catch( IOException ioe )
                {
                    ps.println( "Unable to read Pattern file: " + patternFile.getName() );
                }
            }
        } );
        frmViva.getContentPane().add( btnLoadPattern, "flowx,cell 2 1,alignx left,aligny center" );

        JLabel lblInputFiles = new JLabel( "Input Files:" );
        frmViva.getContentPane().add( lblInputFiles, "cell 0 3,alignx right,aligny top" );

        inputFilesField = new JTextField();
        inputFilesField.setBackground(new Color(255, 255, 255));
        inputFilesField.setEditable(false);
        frmViva.getContentPane().add( inputFilesField, "cell 2 3,growx,aligny center" );
        inputFilesField.setColumns( 10 );
                
        btnLoadInput = new JButton("Identify");
        btnLoadInput.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                inputFileChooser.setApproveButtonText( "Identify" );
                inputFileChooser.showOpenDialog( frmViva );
                File inputFiles[] = inputFileChooser.getSelectedFiles();
                if( inputFiles!=null && inputFiles.length>0 )
                {
                    String fileNames = "";
                    boolean first = true;
                    for( File file : inputFiles )
                    {
                        if( first ) first=false; else fileNames += ";";
                        fileNames += file.getAbsolutePath();
                    }
                    inputFilesField.setText( fileNames );
                    ps.println( "Successfully identified Input file(s) " + fileNames );
                }
            }
        });
        frmViva.getContentPane().add(btnLoadInput, "flowx,cell 2 4");

        lblViva = new JLabel( "VIVA" );
        lblViva.setFont( new Font( "Arial", Font.PLAIN, 99 ) );
        frmViva.getContentPane().add( lblViva, "cell 2 6,alignx center,aligny center" );
        
        btnClearOutput = new JButton("Clear Output");
        btnClearOutput.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                outputsText.setText( "" );
            }
        });
        frmViva.getContentPane().add(btnClearOutput, "cell 0 7");

        outputsPane = new JScrollPane();
        frmViva.getContentPane().add( outputsPane, "cell 0 8 4 1,grow" );

        outputsText = new JTextArea();
        outputsText.setFont( new Font( "Tahoma", Font.PLAIN, 14 ) );
        outputsText.setBackground( Color.WHITE );
        outputsText.setEditable( false );
        outputsPane.setViewportView( outputsText );
        
        btnTestInput = new JButton( "Test" );
        btnTestInput.setEnabled( false );
        btnTestInput.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent arg0 )
            {
                File inputFiles[] = inputFileChooser.getSelectedFiles();
                if( inputFiles!=null && inputFiles.length>0 )
                {
                    for( File file : inputFiles )
                    {
                        ps.println();
                        viva.testInputFile( file.getAbsolutePath() );
                    }
                }
            }
        } );
        frmViva.getContentPane().add( btnTestInput, "cell 2 4,alignx left,growy" );

        btnSavePattern = new JButton( "Save" );
        btnSavePattern.setEnabled(false);
        btnSavePattern.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent arg0 )
            {
                File patternFile = patternFileChooser.getSelectedFile();
                try
                {
                    if( patternFile!=null )
                    {
                        FileOutputStream fos = new FileOutputStream( patternFile );
                        fos.write( patternText.getText().getBytes() );
                        btnSavePattern.setEnabled( false );
                        btnSaveAsPattern.setEnabled( false );
                        ps.println( "Successfully saved Pattern file " + patternFile.getAbsolutePath() );
                    }
                }
                catch( IOException ioe )
                {
                    ps.println( "Unable to write Pattern file: " + patternFile.getName() );
                }
            }
        } );
        frmViva.getContentPane().add( btnSavePattern, "cell 2 1,alignx left,aligny center" );
        
        btnSaveAsPattern = new JButton("Save As");
        btnSaveAsPattern.setEnabled(false);
        btnSaveAsPattern.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                patternFileChooser.setApproveButtonText( "Save" );
                patternFileChooser.showSaveDialog( frmViva );
                File patternFile = patternFileChooser.getSelectedFile();
                try
                {
                    if( patternFile!=null )
                    {
                        FileOutputStream fos = new FileOutputStream( patternFile );
                        fos.write( patternText.getText().getBytes() );
                        btnSavePattern.setEnabled( false );
                        btnSaveAsPattern.setEnabled( false );
                        ps.println( "Successfully saved Pattern file " + patternFile.getAbsolutePath() );
                    }
                }
                catch( IOException ioe )
                {
                    ps.println( "Unable to write Pattern file: " + patternFile.getName() );
                }

            }
        });
        frmViva.getContentPane().add(btnSaveAsPattern, "cell 2 1,alignx left");
        
        btnParsePattern = new JButton( "Parse" );
        btnParsePattern.setEnabled(false);
        btnParsePattern.addActionListener( new ActionListener()
        {
            public void actionPerformed( ActionEvent arg0 )
            {
                parse();
            }
        } );
        frmViva.getContentPane().add( btnParsePattern, "cell 2 1,alignx center,aligny center" );
    }
}
