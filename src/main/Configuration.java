package main;

// The program configuration class
// Contains every text that may vary over time
public class Configuration
{
    // Information about the project
    private static class Project
    {
        public static String
                AUTHOR = "Erick Gaiote",
                DEVELOPMENT_YEAR = "2019",
                GITHUB = "https://github.com/EkEgg/DuoText";
    }

    // Configuration related to the main window
    public static class MainWindow
    {
        // Configuration related to the title
        public static class Title
        {
            // Format:
            // MAIN MAIN_DOCUMENT_SEPARATOR
            // <<file name>> (might be UNSAVED_FILE_NAME) [UNSAVED_CHANGES_INDICATOR]
            // LEFT_FILE_SEPARATOR
            // <<file name>> (might be UNSAVED_FILE_NAME) [UNSAVED_CHANGES_INDICATOR]
            // MIDDLE_FILE_SEPARATOR
            // <<file name>> (might be UNSAVED_FILE_NAME) [UNSAVED_CHANGES_INDICATOR]
            // RIGHT_FILE_SEPARATOR
            // Example: DuoText - doc.dtxt (Untitled*, file.txt)
            public static final String
                    MAIN = "DuoText",
                    MAIN_DOCUMENT_SEPARATOR = " - ",
                    LEFT_FILE_SEPARATOR = " (",
                    MIDDLE_FILE_SEPARATOR = ", ",
                    RIGHT_FILE_SEPARATOR = ")",
                    UNSAVED_CHANGES_INDICATOR = "*",
                    UNTITLED_FILE_NAME = "Untitled";
        }
        
        // Configuration related to the window's dimension
        public static class Dimensions
        {
            // Dimensions the window will start at
            public static final int
                    INITIAL_WIDTH = 1000,
                    INITIAL_HEIGHT = 600;
        }
        
    }
    
    // Configuration related to the buttons
    public static class Button
    {
        // Text displayed in the buttons
        public static class Label
        {
            public static final String
                    NEW_DOC = "New document",
                    OPEN_DOC = "Open document",
                    SAVE_DOC = "Save document",
                    SAVE_DOC_AS = "Save document as...",
                    CLEAR_ALL = "Clear texts",
                    SWAP_CONTENTS = "Swap contents",
                    SWAP_TEXTS = "Swap texts",
                    HELP = "Help",
                    INFO = "About",
                    NEW_TXT = "New",
                    OPEN_TXT = "Open",
                    SAVE_TXT = "Save",
                    SAVE_TXT_AS = "Save as...",
                    CLEAR_TXT = "Clear";
        }

        // Text displayed in the buttons' tooltips
        public static class Tooltip
        {
            public static final String
                    NEW_DOC = "Creates a blank new DuoText document.",
                    OPEN_DOC = "Opens a DuoText document.",
                    SAVE_DOC = "Saves the DuoText document.",
                    SAVE_DOC_AS = "Saves the DuoText document as a different file.",
                    CLEAR_ALL = "Clears the two text areas.",
                    SWAP_CONTENTS = "Swaps the contents of the text areas.",
                    SWAP_TEXTS = "Swaps the text areas from side to side.",
                    HELP = "Shows help about the program's functions.",
                    INFO = "Shows information about the program.",
                    NEW_TXT = "Creates a blank new text for this text area.",
                    OPEN_TXT = "Opens a text file in this text area.",
                    SAVE_TXT = "Saves the text file assigned to this text area.",
                    SAVE_TXT_AS = "Saves the text file assigned to this text area as another file.",
                    CLEAR_TXT = "Clears the content of this text area.";
        }
    }

    // Configuration related to the dialogs
    public static class Dialog
    {
        // Dialogs' title
        public static class Title
        {
            public static final String
                    UNSAVED_CHANGES = "Unsaved changes",
                    ERROR = "An error occurred",
                    HELP = "Help",
                    INFO = "About";
        }

        // Dialogs' text
        public static class Text
        {
            public static final String
                    UNSAVED_CHANGES_DOC = "You have unsaved changes in this DuoText document.\n" +
                                          "Do you want to save it?",
                    UNSAVED_CHANGES_TXT = "You have unsaved changes in this text file.\n" +
                                          "Do you want to save it?",
                    UNSAVED_CHANGES_TXT_L = "You have unsaved changes in the left text file.\n" +
                                            "Do you want to save it?",
                    UNSAVED_CHANGES_TXT_R = "You have unsaved changes in the right text file.\n" +
                                            "Do you want to save it?",
                    ERROR_SELECT = "An error occurred while selecting the file.\n" +
                                   "The operation was canceled.",
                    ERROR_ACCESS = "An error occurred while accessing the file.\n" +
                                   "The operation was canceled.",

                    // Text shown in the help menu
                    HELP = "DuoText is a simple text editor with two text boxes side by side.\n" +
                           "Files with the two text areas are called DuoText documents. The top toolbar handles the " +
                           "operations related to the whole DuoText document and other functions involving both or " +
                           "none of the text areas.\n" +
                           "Also, in each text area, you'll have a bottom toolbar to operate that specific text area " +
                           "just like a normal text editor.\n" +
                           "You can also move de divider that splits the two text areas left or right or even " +
                           "collapse a text area.",

                    // Text shown in the information menu
                    INFO = "DuoText was developed by " + Project.AUTHOR + " in " + Project.DEVELOPMENT_YEAR + " " +
                           "using AWT and Swing to practice Java programming and software development in those " +
                           "platforms.\n" +
                           "The project is open-source and its repository can be found in <" + Project.GITHUB + ">.";
        }
    }

    // Configuration related to file management
    public static class File
    {
        public static String
                DUOTEXT_EXTENSION_NAME = "dtxt",                        // DuoText documents' extension name
                DUOTEXT_EXTENSION = "." + DUOTEXT_EXTENSION_NAME,       // DuoText documents' extension with prefix
                DUOTEXT_DOC_FILTER = "DuoText document",                // DuoText documents filter name
                DEFAULT_TEXT_EXTENSION = ".txt",                        // Default text extension when saving plain text
                DEFAULT_ENCODING = "UTF-8";                             // Default encoding used for text

        public static char DUOTEXT_FILE_SEPARATOR = '\0';       // dtxt separation character
    }
}
