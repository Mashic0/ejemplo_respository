/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import sw.ConversionSW;
import sw.ConversionSW_Service;

import java.util.Scanner;

/**
 *
 * @author marce
 */
public class modelo {

    public static void main(String[] args) {
        ConversionSW_Service servicio = new ConversionSW_Service();
        ConversionSW cliente = servicio.getConversionSWPort();
        Scanner reader = new Scanner(System.in);
        int op = 0;
        int op2 = 0;

        do {
            System.out.println("¿Desea ingresar?");
            System.out.println("1. Login");
            System.out.println("2. No (salir)");
            op = reader.nextInt();
            reader.nextLine();
            switch (op) {
                case 1:
                    System.out.println("Ingrese el usuario:");
                    String usuario = reader.nextLine();

                    System.out.println("Ingrese la clave:");
                    String clave = reader.nextLine();

                    if (cliente.login(usuario, clave)) {

                        System.out.println("Login correcto");

                        do {
                            System.out.println("¿Desea realizar una consulta?");
                            System.out.println("1. Si");
                            System.out.println("2. Logout");

                            op2 = reader.nextInt();
                            reader.nextLine();

                            switch (op2) {
                                case 1:
                                    System.out.println("Ingrese el dni:");
                                    String dni = reader.nextLine();
                                    System.out.println(cliente.buscarDatos(dni));
                                    break;

                                case 2:
                                    System.out.println("Logout realizado");
                                    break;

                                default:
                                    System.out.println("Opción incorrecta");
                            }

                        } while (op2 != 2);

                    } else {
                        System.out.println("Usuario o clave incorrectos");
                    }

                    break;
                case 2:
                    System.out.println("Adios");
                    return;
                default:
                    System.err.println("Opcion incorrecta");
            }

        } while (op != 2);

    }
}
