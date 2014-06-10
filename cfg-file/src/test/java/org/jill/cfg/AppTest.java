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
    private Properties prop = new Properties();

    private String homePath ;

    private String tempPath ;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) throws IOException
    {
        super( testName );

        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties")) ;
        homePath = prop.getProperty("home") ;
        tempPath = prop.getProperty("temp") ;
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
