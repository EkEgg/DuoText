package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

import main.Configuration;
import main.MainWindow;

// The input listener for the buttons
public class ButtonInputListener implements ActionListener
{
    // Window that called the listener
    private final MainWindow window;

    // Constructor to get the window
    public ButtonInputListener(MainWindow window)
    {
        this.window = window;
    }

    // Action performed when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Chooses what action will be taken depending on which button called the event
        if(e.getSource().equals(window.buttonNewDocument)) newDocument();
        else if(e.getSource().equals(window.buttonNewL)) newText(window.textAreaL);
        else if(e.getSource().equals(window.buttonNewR)) newText(window.textAreaR);
        else if(e.getSource().equals(window.buttonOpenDocument)) openDocument();
        else if(e.getSource().equals(window.buttonOpenL)) openText(window.textAreaL);
        else if(e.getSource().equals(window.buttonOpenR)) openText(window.textAreaR);
        else if(e.getSource().equals(window.buttonSaveDocument)) saveDocument();
        else if(e.getSource().equals(window.buttonSaveL)) saveText(window.textAreaL);
        else if(e.getSource().equals(window.buttonSaveR)) saveText(window.textAreaR);
        else if(e.getSource().equals(window.buttonSaveDocumentAs)) saveDocumentAs();
        else if(e.getSource().equals(window.buttonSaveAsL)) saveTextAs(window.textAreaL);
        else if(e.getSource().equals(window.buttonSaveAsR)) saveTextAs(window.textAreaR);
        else if(e.getSource().equals(window.buttonClearTexts)) clearAllTexts();
        else if(e.getSource().equals(window.buttonClearL)) clearText(window.textAreaL);
        else if(e.getSource().equals(window.buttonClearR)) clearText(window.textAreaR);
        else if(e.getSource().equals(window.buttonSwapContents)) swapContents();
        else if(e.getSource().equals(window.buttonSwapTexts)) swapTexts();
        else if(e.getSource().equals(window.buttonHelp)) showHelp();
        else if(e.getSource().equals(window.buttonAbout)) showInfo();
    }

    // Creates a new DuoText document
    private void newDocument()
    {
        // Will determine if the program will really renew the document
        boolean canRenew = true;

        // If there are unsaved changes in the whole document
        if(window.unsavedChangesDocument)
        {
            // Ask if it should be saved
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_DOC,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save
                canRenew = saveDocument();
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canRenew = false;
            }
            // If no, just skip
        }

        // If there are unsaved changes in the left text area
        if(window.unsavedChangesL && window.fileL != null)
        {
            // Ask if it should be saved
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT_L,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save
                canRenew = saveText(window.textAreaL) && canRenew;  // Won't de-cancel if canceled before
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canRenew = false;
            }
            // If no, just skip
        }

        // Same for right text area (check above)
        if(window.unsavedChangesR && window.fileR != null)
        {
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT_R,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(save == JOptionPane.YES_OPTION)
            {
                canRenew = saveText(window.textAreaR) && canRenew;  // Won't de-cancel if canceled before
            }
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                canRenew = false;
            }
        }

        // If will really renew
        if(canRenew)
        {
            // Assign no files to the document and each text area
            window.documentFile = window.fileL = window.fileR = null;

            // Clear each text area
            window.textAreaL.setText("");
            window.textAreaR.setText("");

            // Notify saved changes
            window.notifySavedChanges(null);
            window.notifySavedChanges(window.textAreaL);
            window.notifySavedChanges(window.textAreaR);
        }
    }

    // Creates a new text file in a text area
    private void newText(JTextArea textArea)
    {
        // Will determine if the program will really renew the text
        boolean canRenew = true;

        // If there are unsaved changes in the respective text area
        if((textArea.equals(window.textAreaL) && window.unsavedChangesL) ||
                (textArea.equals(window.textAreaR) && window.unsavedChangesR))
        {
            // Ask if should save
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save
                canRenew = saveText(textArea);
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canRenew = false;
            }
            // If no, just skip
        }

        // If will really renew
        if(canRenew)
        {
            // Assign no file to the respective text area
            if(textArea.equals(window.textAreaL)) window.fileL = null;
            else if(textArea.equals(window.textAreaR)) window.fileR = null;

            // Clear the text
            textArea.setText("");

            // Notify saved changes
            window.notifySavedChanges(textArea);
        }
    }

    // Opens a DuoText document
    private void openDocument()
    {
        // Will determine if the program will really open a file
        boolean canOpen = true;

        // If there are unsaved changes in the document
        if(window.unsavedChangesDocument)
        {
            // Ask if should save
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_DOC,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save
                canOpen = saveDocument();
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canOpen = false;
            }
            // If no, just skip
        }

        // If there are unsaved changes in the left text area and there are no files linked to it
        if(window.unsavedChangesL && window.fileL != null)
        {
            // Ask if should save
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT_L,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save
                canOpen = saveText(window.textAreaL) && canOpen;    // Won't de-cancel if canceled before
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canOpen = false;
            }
            // If no, just skip
        }

        // Same for the right text area (check above)
        if(window.unsavedChangesR && window.fileR != null)
        {
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT_R,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(save == JOptionPane.YES_OPTION)
            {
                canOpen = saveText(window.textAreaR) && canOpen;    // Won't de-cancel if canceled before
            }
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                canOpen = false;
            }
        }

        // If will really open
        if(canOpen)
        {
            // Choose a file
            JFileChooser fChooser = new JFileChooser();

            // Only DuoText documents will be seen by default
            fChooser.setFileFilter(new FileNameExtensionFilter(Configuration.File.DUOTEXT_DOC_FILTER,
                    Configuration.File.DUOTEXT_EXTENSION_NAME));

            int option = fChooser.showOpenDialog(window);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fChooser.getSelectedFile();
                try
                {
                    // Reads the file into the text areas
                    FileInputStream fStream = new FileInputStream(selectedFile);
                    InputStreamReader fReader = new InputStreamReader(fStream, Configuration.File.DEFAULT_ENCODING);
                    StringBuilder newTextL = new StringBuilder(),
                                  newTextR = new StringBuilder();
                    int i;
                    // Read until separation character
                    while((char) (i = fReader.read()) != Configuration.File.DUOTEXT_FILE_SEPARATOR)
                    {
                        newTextL.append((char) i);  // into the left
                    }
                    // Read until the end
                    while((i = fReader.read()) != -1)
                    {
                        newTextR.append((char) i);  // into the right
                    }
                    fReader.close();
                    fStream.close();
                    window.textAreaL.setText(newTextL.toString());
                    window.textAreaR.setText(newTextR.toString());

                    // Assign the opened file to the document
                    window.documentFile = selectedFile;
                    window.notifySavedChanges(null);
                }
                catch(Exception e)
                {
                    // File access exception
                    JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                            Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(option == JFileChooser.ERROR_OPTION)
            {
                // File choosing exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_SELECT,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Opens a text file in a text area
    private void openText(JTextArea textArea)
    {
        // Will determine if the program will really open a file
        boolean canOpen = true;

        // If there are in the unsaved changes in the respective text area
        if((textArea.equals(window.textAreaL) && window.unsavedChangesL) ||
                (textArea.equals(window.textAreaR) && window.unsavedChangesR))
        {
            // Ask if it should be saved
            int save = JOptionPane.showConfirmDialog(window, Configuration.Dialog.Text.UNSAVED_CHANGES_TXT,
                    Configuration.Dialog.Title.UNSAVED_CHANGES, JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // If yes
            if(save == JOptionPane.YES_OPTION)
            {
                // Try to save it
                canOpen = saveText(textArea);
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canOpen = false;
            }
        }

        // If can really open
        if(canOpen)
        {
            // Chooses the file
            JFileChooser fChooser = new JFileChooser();
            int option = fChooser.showOpenDialog(window);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fChooser.getSelectedFile();
                try
                {
                    // Reads the file into the text area
                    FileInputStream fStream = new FileInputStream(selectedFile);
                    InputStreamReader fReader = new InputStreamReader(fStream, Configuration.File.DEFAULT_ENCODING);
                    StringBuilder newText = new StringBuilder();
                    int i;
                    while((i = fReader.read()) != -1)
                    {
                        newText.append((char) i);
                    }
                    fReader.close();
                    fStream.close();
                    textArea.setText(newText.toString());

                    // Assigns the opened file to the respective text area
                    if(textArea.equals(window.textAreaL)) window.fileL = selectedFile;
                    else if(textArea.equals(window.textAreaR)) window.fileR = selectedFile;
                    window.notifySavedChanges(textArea);
                }
                catch(Exception e)
                {
                    // File access exception
                    JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                            Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(option == JFileChooser.ERROR_OPTION)
            {
                // File choosing exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_SELECT,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Saves the DuoText document
    // Returns true if successfully saved
    public boolean saveDocument()
    {
        // If no file is assigned, save as a chosen file
        if(window.documentFile == null)
        {
            return saveDocumentAs();
        }
        else
        {
            // If a file is assigned, save in it
            try
            {
                // Writes the file
                FileOutputStream fStream = new FileOutputStream(window.documentFile);
                OutputStreamWriter fWriter = new OutputStreamWriter(fStream, Configuration.File.DEFAULT_ENCODING);
                fWriter.write(window.textAreaL.getText());
                fWriter.write(Configuration.File.DUOTEXT_FILE_SEPARATOR);   // Inserts the separator byte
                fWriter.write(window.textAreaR.getText());
                fWriter.close();
                fStream.close();

                // Notifies saved changes
                window.notifySavedChanges(null);

                return true;
            }
            catch(Exception e) {
                // File access exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    // Saves a text file from a text area
    // Returns true if successfully saved
    public boolean saveText(JTextArea textArea)
    {
        // Takes the file assigned to the text area
        File respectiveFile = null;
        if(textArea.equals(window.textAreaL)) respectiveFile = window.fileL;
        else if(textArea.equals(window.textAreaR)) respectiveFile = window.fileR;

        // If no file is assigned, save as a chosen file
        if(respectiveFile == null)
        {
            return saveTextAs(textArea);
        }
        else
        {
            // If a file is assigned, save in it
            try
            {
                // Writes the file
                FileOutputStream fStream = new FileOutputStream(respectiveFile);
                OutputStreamWriter fWriter = new OutputStreamWriter(fStream, Configuration.File.DEFAULT_ENCODING);
                fWriter.write(textArea.getText());
                fWriter.close();
                fStream.close();

                // Assigns the file to the respective text area
                if (textArea.equals(window.textAreaL)) window.fileL = respectiveFile;
                else if (textArea.equals(window.textAreaR)) window.fileR = respectiveFile;
                window.notifySavedChanges(textArea);

                return true;
            }
            catch(Exception e) {
                // File access exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    // Saves the DuoText document as a chosen file
    // Returns true if successfully saved.
    private boolean saveDocumentAs()
    {
        // Chooses file
        JFileChooser fChooser = new JFileChooser();

        // Must view preferably only DuoText documents
        fChooser.setFileFilter(new FileNameExtensionFilter(Configuration.File.DUOTEXT_DOC_FILTER,
                Configuration.File.DUOTEXT_EXTENSION_NAME));

        int option = fChooser.showSaveDialog(window);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            File respectiveFile = fChooser.getSelectedFile();

            // Makes sure the file has the dtxt extension
            if(!respectiveFile.getName().endsWith(Configuration.File.DUOTEXT_EXTENSION))
            {
                respectiveFile = new File(respectiveFile.toString() + Configuration.File.DUOTEXT_EXTENSION);
            }

            try
            {
                // Writes the file to be saved
                FileOutputStream fStream = new FileOutputStream(respectiveFile);
                OutputStreamWriter fWriter = new OutputStreamWriter(fStream, Configuration.File.DEFAULT_ENCODING);
                fWriter.write(window.textAreaL.getText());
                fWriter.write(Configuration.File.DUOTEXT_FILE_SEPARATOR);   // Inserts the separator byte
                fWriter.write(window.textAreaR.getText());
                fWriter.close();
                fStream.close();

                // Assigns the created file to the document
                window.documentFile = respectiveFile;
                window.notifySavedChanges(null);

                return true;
            }
            catch(Exception e)
            {
                // File access exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else if(option == JFileChooser.ERROR_OPTION)
        {
            // File choosing exception
            JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_SELECT,
                    Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else return true;   // Cancelled
    }

    // Saves a text file from a text area as chosen file
    // Returns true if successfully saved
    private boolean saveTextAs(JTextArea textArea)
    {
        // Chooses file
        JFileChooser fChooser = new JFileChooser();
        int option = fChooser.showSaveDialog(window);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            File respectiveFile = fChooser.getSelectedFile();

            // If the file doesn't have an extension, defaults to .txt
            if(!respectiveFile.getName().contains("."))
            {
                respectiveFile = new File(respectiveFile.toString() +
                        Configuration.File.DEFAULT_TEXT_EXTENSION);
            }

            try
            {
                // Writes the file to be saved
                FileOutputStream fStream = new FileOutputStream(respectiveFile);
                OutputStreamWriter fWriter = new OutputStreamWriter(fStream, Configuration.File.DEFAULT_ENCODING);
                fWriter.write(textArea.getText());
                fWriter.close();
                fStream.close();

                // Assigns the respective text area to the created file
                if(textArea.equals(window.textAreaL)) window.fileL = respectiveFile;
                else if(textArea.equals(window.textAreaR)) window.fileR = respectiveFile;

                // Notifies saved changes
                window.notifySavedChanges(textArea);

                return true;
            }
            catch(Exception e)
            {
                // File access exception
                JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_ACCESS,
                        Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        else if(option == JFileChooser.ERROR_OPTION)
        {
            // File choosing exception
            JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.ERROR_SELECT,
                    Configuration.Dialog.Title.ERROR, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else return true;   // Cancelled
    }

    // Clears all text areas' content
    private void clearAllTexts()
    {
        window.textAreaL.setText("");
        window.textAreaR.setText("");
    }

    // Clears the content of a text area
    private void clearText(JTextArea textArea)
    {
        textArea.setText("");
    }

    // Swaps the content of the two text areas
    private void swapContents()
    {
        String aux = window.textAreaL.getText();
        window.textAreaL.setText(window.textAreaR.getText());
        window.textAreaR.setText(aux);
    }

    // Swaps the two text areas
    private void swapTexts()
    {
        // Save unsaved changes controls
        boolean auxUnsL = window.unsavedChangesL,
                auxUnsR = window.unsavedChangesR;

        // Swap contents
        swapContents();

        // Swap files
        File auxFile = window.fileL;
        window.fileL = window.fileR;
        window.fileR = auxFile;

        // Load unsaved changes controls and updates title
        window.unsavedChangesL = auxUnsL;
        window.unsavedChangesR = auxUnsR;
        window.updateTitle();
    }

    // Shows the help window
    private void showHelp()
    {
        JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.HELP, Configuration.Dialog.Title.HELP,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Shows the information window
    private void showInfo()
    {
        JOptionPane.showMessageDialog(window, Configuration.Dialog.Text.INFO, Configuration.Dialog.Title.INFO,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
