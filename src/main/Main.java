package main;

import java.awt.*;

// Main class used to initialize the program
public class Main
{
    // Main method
    public static void main(String[] args)
    {
        EventQueue.invokeLater(
            () -> {
                try
                {
                    // Initialize the main window
                    MainWindow window = new MainWindow();
                    window.setVisible(true);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        );
    }
}
