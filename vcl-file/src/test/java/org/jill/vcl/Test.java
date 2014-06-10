/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jill.vcl;

import java.io.IOException;
import org.jill.file.FileAbstractByte;
import org.jill.file.FileAbstractByteImpl;

/**
 *
 * @author Emeric MARTINEAU
 */
public class Test {
    public static void main(String[] args) throws IOException {
        final FileAbstractByte f = new FileAbstractByteImpl();
        f.load("jill1.vcl");
        final VclFile vf = new VclFileImpl();
        vf.load(f);

        for(VclTextEntry vte : vf.getVclText()) {
            System.out.println("/////////////////");
            System.out.println(vte.getText());
            System.out.println("****");
        }
    }
}
