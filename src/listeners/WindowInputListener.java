package listeners;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import main.Configuration;
import main.MainWindow;

// The input listener for the window so the program can detect the window closing.
public class WindowInputListener extends WindowAdapter
{
    // Caller window
    private final MainWindow window;

    // Constructor to get the caller window
    public WindowInputListener(MainWindow window)
    {
        this.window = window;
    }

    // Action done when the window is closed.
    // Check for unsaved changes.
    @Override
    public void windowClosing(WindowEvent e) {
        boolean canClose = true;

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
                canClose = new ButtonInputListener(window).saveDocument();
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canClose = false;
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
                // Won't de-cancel if canceled before
                canClose = new ButtonInputListener(window).saveText(window.textAreaL) && canClose;
            }
            // If cancel
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                // Cancel operation
                canClose = false;
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
                canClose = new ButtonInputListener(window).saveText(window.textAreaR) && canClose;
            }
            else if(save == JOptionPane.CANCEL_OPTION)
            {
                canClose = false;
            }
        }

        // Determine if can close or not
        if(canClose) window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
