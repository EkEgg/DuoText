package main;

import listeners.*;
import java.awt.*;
import javax.swing.*;
import java.util.HashSet;
import java.io.File;

// The main window class
public class MainWindow extends JFrame
{
    // Main toolbar buttons
    public JButton
            buttonNewDocument,              // Button used to create a new document
            buttonOpenDocument,             // Button used to open a document
            buttonSaveDocument,             // Button used to save the document as a DuoText file
            buttonSaveDocumentAs,           // Button used to save a document as another DuoText file
            buttonClearTexts,               // Button used to clear the two text areas
            buttonSwapContents,             // Button used to swap the content of the two text areas
            buttonSwapTexts,                // Button used to swap the two text areas
            buttonHelp,                     // Button used to display help about the program's usage
            buttonAbout;                    // Button used to display information about the program

    // Individual text area toolbar buttons
    public JButton
            buttonNewL, buttonNewR,         // Buttons used to create a new text in the text area
            buttonOpenL, buttonOpenR,       // Buttons used to open a text file in the text area
            buttonSaveL, buttonSaveR,       // Buttons used to save the text area's content in a text file
            buttonSaveAsL, buttonSaveAsR,   // Buttons used to save the text area's content in another text file
            buttonClearL, buttonClearR;     // Buttons used to clear the text area's content

    // The two text areas
    public JTextArea textAreaL, textAreaR;

    // Controls whether there are unsaved changes in each text area
    public boolean unsavedChangesDocument, unsavedChangesL, unsavedChangesR;

    // Files
    public File documentFile,   // DuoText file currently being edited
                fileL, fileR;   // Text file currently being edited in each text area

    // Constructor
    public MainWindow()
    {
        // Sets unsaved changes to false
        unsavedChangesL = unsavedChangesR = false;

        // Sets file paths to null
        documentFile = fileL = fileR = null;

        // Auxiliary holders
        JToolBar toolbar;
        JPanel panel, mainPanel;
        JScrollPane scrollPane;
        JSplitPane splitPane;

        // Auxiliary set with all buttons
        HashSet<JButton> buttonSet = new HashSet<>(19);

        // Configures the window
        addWindowListener(new WindowInputListener(this));
        updateTitle();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0,
                Configuration.MainWindow.Dimensions.INITIAL_WIDTH, Configuration.MainWindow.Dimensions.INITIAL_HEIGHT);
        setLocationRelativeTo(null);    // Centers the window in the screen

        // Tries the look-and-feel to the system's native
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // Creates the main content pane
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // Creates the main toolbar and its buttons
        toolbar = new JToolBar();
        toolbar.setFloatable(false);    // Makes the toolbar unmovable
        toolbar.add(buttonNewDocument = new JButton(Configuration.Button.Label.NEW_DOC)); toolbar.addSeparator();
        buttonSet.add(buttonNewDocument);
        toolbar.add(buttonOpenDocument = new JButton(Configuration.Button.Label.OPEN_DOC)); toolbar.addSeparator();
        buttonSet.add(buttonOpenDocument);
        toolbar.add(buttonSaveDocument = new JButton(Configuration.Button.Label.SAVE_DOC)); toolbar.addSeparator();
        buttonSet.add(buttonSaveDocument);
        toolbar.add(buttonSaveDocumentAs = new JButton(Configuration.Button.Label.SAVE_DOC_AS)); toolbar.addSeparator();
        buttonSet.add(buttonSaveDocumentAs);
        toolbar.add(buttonClearTexts = new JButton(Configuration.Button.Label.CLEAR_ALL)); toolbar.addSeparator();
        buttonSet.add(buttonClearTexts);
        toolbar.add(buttonSwapContents = new JButton(Configuration.Button.Label.SWAP_CONTENTS)); toolbar.addSeparator();
        buttonSet.add(buttonSwapContents);
        toolbar.add(buttonSwapTexts = new JButton(Configuration.Button.Label.SWAP_TEXTS)); toolbar.addSeparator();
        buttonSet.add(buttonSwapTexts);
        toolbar.add(buttonHelp = new JButton(Configuration.Button.Label.HELP)); toolbar.addSeparator();
        buttonSet.add(buttonHelp);
        toolbar.add(buttonAbout = new JButton(Configuration.Button.Label.INFO)); toolbar.addSeparator();
        buttonSet.add(buttonAbout);
        mainPanel.add(toolbar, BorderLayout.PAGE_START);

        // Creates the split pane
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);          // Turn collapse buttons on
        splitPane.setDividerLocation(getWidth() / 2);   // Center the divider

        // Creates the left text area panel
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        textAreaL = new JTextArea();
        textAreaL.setLineWrap(true);
        textAreaL.setWrapStyleWord(true);
        scrollPane.setViewportView(textAreaL);
        panel.add(scrollPane, BorderLayout.CENTER);
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(false);
        toolbar.add(buttonNewL = new JButton(Configuration.Button.Label.NEW_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonNewL);
        toolbar.add(buttonOpenL = new JButton(Configuration.Button.Label.OPEN_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonOpenL);
        toolbar.add(buttonSaveL = new JButton(Configuration.Button.Label.SAVE_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonSaveL);
        toolbar.add(buttonSaveAsL = new JButton(Configuration.Button.Label.SAVE_TXT_AS)); toolbar.addSeparator();
        buttonSet.add(buttonSaveAsL);
        toolbar.add(buttonClearL = new JButton(Configuration.Button.Label.CLEAR_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonClearL);
        panel.add(toolbar, BorderLayout.PAGE_END);
        panel.add(scrollPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(panel);

        // Creates the right text area panel
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane();
        textAreaR = new JTextArea();
        textAreaR.setLineWrap(true);
        textAreaR.setWrapStyleWord(true);
        scrollPane.setViewportView(textAreaR);
        panel.add(scrollPane, BorderLayout.CENTER);
        toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setRollover(false);
        toolbar.add(buttonNewR = new JButton(Configuration.Button.Label.NEW_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonNewR);
        toolbar.add(buttonOpenR = new JButton(Configuration.Button.Label.OPEN_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonOpenR);
        toolbar.add(buttonSaveR = new JButton(Configuration.Button.Label.SAVE_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonSaveR);
        toolbar.add(buttonSaveAsR = new JButton(Configuration.Button.Label.SAVE_TXT_AS)); toolbar.addSeparator();
        buttonSet.add(buttonSaveAsR);
        toolbar.add(buttonClearR = new JButton(Configuration.Button.Label.CLEAR_TXT)); toolbar.addSeparator();
        buttonSet.add(buttonClearR);
        panel.add(toolbar, BorderLayout.PAGE_END);
        panel.add(scrollPane, BorderLayout.CENTER);
        splitPane.setRightComponent(panel);

        // Adds the split pane to the window
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Adds the buttons' action listener
        for(JButton button : buttonSet)
        {
            button.addActionListener(new ButtonInputListener(this));
        }

        // Buttons' tooltips
        buttonNewDocument.setToolTipText(Configuration.Button.Tooltip.NEW_DOC);
        buttonOpenDocument.setToolTipText(Configuration.Button.Tooltip.OPEN_DOC);
        buttonSaveDocument.setToolTipText(Configuration.Button.Tooltip.SAVE_DOC);
        buttonSaveDocumentAs.setToolTipText(Configuration.Button.Tooltip.SAVE_DOC_AS);
        buttonClearTexts.setToolTipText(Configuration.Button.Tooltip.CLEAR_ALL);
        buttonSwapContents.setToolTipText(Configuration.Button.Tooltip.SWAP_CONTENTS);
        buttonSwapTexts.setToolTipText(Configuration.Button.Tooltip.SWAP_TEXTS);
        buttonHelp.setToolTipText(Configuration.Button.Tooltip.HELP);
        buttonAbout.setToolTipText(Configuration.Button.Tooltip.INFO);
        buttonNewL.setToolTipText(Configuration.Button.Tooltip.NEW_TXT);
        buttonNewR.setToolTipText(Configuration.Button.Tooltip.NEW_TXT);
        buttonOpenL.setToolTipText(Configuration.Button.Tooltip.OPEN_TXT);
        buttonOpenR.setToolTipText(Configuration.Button.Tooltip.OPEN_TXT);
        buttonSaveL.setToolTipText(Configuration.Button.Tooltip.SAVE_TXT);
        buttonSaveR.setToolTipText(Configuration.Button.Tooltip.SAVE_TXT);
        buttonSaveAsL.setToolTipText(Configuration.Button.Tooltip.SAVE_TXT_AS);
        buttonSaveAsR.setToolTipText(Configuration.Button.Tooltip.SAVE_TXT_AS);
        buttonClearL.setToolTipText(Configuration.Button.Tooltip.CLEAR_TXT);
        buttonClearR.setToolTipText(Configuration.Button.Tooltip.CLEAR_TXT);

        // Clears the button's set
        buttonSet.clear();

        // Sets the text areas' font to the same as the buttons
        textAreaL.setFont(buttonNewDocument.getFont());
        textAreaR.setFont(buttonNewDocument.getFont());

        // Adds the text areas' document listeners
        textAreaL.getDocument().addDocumentListener(new TextAreaInputListener(this, textAreaL));
        textAreaR.getDocument().addDocumentListener(new TextAreaInputListener(this, textAreaR));
    }

    // Notify unsaved changes
    // Null the parameter for the whole document
    public void notifyUnsavedChanges(JTextArea textArea)
    {
        if(textArea.equals(textAreaL)) unsavedChangesL = true;
        else if(textArea.equals(textAreaR)) unsavedChangesR = true;
        unsavedChangesDocument = true;
        updateTitle();
    }

    // Notify unsaved changes were saved
    // Null the parameter for the whole document
    public void notifySavedChanges(JTextArea textArea)
    {
        if(textArea != null)
        {
            if(textArea.equals(textAreaL)) unsavedChangesL = false;
            else if(textArea.equals(textAreaR)) unsavedChangesR = false;
        }
        unsavedChangesDocument = false;
        updateTitle();
    }

    // Update title
    public void updateTitle()
    {
        StringBuilder newTitle = new StringBuilder(Configuration.MainWindow.Title.MAIN);
        newTitle.append(Configuration.MainWindow.Title.MAIN_DOCUMENT_SEPARATOR);

        // Name of the DuoText file or Untitled
        if(documentFile == null) newTitle.append(Configuration.MainWindow.Title.UNTITLED_FILE_NAME);
        else newTitle.append(documentFile.getName());

        // * if unsaved changes
        if(unsavedChangesDocument) newTitle.append(Configuration.MainWindow.Title.UNSAVED_CHANGES_INDICATOR);

        newTitle.append(Configuration.MainWindow.Title.LEFT_FILE_SEPARATOR);

        // Name of the left text area file or Untitled
        if(fileL == null) newTitle.append(Configuration.MainWindow.Title.UNTITLED_FILE_NAME);
        else newTitle.append(fileL.getName());

        // * if unsaved changes
        if(unsavedChangesDocument) newTitle.append(Configuration.MainWindow.Title.UNSAVED_CHANGES_INDICATOR);

        newTitle.append(Configuration.MainWindow.Title.MIDDLE_FILE_SEPARATOR);

        // Name of the right text area file or Untitled
        if(fileR == null) newTitle.append(Configuration.MainWindow.Title.UNTITLED_FILE_NAME);
        else newTitle.append(fileR.getName());

        // * if unsaved changes
        if(unsavedChangesDocument) newTitle.append(Configuration.MainWindow.Title.UNSAVED_CHANGES_INDICATOR);

        newTitle.append(Configuration.MainWindow.Title.RIGHT_FILE_SEPARATOR);

        setTitle(newTitle.toString());
    }
}
