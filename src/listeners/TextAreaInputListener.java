package listeners;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import main.MainWindow;

// The input listener for the text areas
public class TextAreaInputListener implements DocumentListener
{
    // Window that called the listener
    private final MainWindow window;

    // Text area that called the listener
    private final JTextArea textArea;

    // Constructor to get the window
    public TextAreaInputListener(MainWindow window, JTextArea textArea)
    {
        this.window = window;
        this.textArea = textArea;
    }

    // Update unsaved changes
    private void notifyUnsavedChanges()
    {
        // Notify unsaved changes when a text area is changed
        if(textArea.equals(window.textAreaL) || textArea.equals(window.textAreaR))
        {
            window.notifyUnsavedChanges(textArea);
        }
    }

    // Event triggers that will call the same function
    @Override public void insertUpdate(DocumentEvent e) { notifyUnsavedChanges(); }
    @Override public void removeUpdate(DocumentEvent e) { notifyUnsavedChanges(); }
    @Override public void changedUpdate(DocumentEvent e) { notifyUnsavedChanges(); }
}
