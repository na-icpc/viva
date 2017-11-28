package org.vanb.viva.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultCaret;

import net.miginfocom.swing.MigLayout;

import org.vanb.viva.VIVA;
import org.vanb.viva.parameters.Parameter;
import org.vanb.viva.parameters.StringListParameter;
import org.vanb.viva.utils.VIVAContext;

public class VIVAGUI implements ActionListener
{
    /** Here are all of the widgets that make up the GUI. */
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

    private JTextArea patternEditor;
    private JTextArea lineNumbers;
    private JScrollPane patternPane;
    private JTextArea outputsText;
    private JScrollPane outputsPane;
    private JLabel lblViva;

    private JFileChooser patternFileChooser;
    private JFileChooser inputFileChooser;
    
    private JTable parametersTable;
    private JDialog parametersDialog;
    
    private JTextField parametersError;
    
    private TableCellEditor editors[];

    /** VIVA! */
    private VIVA viva;
    private VIVAContext context;

    /**
     * IO stuff. We'll create a connected input & output streams. We'll pass the
     * output stream to VIVA, and that's where it will write its output. We'll
     * read that from the attached input stream in a separate thread, and then
     * write it to the outputs text box.
     */
    private PipedInputStream is;
    private PipedOutputStream os;

    /**
     * The PrintStream is a convenience. It'll be wrapped around the
     * OutputStream.
     */
    private PrintStream ps;
    
    private Actions actions = new Actions();

    /**
     * Adjust the line numbers on the left side of the Pattern editor.
     */
    public void setLineNumbers()
    {
        String text = "";
        for( int i = 1; i <= patternEditor.getLineCount(); i++ )
        {
            text += (i < 10 ? " " : "") + i
                    + System.getProperty( "line.separator" );
        }
        lineNumbers.setText( text );
    }

    /**
     * Enable/disable buttons.
     */
    public void setButtons()
    {
        // If the pattern has changed, then save, SaveAs and Parse
        // should all be enabled.
        btnSavePattern.setEnabled( true );
        btnSaveAsPattern.setEnabled( true );
        btnParsePattern.setEnabled( true );

        // We shouldn't allow the user to Test input
        // files until they re-parse the (changed) pattern.
        btnTestInput.setEnabled( false );
    }

    /**
     * This listener listens for changes in the Pattern text box. If the user
     * has made changes to the pattern, then some buttons need to be enabled or
     * disabled.
     * 
     * @author vanb
     */
    private class PatternTextDocumentListener implements DocumentListener
    {
        /**
         * If the pattern has changed, set the buttons.
         */
        public void changedUpdate( DocumentEvent de )
        {
            setButtons();
            setLineNumbers();
        }

        /**
         * If something's been inserted, set the buttons.
         */
        public void insertUpdate( DocumentEvent de )
        {
            setButtons();
            setLineNumbers();
        }

        /**
         * If something's been removed, set the buttons.
         */
        public void removeUpdate( DocumentEvent de )
        {
            setButtons();
            setLineNumbers();
        }
    }

    /**
     * An instance of the Listener. We only need one, might as well create it
     * here.
     */
    private PatternTextDocumentListener listener = new PatternTextDocumentListener();

    /**
     * Some text fields are not editable - but still navigable. Java, by
     * default, turns off the cursor (or, Caret) if a text widget isn't
     * editable. We want the cursor. So, we'll have to manage it explicitly.
     * 
     * @author vanb
     */
    private class VIVAFocusAdapter extends FocusAdapter
    {
        private JTextField text;

        /**
         * Create a FocusAdapter for a JTextField
         * 
         * @param t
         *            The JTextField
         */
        public VIVAFocusAdapter( JTextField t )
        {
            text = t;
        }

        @Override
        /**
         * Turn on the cursor of the field gets focus
         * @param fe A FocusEvent
         */
        public void focusGained( FocusEvent fe )
        {
            text.getCaret().setVisible( true );
        }

        @Override
        /**
         * Turn off the cursor of the field loses focus
         * @param fe A FocusEvent
         */
        public void focusLost( FocusEvent fe )
        {
            text.getCaret().setVisible( false );
        }
    }

    /**
     * A worker for the Swing thread to update the Outputs text widget.
     * 
     * @author vanb
     */
    private class OutputsWorker extends SwingWorker<String,String>
    {
        /** The input stream */
        private InputStream in;
        
        /** Build a line of input */
        private StringBuilder builder = new StringBuilder();

        /**
         * Create one of these, remember the input stream.
         * 
         * @param stream
         *            The input stream to read
         */
        public OutputsWorker( InputStream stream )
        {
            in = stream;
        }

        @Override
        /**
         * In background, collect characters into lines and send them to Swing to be processed. 
         */
        protected String doInBackground() throws Exception
        {
            while( !isCancelled() )
            {
                try
                {
                    char ch = (char) in.read();
                    builder.append( ch );
                    if( ch=='\n' )
                    {
                        publish( builder.toString() );
                        builder.setLength( 0 );
                    }
                }
                catch( IOException ioe )
                {
                    System.err.println( "IO Exception in OutputsWorker. " + ioe.getMessage() );
                    break;
                }
            }
            return null;
        }
        
        /**
         * Publish a list of lines to the Outputs text area.
         * 
         * @param lines A list of lines
         */
        protected void process( List<String> lines )
        {
            for( String line : lines ) outputsText.append( line );
        }
        
    }
    
    private class ParameterInputVerifier extends InputVerifier
    {
        private Parameter parameter;
        private String lastGood;
        private int row;
        
        public ParameterInputVerifier( Parameter parameter, String lastGood, int row )
        {
            this.parameter = parameter;    
            this.lastGood = lastGood;
            this.row = row;
        }
        
        public boolean verify( JComponent field )
        {
            boolean ok = true;
            JTextField textField = (JTextField)field;
            String text = textField.getText();

            Object value = parameter.convert( text );
            ok = value!=null;

            if( ok ) ok = parameter.isvalid( value );
            
            if( ok ) 
            {
                if( !text.equals( lastGood ) )
                {
                    parametersError.setText( "Warning: Changes will not take effect until Pattern is Parsed." );
                    btnParsePattern.setEnabled( true );
                }
                lastGood = text;
            }
            else
            {
                textField.setText( lastGood );
                parametersTable.getModel().setValueAt( lastGood, row, 1 );
                parametersError.setText( parameter.getName() + " " + parameter.usage() );
            }
            
            return ok;
        }
        
    }

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

    /**
     * Parse the pattern in the Pattern editor.
     */
    private void parse()
    {
        // First, reset parameters        
        TableModel model = parametersTable.getModel();
        for( int i=0; i<model.getRowCount(); i++ )
        {
            String parm = model.getValueAt( i, 0 ).toString();
            Parameter parameter = context.parameters.get( parm );
            Object value = parameter.convert( model.getValueAt( i, 1 ).toString() );
            parameter.setCurrentValue( value );
            parameter.action( context, value );
        }

        ps.println( "<<< Parsing Pattern >>>" );
        boolean success = viva.setPattern( 
            new ByteArrayInputStream( patternEditor.getText().getBytes() ) );
        btnParsePattern.setEnabled( !success );
        btnTestInput.setEnabled( success );
        ps.println( "<<< DONE Parsing Pattern >>>" );
        ps.println();
    }

    /**
     * Convenience function to write a line of text to the output window,
     * followed by a blank line for spacing.
     * 
     * @param text
     *            Text to write to the output window.
     */
    private void write( String text )
    {
        ps.println( text );
        ps.println();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        // VIVA!
        viva = new VIVA();
        context = viva.getContext();

        // Create the connected io streams
        try
        {
            os = new PipedOutputStream();
            is = new PipedInputStream( os );
        }
        catch( IOException ioe )
        {
            System.err.println( "ACK!! " + ioe.getMessage() );
        }

        // Start the writer thread
//        writer = new OutputsWriter( is );
//        writer.start();
        (new OutputsWorker( is )).execute();

        // Wrap the OutputStream in a PrintStream
        ps = new PrintStream( os );
        viva.setOutputStream( ps );

        // This is the dialog to choose a Pattern file.
        // It is used for both Load and SaveAs.
        patternFileChooser = new JFileChooser();
        patternFileChooser.setFileFilter( new FileNameExtensionFilter( "VIVA Pattern files", "viva" ) );
        patternFileChooser.setDialogTitle( "VIVA Pattern File" );

        // This is the dialog for choosing input files to test.
        inputFileChooser = new JFileChooser();
        inputFileChooser.setMultiSelectionEnabled( true );
        inputFileChooser.setFileFilter( new FileNameExtensionFilter( "Input files", "in" ) );
        inputFileChooser.setDialogTitle( "Judge Input File(s)" );

        // The overall frame
        frmViva = new JFrame();
        frmViva.setTitle( "VIVA!" );
        frmViva.setBounds( 100, 100, 1442, 682 );
        frmViva.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frmViva.getContentPane().setLayout( new MigLayout( "",
                                "[108px][-2.00px][761.00px][725px,grow,fill]",
                                "[27px][][5px][23px][][6px][116px][][473px,grow,fill]" ) );

        // The Pattern File label
        JLabel lblPatternFile = new JLabel( "Pattern File:" );
        frmViva.getContentPane().add( lblPatternFile, "cell 0 0,alignx right,growy" );

        // The Pattern File text field.
        // It's not editable - it just shows the name of the Pattern file.
        patternFileField = new JTextField();
        patternFileField.addFocusListener( new VIVAFocusAdapter( patternFileField ) );
        patternFileField.setEditable( false );
        patternFileField.setBackground( Color.WHITE );
        patternFileField.setColumns( 10 );
        frmViva.getContentPane().add( patternFileField, "cell 2 0,growx,aligny center" );

        // The Pattern editor
        patternEditor = new JTextArea();
        patternEditor.setFont( new Font( "Courier New", Font.PLAIN, 14 ) );
        patternEditor.getDocument().addDocumentListener( listener );

        // Add line numbers to the Pattern editor
        lineNumbers = new JTextArea( " 1" );
        lineNumbers.setFont( new Font( "Courier New", Font.PLAIN, 14 ) );
        lineNumbers.setEditable( false );
        lineNumbers.setBackground( Color.LIGHT_GRAY );
        ((DefaultCaret) lineNumbers.getCaret()).setUpdatePolicy( DefaultCaret.NEVER_UPDATE );

        // The scroll bars around the Pattern editor
        patternPane = new JScrollPane();
        patternPane.setViewportBorder( new BevelBorder( BevelBorder.LOWERED, null, null, null, null ) );
        patternPane.setViewportView( patternEditor );
        patternPane.setRowHeaderView( lineNumbers );
        frmViva.getContentPane().add( patternPane, "cell 3 0 1 8,grow" );

        // The button to Load a pattern file
        btnLoadPattern = new JButton( "Load" );
        btnLoadPattern.addActionListener( this );
        btnLoadPattern.setMnemonic( KeyEvent.VK_L );
        frmViva.getContentPane().add( btnLoadPattern, "flowx,cell 2 1,alignx left,aligny center" );

        // The Input Files label
        JLabel lblInputFiles = new JLabel( "Input Files:" );
        frmViva.getContentPane().add( lblInputFiles, "cell 0 3,alignx right,aligny top" );

        // The Input Files field
        inputFilesField = new JTextField();
        inputFilesField.addFocusListener( new VIVAFocusAdapter( inputFilesField ) );
        inputFilesField.setEditable( false );
        inputFilesField.setBackground( Color.WHITE );
        inputFilesField.setColumns( 10 );
        frmViva.getContentPane().add( inputFilesField, "cell 2 3,growx,aligny center" );

        // The button to Identify input files to test
        btnLoadInput = new JButton( "Identify" );
        btnLoadInput.addActionListener( this );
        btnLoadInput.setMnemonic( KeyEvent.VK_I );
        frmViva.getContentPane().add( btnLoadInput, "flowx,cell 2 4" );

        // VIVA!
        lblViva = new JLabel( "VIVA!" );
        lblViva.setFont( new Font( "Arial", Font.PLAIN, 99 ) );
        frmViva.getContentPane().add( lblViva, "cell 2 6,alignx center,aligny center" );

        // Clear the outputs window
        btnClearOutput = new JButton( "Clear Output" );
        btnClearOutput.addActionListener( this );
        btnClearOutput.setMnemonic( KeyEvent.VK_C );
        frmViva.getContentPane().add( btnClearOutput, "cell 0 7" );

        // The outputs text
        outputsText = new JTextArea();
        outputsText.setFont( new Font( "Tahoma", Font.PLAIN, 14 ) );
        outputsText.setBackground( Color.WHITE );
        outputsText.setEditable( false );
        ((DefaultCaret) outputsText.getCaret()).setUpdatePolicy( DefaultCaret.UPDATE_WHEN_ON_EDT );

        // The scroll bars around the outputs text
        outputsPane = new JScrollPane();
        outputsPane.setViewportView( outputsText );
        outputsPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        frmViva.getContentPane().add( outputsPane, "cell 0 8 4 1,grow" );

        // The button to test the identified input files
        btnTestInput = new JButton( "Test" );
        btnTestInput.setEnabled( false );
        btnTestInput.addActionListener( this );
        btnTestInput.setMnemonic( KeyEvent.VK_T );
        frmViva.getContentPane().add( btnTestInput, "cell 2 4,alignx left,growy" );

        // The button to Save the contents of the Pattern editor
        btnSavePattern = new JButton( "Save" );
        btnSavePattern.setEnabled( false );
        btnSavePattern.addActionListener( this );
        btnSavePattern.setMnemonic( KeyEvent.VK_S );
        frmViva.getContentPane().add( btnSavePattern, "cell 2 1,alignx left,aligny center" );

        // Button to Save the pattern, specifying the file
        btnSaveAsPattern = new JButton( "Save As" );
        btnSaveAsPattern.setEnabled( false );
        btnSaveAsPattern.addActionListener( this );
        btnSaveAsPattern.setMnemonic( KeyEvent.VK_A );
        btnSaveAsPattern.setDisplayedMnemonicIndex( 5 );
        frmViva.getContentPane().add( btnSaveAsPattern, "cell 2 1,alignx left" );

        // The button to Parse whatever's in the Pattern editor
        btnParsePattern = new JButton( "Parse" );
        btnParsePattern.setEnabled( false );
        btnParsePattern.addActionListener( this );
        btnParsePattern.setMnemonic( KeyEvent.VK_P );
        frmViva.getContentPane().add( btnParsePattern, "cell 2 1,alignx center,aligny center" );
        

        // Start to work with VIVA's parameters. First, gather their names.
        String parms[] = (String[])context.parameters.keySet().toArray(new String[context.parameters.size()]);
        Arrays.sort( parms );
        
        // Build a table of Parameters
        String columns[] = { "Parameter", "Value" };   
        Object table[][] = new Object[parms.length][2];
        editors = new TableCellEditor[parms.length];
        for( int i=0; i<parms.length; i++ )
        {
            Parameter parameter = context.parameters.get( parms[i] );
            table[i][0] = parms[i];
            if( parameter instanceof StringListParameter ) 
            {
                // If it's a StringListParameter, we'll set up a ComboBox for it.
                String values[] = ((StringListParameter) parameter).values;
                List<String> v = Arrays.asList( values );
                
                // If it's a True/False parameter, then we'll only offer true and false,
                // not all the others (like t, f, yes, no, y, n, 0, 1)
                if( v.contains( "true" ) && v.contains( "false" ) ) values = new String[] { "true", "false" };
                
                // Create the pulldown menu (aka JComboBox) and make it the default editor for this cell
                JComboBox<String> pulldown = new JComboBox<String>( values ); 
                pulldown.setSelectedItem( parameter.getCurrentValue() );
                editors[i] = new DefaultCellEditor( pulldown );
                
                // If the item changes, we'll want to display a message and enable the Parse button.
                pulldown.addItemListener( new ItemListener()
                {
                    @Override
                    public void itemStateChanged( ItemEvent ie )
                    {
                        if( ie.getStateChange()==ItemEvent.SELECTED )
                        {
                            JComboBox<String> pulldown = (JComboBox<String>)ie.getSource();
                            parametersError.setText( "Warning: Changes will not take effect until Pattern is Parsed." );
                            btnParsePattern.setEnabled( true );
                        }
                    }   
                });
            }
            else 
            {
                // If it's not a StringListParameter, then it's a Double, Float, Integer or Long.
                // We'll use a JTextField for them.
                JTextField textField = new JTextField( parameter.getCurrentValue().toString() );
                textField.setInputVerifier( new ParameterInputVerifier( parameter, textField.getText(), i ) );
                editors[i] = new DefaultCellEditor( textField );
                editors[i].addCellEditorListener( new CellEditorListener()
                {
                    @Override
                    public void editingCanceled( ChangeEvent ce )
                    {
                    }

                    @Override
                    public void editingStopped( ChangeEvent ce )
                    {
                        DefaultCellEditor editor = (DefaultCellEditor)ce.getSource();
                        JTextField textField = (JTextField)editor.getComponent();
                        textField.getInputVerifier().verify( textField );
                    }
                });

            }
            table[i][1] = parameter.getCurrentValue();
        }
        
        // Create a graphical version of that table
        parametersTable = new JTable( table, columns )
        {
            public TableCellEditor getCellEditor( int row, int col )
            {
                return col==1 && editors[row]!=null ? editors[row] : super.getCellEditor( row, col );
            }
            
            public boolean isCellEditable( int row, int col )
            {
                return col==1;
            }
        };
        
        parametersTable.putClientProperty( "terminateEditOnFocusLost", Boolean.TRUE );
                   
        // A field for error messages when changing parameter values
        parametersError = new JTextField( "\t\t\t\t\t" );
        parametersError.setEnabled( true );
        parametersError.setEditable( false );
        
        // Put'em all together on a Panel
        JPanel parametersPanel = new JPanel();
        parametersPanel.setLayout( new GridBagLayout() );
        
        // The table
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;   
        constraints.gridx = 0;        
        constraints.gridy = 0;
        parametersPanel.add( parametersTable, constraints );
        
        // A place for error messages
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;    
        constraints.gridx = 0;        
        constraints.gridy = 1;
        parametersPanel.add( parametersError, constraints );
               
        // Put the JPanel on a JDialog
        parametersDialog = new JDialog( frmViva, "Parameters", true ); 
        parametersDialog.add( parametersPanel );
        parametersDialog.setResizable( false );
        
        // File menu has 2 options: Parameters, and Quit
        JMenu fileMenu = new JMenu( "File" );
        
        // Option to edit Parameters
        JMenuItem parametersMenuItem = new JMenuItem( "Parameters" );
        parametersMenuItem.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                parametersError.setText( "\t\t\t\t\t" );
                parametersDialog.pack();
                parametersDialog.setVisible( true );
            }
        });
        fileMenu.add( parametersMenuItem );
        
        // Just to make it harder for the user to choose Quit by mistake - add some distance.
        fileMenu.addSeparator();
        
        // Exit VIVA
        JMenuItem quitMenuItem = new JMenuItem( "Quit" );
        quitMenuItem.addActionListener( new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                int response = JOptionPane.showConfirmDialog( 
                        frmViva,
                        "Really Quit VIVA?", 
                        "Quit VIVA?", 
                        JOptionPane.OK_CANCEL_OPTION );
                if( response==JOptionPane.OK_OPTION ) System.exit(0);
            }
        });
        fileMenu.add( quitMenuItem );
        
        // Put the File menu on the top menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add( fileMenu );
        frmViva.setJMenuBar( menuBar );
        
        // This is a thread that performs actions when the user presses a button.
        // It has to be separate from the Swing thread.
        new Thread( actions ).start();

        // One last little bit of business.
        // The only place on the whole application where the user can type text
        // is the Pattern Editor. They can click in other places, but only the
        // Pattern Editor can take typing. The two text fields (Pattern File and Input Files)
        // have cursors for navigation, but you can't type in them. We only want for
        // them to have focus when the user explicitly clicks there, because that
        // flashing cursor is annoying. So, whenever VIVAGUI becomes active, we'll
        // automatically shift focus to the Pattern Editor.
        frmViva.addWindowFocusListener( new WindowAdapter()
        {
            public void windowGainedFocus( WindowEvent e )
            {
                patternEditor.requestFocusInWindow();
            }
        } );
    }
    /**
     * A thread to perform the actions of the VIVAGUI outside of the Swing thread(s).
     * 
     * @author vanb
     */
    public class Actions implements Runnable
    {
        /** A queue of actions to perform. */
        public BlockingQueue<Action> todo = new ArrayBlockingQueue<Action>(10);
        
        @Override
        /**
         * Perform actions!
         */
        public void run()
        {            
            try
            {
                for(;;) todo.take().run();
            }
            catch( InterruptedException e )
            {
            }           
        }
    }
    
    /**
     * One action.
     * 
     * @author vanb
     */
    public class Action implements Runnable
    {
        /** Which button was pressed? */
        private JButton source;
        
        /**
         * Create an Action.
         * 
         * @param source Which button was pressed?
         */
        public Action( JButton source )
        {
            this.source = source;
        }
        
        @Override
        /** 
         * Perform the action!
         */
        public void run()
        {
            frmViva.setEnabled( false );
            
            if( source == btnLoadPattern )
            {
                // Load a Pattern.

                // Invoke the Chooser
                patternFileChooser.setApproveButtonText( "Load" );
                patternFileChooser.showOpenDialog( frmViva );
                File patternFile = null;
                try
                {
                    // Get the chosen file (if there was one)
                    patternFile = patternFileChooser.getSelectedFile();
                    if( patternFile != null )
                    {
                        // Read it into the Pattern editor
                        FileReader fr = new FileReader( patternFile );
                        patternEditor.read( fr, "VIVA Pattern" );
                        patternEditor.getDocument().addDocumentListener( listener );
                        patternFileField.setText( patternFile.getAbsolutePath() );
                        setLineNumbers();
                        write( "Successfully loaded Pattern file " + patternFile.getAbsolutePath() );

                        // Parse it
                        parse();
                    }
                }
                catch( FileNotFoundException fnfe )
                {
                    write( "Unable to find Pattern file: " + patternFile.getAbsolutePath() );
                }
                catch( IOException ioe )
                {
                    write( "Unable to read Pattern file: " + patternFile.getAbsolutePath() );
                }
            }
            else if( source == btnSavePattern || source == btnSaveAsPattern )
            {
                // Save a pattern, or SaveAs a Pattern.

                // The only real difference is that SaveAs invokes the Chooser
                if( source == btnSaveAsPattern )
                {
                    patternFileChooser.setApproveButtonText( "Save" );
                    patternFileChooser.showSaveDialog( frmViva );
                }

                // Get the file (if any). If Save (instead of SaveAs),
                // just use whatever file is still in the Chooser.
                File patternFile = patternFileChooser.getSelectedFile();
                try
                {
                    if( patternFile != null )
                    {
                        FileOutputStream fos = new FileOutputStream( patternFile );
                        fos.write( patternEditor.getText().getBytes() );
                        write( "Successfully saved Pattern file " + patternFile.getAbsolutePath() );
                        fos.close();

                        // Record the filename
                        patternFileField.setText( patternFile.getAbsolutePath() );

                        // Disable Save and SaveAs until the user makes more changes
                        btnSavePattern.setEnabled( false );
                        btnSaveAsPattern.setEnabled( false );
                    }
                }
                catch( IOException ioe )
                {
                    write( "Unable to write Pattern file: " + patternFile.getAbsolutePath() );
                }

            }
            else if( source == btnParsePattern )
            {
                // Parse a Pattern. First, reset parameters.
                parse();
            }
            else if( source == btnLoadInput )
            {
                // Identify Input files for testing

                // Invoke the Chooser
                inputFileChooser.setApproveButtonText( "Identify" );
                inputFileChooser.showOpenDialog( frmViva );

                // Get the selected file(s) (if any)
                File inputFiles[] = inputFileChooser.getSelectedFiles();
                if( inputFiles != null && inputFiles.length > 0 )
                {
                    // We don't do anything with them here.
                    // We don't "read them in" or anything like that.
                    // We'll test them as they are, on disk.
                    // All we do is build a String with all of their names,
                    // and display it in the Input Files text field.
                    String fileNames = "";
                    boolean first = true;
                    for( File file : inputFiles )
                    {
                        if( first ) first = false;
                        else fileNames += ";";
                        fileNames += file.getAbsolutePath();
                    }
                    inputFilesField.setText( fileNames );
                    write( "Successfully identified Input file(s) " + fileNames );
                }
            }
            else if( source == btnTestInput )
            {
                // Test Input files
                
                // Get the files (if any) directly from the Chooser.
                File inputFiles[] = inputFileChooser.getSelectedFiles();
                if( inputFiles != null && inputFiles.length > 0 )
                {
                    for( File file : inputFiles )
                    {
                        // VIVA!
                        viva.testInputFile( file.getAbsolutePath() );

                        // Some nice spacing
                        ps.println();
                    }
                }
            }
            else if( source == btnClearOutput )
            {
                // Clear the Outputs window

                outputsText.setText( "" );
            }
            
            frmViva.setEnabled( true );
        }
    }

    /**
     * Performs all of the actions for all of the buttons.
     * 
     * @param ae
     *            The Action Event
     */
    public void actionPerformed( ActionEvent ae )
    {
        // Figure out which button was pressed
        JButton source = (JButton) ae.getSource();
        
        try
        {
            // Put this Action on the To Do list.
            actions.todo.put( new Action( source ) );
        }
        catch( InterruptedException e )
        {
        }
    }
}
