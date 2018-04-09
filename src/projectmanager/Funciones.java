/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager;

import java.util.Scanner;

/**
 *
 * @author Catherine Beata
 */
public class Funciones {

    Scanner sc = new Scanner(System.in);
    String correo;
    String clave;
    String pregCorreo;
    String registrar;
    int salir = 0;
    Correo c = new Correo();
    Conexion con = new Conexion();
    Menu menu = new Menu();

    public Funciones() {
    }

    public int opcionSalir() {
        System.out.println();
        System.out.println("****************************************************************************");
        System.out.println("                            SE HA CERRADO SESION                            ");
        System.out.println("            Copyright © 2018 Catherine Inc. All rights reserved.            ");
        System.out.println("****************************************************************************");
        return 1;
    }

    public void registrarUsuario(String correo) {
        int cont = 0;
        do {
            System.out.println();
            System.out.println("****************************************************************************");
            System.out.println(">   >   >  >  > > > > >>>>>>REGISTRO DE USUARIOS<<<<<< < < < <  <  <   <   <");
            System.out.println("****************************************************************************");
            if (cont == 0) {
                do {
                    System.out.println("Desea utilizar <" + correo + "> como su Usuario?  S/N");
                    pregCorreo = sc.nextLine();
                    if (pregCorreo.equalsIgnoreCase("S")) {
                        this.correo = correo;
                        cont++;
                    } else if (pregCorreo.equalsIgnoreCase("N")) {
                        cont++;
                    } else {
                        System.out.println("<----------------------| Porfavor ingrese <S> o <N> |---------------------->");
                        System.out.println();
                    }
                } while (!pregCorreo.equalsIgnoreCase("S") && !pregCorreo.equalsIgnoreCase("N"));
            }
            do {
                if (pregCorreo.equalsIgnoreCase("N")) {
                    System.out.println("Escriba su Correo Electrónico:                        Ingrese <0> para salir");
                    System.out.print("→ ");
                    this.correo = sc.nextLine();
                }
                if (this.correo.equalsIgnoreCase("0")) {
                    salir = 1;
                } else if (c.validarCorreo(this.correo) == false) {
                    System.out.println("<---------------------| Formato de Correo no Valido |---------------------->");
                    System.out.println();
                } else if (c.validarCorreo(this.correo) == true) {
                    System.out.println("Escriba su Contraseña: ");
                    System.out.print("→ ");
                    this.clave = sc.nextLine();
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("-------------------------Confirmacion de Registro---------------------------");
                    System.out.println("Correo: " + this.correo);
                    System.out.println("Contraseña: " + this.clave);
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.print("Desea Registrar sus datos? S/N → ");
                    registrar = sc.nextLine();

                    if (registrar.equalsIgnoreCase("S")) {
                        con.insertarUsuario(this.correo, this.clave);
                        pantallaInicio(this.correo);
                        salir = 1;
                    } else if (registrar.equalsIgnoreCase("N")) {
                        salir = 1;
                    }
                }

            } while (salir != 1);

        } while (salir != 1);
    }

    public void pantallaInicio(String correo) {
        int salir = 0;
        int opcion = 0;
        
        System.out.println();
        System.out.println("****************************************************************************");
        System.out.println(">   >   >  >  > > > > >>>>>>>PANTALLA DE INICIO<<<<<<< < < < <  <  <   <   <");
        System.out.println("****************************************************************************");
        System.out.println("Usuario Activo: " + correo);
        do {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("------------------------------MENU PRINCIPAL--------------------------------");
            System.out.println("1.Crea un Nuevo Proyecto");
            System.out.println("2.Crea una nueva Tarea");
            System.out.println("3.Ver tus Proyectos");
            System.out.println("4.Ver tus Tareas");
            System.out.println("5.Compartir Tarea");
            System.out.println("6.← Atras");
            System.out.println("----------------------------------------------------------------------------");
            System.out.print("Elije una opcion del Menu → ");
            opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                    menu.opcion1(correo);
                    break;
                case 2:
                    menu.opcion2(correo);
                    break;
                case 3:
                    menu.opcion3(correo);
                    break;
                case 4:
                    menu.opcion4(correo);
                    break;
                case 5:
                    menu.opcion5(correo);
                    break;
                case 6:
                    System.out.println();
                    salir = 1;
                    break;
                default:
                    System.out.println();
                    System.out.println("****************************| Opción no Valida |****************************");
                    System.out.println();
                    break;
            }
        } while (salir != 1);
    }
}
