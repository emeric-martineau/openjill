/**
 * Jill of the Jungle tool.
 */
package org.jill.sha;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jill.sha.ui.button.ExportButton;
import org.jill.sha.ui.button.SelectDirButton;
import org.jill.sha.ui.button.SelectFileButton;


/**
 * Main form
 *
 * @author emeric martineau
 * @version 1.0
 */
public class ShaFileExportFrame extends JFrame {

    /**
     * @throws HeadlessException
     */
    public ShaFileExportFrame() throws HeadlessException {
        super();

        buildUI();
    }

    /**
     * @param gc
     */
    protected ShaFileExportFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    /**
     * @param title
     * @throws HeadlessException
     */
    protected ShaFileExportFrame(String title) throws HeadlessException {
        super(title);
    }

    /**
     * @param title
     * @param gc
     */
    protected ShaFileExportFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    /**
     * Buil User Interface
     */
    protected void buildUI() {
        // Default size
        setSize(300, 200);
        // Title
        setTitle("Jill of the Jungle image ripper");
        // Close program if clique on arrow
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // See http://stackoverflow.com/questions/7769885/interruptedexception-after-cancel-file-open-dialog-1-6-0-26
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });


        getContentPane().add(buildComponent());


        // Display the window.
        pack();
        // Show window
        setVisible(true);

        // Screen center
        setLocationRelativeTo(null);
        // Not resizable
        setResizable(false);
    }

    /**
     * Build component. Call bay buildUI()
     *
     * @return a panel
     */
    protected JPanel buildComponent() {
        GridLayout layout = new GridLayout(0, 3);

        JPanel panel = new JPanel();

        // Add layout
        panel.setLayout(layout);

        JLabel label = new JLabel("Please select file (SHA format) to extract picture :");
        panel.add(label);

        JTextField fileToRead = new JTextField();
        panel.add(fileToRead);

        JButton button = new SelectFileButton("Choose file...", fileToRead);
        panel.add(button);

        label = new JLabel("Please select path where extract file :");
        panel.add(label);

        JTextField dirToWrite = new JTextField();
        panel.add(dirToWrite);

        button = new SelectDirButton("Choose directory...", dirToWrite);
        panel.add(button);

        button = new ExportButton("Extract", fileToRead, dirToWrite);
        panel.add(button);

        return panel;
    }
}
