package org.jill.cfg;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    private final String homePath ;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     *
     * @throws IOException if error
     */
    public AppTest( String testName ) throws IOException
    {
        super( testName );

        final Properties prop = new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties")) ;
        homePath = prop.getProperty("home");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     *
     * @throws IOException if error
     */
    public void testFile() throws IOException
    {
        FileAbstractByte f = new FileAbstractByteImpl();

        f.load(homePath + "jill1.cfg") ;

        final CfgFile cfgFile = new CfgFileImpl();
        cfgFile.load(f, "JN1") ;

        final List<HighScoreItem> listScore = cfgFile.getHighScore() ;

        for(HighScoreItem hsi : listScore) {
            System.out.println("Score : " + hsi.getScore() + " Name : '" + hsi.getName() + "'") ;
        }

        final List<SaveGameItem> listSave = cfgFile.getSaveGame() ;

        for(SaveGameItem sgi : listSave) {
            System.out.println("Name : '" + sgi.getName() + "' Save name : '" +
                    sgi.getSaveGameFile() + "' Save map name : '" +
                    sgi.getSaveMapFile() + "'");
        }
    }
}
