/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author Catherine Beata
 */
public class ProjectManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String correo;
        String pass = null;
        String pregRegistro;
        int salir = 0;
        Funciones f = new Funciones();
        Correo c = new Correo();
        Conexion con = new Conexion();

        System.out.println("****************************************************************************");
        System.out.println("                 BIENVENIDO A SU ADMINISTRADOR DE PROYECTOS                 ");
        do {
            System.out.println("****************************************************************************");
            System.out.println(">   >   >  >  > > > > > >>>>>>INICIO DE SESION<<<<<< < < < < <  <  <   <   <");
            System.out.println("****************************************************************************");
            System.out.println("Porfavor ingrese su Correo:                          Ingrese <0> para cerrar");
            System.out.print("→ ");
            correo = sc.nextLine();

            if (correo.equalsIgnoreCase("0")) {
                salir = f.opcionSalir();
            } else if (c.validarCorreo(correo) == false) {
                System.out.println("<---------------------| Formato de Correo no Valido |---------------------->");
                System.out.println();
            } else if (con.verificarUsuario(correo) == false) {
                int s = 0;
                do {
                    System.out.println("El Usuario ingresado aun no existe. Desea Registrarse ahora? <S>/<N>");
                    pregRegistro = sc.nextLine();
                    if (pregRegistro.equalsIgnoreCase("S")) {
                        f.registrarUsuario(correo);
                        s = 1;
                    } else if (pregRegistro.equalsIgnoreCase("N")) {
                        System.out.println();
                        s = 1;
                    } else {
                        System.out.println("<----------------------| Porfavor ingrese <S> o <N> |---------------------->");
                        System.out.println();
                    }
                } while (s != 1);
            } else {
                String resp = null;
                do{
                System.out.println("Ingrese su Contraseña:");
                System.out.print("→ ");
                pass = sc.nextLine();
                    if (con.verificarClave(pass, correo) == false) {
                        System.out.println("<------------------------| Contraseña Incorrecta |------------------------->");
                        System.out.println("Desea intentar de nuevo?    <S>/<N>");
                        System.out.print("→ ");
                        resp = sc.nextLine();
                    }else {
                        resp = "N";
                    }
                }while(resp.equalsIgnoreCase("S"));
                f.pantallaInicio(correo);
            }

        } while (salir != 1);
    }

}
