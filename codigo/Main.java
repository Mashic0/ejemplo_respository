/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestionproductostoolbar;

import VISTA.*;
import MODELO.*;
import CONTROLADOR.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author marce
 */
public class GestionProductosToolBar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Establecer estilo
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            //Instanciar vista
            Vista newVista = new Vista();
            newVista.setVisible(true);
            //Instanciar productos
            ModeloProductos newModeloProductos = new ModeloProductos();
            ControladorProductos newControladorProductos = new ControladorProductos(newModeloProductos, newVista);
        });

    }

}