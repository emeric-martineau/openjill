package org.jill.dma;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;
import org.jill.jn.BackgroundLayer;
import org.jill.jn.BackgroundLayerImpl;
import org.jill.jn.JnFile;
import org.jill.jn.JnFileImpl;
import org.jill.jn.ObjectItem;
import org.jill.jn.ObjectItemImpl;
import org.jill.jn.SaveData;
import org.jill.jn.StringItem;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    private String homePath ;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) throws IOException
    {
        super( testName );

        final Properties prop = new Properties();

        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties")) ;
        homePath = prop.getProperty("home") ;
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
    public void testDumpBackground() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "map.jn1");
        final JnFile jnFile = new JnFileImpl();
        jnFile.load(f) ;
        final BackgroundLayer background = jnFile.getBackgroundLayer() ;

        System.out.println("Background layer :") ;
        System.out.println("") ;

        for(int indexX = 0; indexX < BackgroundLayerImpl.MAP_WIDTH; indexX++)
        {
            for(int indexY = 0; indexY < BackgroundLayerImpl.MAP_HEIGHT; indexY++)
            {
                System.out.print(
                        String.format("%04X ",
                                    background.getMapCode(indexX, indexY))) ;
            }

            System.out.println("") ;
        }
    }

    /**
     * Rigourous Test :-).
     *
     * @throws InvocationTargetException error
     * @throws IllegalAccessException error
     * @throws IllegalArgumentException error
     */
    public void testDumpObject() throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "map.jn1");

        final JnFile jnFile = new JnFileImpl();
        jnFile.load(f) ;
        final List<ObjectItem> objectLayer = jnFile.getObjectLayer() ;
        final int nbObject = objectLayer.size() ;

        ObjectItem currentObject ;

        System.out.println("Object layer (" + String.valueOf(nbObject) + ") :") ;
        System.out.println("") ;

        final Class tClass = ObjectItemImpl.class;

        final Method[] methods = tClass.getMethods();

        String methodeName ;

        for (ObjectItem anObjectLayer : objectLayer) {
            currentObject = anObjectLayer;

            System.out.println(
                    String.format("Object #%d",
                                    currentObject.getIndex()));

            for (Method method : methods) {
                methodeName = method.getName();
                if (methodeName.startsWith("get") &&
                        !methodeName.equals("getClass")) {
                    methodeName = methodeName.substring(3);
                    System.out.print("    ");
                    System.out.print(methodeName.substring(0, 1).
                            toLowerCase(Locale.US));
                    System.out.print(methodeName.substring(1));
                    System.out.print(" = ");
                    System.out.println(method.invoke(currentObject, new Object[]{}));
                }
            }
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testDumpSaveData() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "map.jn1");

        final JnFile jnFile = new JnFileImpl();
        jnFile.load(f) ;
        final SaveData saveData = jnFile.getSaveData() ;

        System.out.println("Save data layer :") ;
        System.out.println("") ;

        final int level = saveData.getLevel() ;

        if (level == SaveData.MAP_LEVEL)
        {
            System.out.println("Level : MAP") ;
        }
        else
        {
            System.out.println(
                String.format("Level : %d", level)) ;
        }

        System.out.println(
            String.format("Health : %d", saveData.getHealth())) ;

        System.out.println(
                String.format("Score : %d", saveData.getScore())) ;

        final List<Integer> inventory = saveData.getInventory() ;

        System.out.println(
                String.format("Inventory (%d)", inventory.size())) ;

        for(int index = 0; index < inventory.size(); index++)
        {
            System.out.print("    ");
            System.out.println(
                    String.format("#%d : %d",
                            new Object[] {
                                index,
                                inventory.get(index) }
                    )) ;
        }
    }


    /**
     * Rigourous Test :-)
     */
    public void testStringStack() throws IOException
    {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load(homePath + "map.jn1");

        final JnFile jnFile = new JnFileImpl();
        jnFile.load(f) ;
        final List<StringItem> stringStack = jnFile.getStringStack() ;
        final Iterator<StringItem> it = stringStack.iterator() ;

        System.out.println("String stack :") ;
        System.out.println("") ;

        while(it.hasNext())
        {
            System.out.println(
                String.format("    '%s'",
                        new Object[] { it.next().getValue() }
                )) ;
        }
    }
}
