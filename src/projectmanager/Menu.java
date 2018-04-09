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
public class Menu {

    Scanner sc = new Scanner(System.in);
    Conexion con = new Conexion();
    Correo c = new Correo();

    public Menu() {
    }

    public void opcion1(String correo) {
        String nombre, descripcion, fechaInicio, fechaFin, registrar;
        int encargado;
        int salir = 0;

        do {
            System.out.println();
            System.out.println("1. CREAR PROYECTO........ . . . . . .  .  .  .  .    .    .    .     .     .");
            System.out.println("Ingrese Nombre del Proyecto:");
            System.out.print("→");
            nombre = sc.nextLine();
            System.out.println("Ingrese Descripcion del Proyecto:");
            System.out.print("→");
            descripcion = sc.nextLine();
            do {
                System.out.println("Ingrese Fecha de Inicio:                                 Formato: YYYY-MM-DD");
                System.out.print("→");
                fechaInicio = sc.nextLine();
            } while (validarFormatoFechaInicio(fechaInicio) == false);
            do {
                System.out.println("Ingrese Fecha de Finalización:                           Formato: YYYY-MM-DD");
                System.out.print("→");
                fechaFin = sc.nextLine();
            } while (validarFormatoFechaFin(fechaInicio, fechaFin) == false);
            do {
                con.mostrarListaEmpleados();
                System.out.print("Ingrese el ID del encargado del Proyecto: → ");
                encargado = sc.nextInt();
                if (con.verificarUsuarioID(encargado) == false) {
                    System.out.println("<-------------------------| Usuario no existente |------------------------->");
                    System.out.println();
                } else {
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("-------------------------Confirmación de Proyecto---------------------------");
                    System.out.println("Proyecto: " + nombre);
                    System.out.println("Descripcion: " + descripcion);
                    System.out.println("Fecha Inicio: " + fechaInicio);
                    System.out.println("Fecha Finalización: " + fechaFin);
                    System.out.println("Encargado: " + con.retornarCorreo(encargado));
                    System.out.println("----------------------------------------------------------------------------");
                    sc.nextLine();
                    System.out.print("Desea Registrar sus datos? S/N → ");
                    registrar = sc.nextLine();
                    
                    if (registrar.equalsIgnoreCase("S")) {
                        con.insertarProyecto(nombre, descripcion, fechaInicio, fechaFin, encargado);
                        c.enviarCorreoAsignacion(con.retornarCorreo(encargado), nombre, descripcion, fechaInicio, fechaFin);
                        salir = 1;
                    }else if (registrar.equalsIgnoreCase("N")) {
                        salir = 1;
                    }
                }
            } while (salir!=1);

        } while (salir!=1);
    }

    public void opcion2(String correo) {

    }

    public void opcion3(String correo) {
        String continuar;
        System.out.println();
        System.out.println("1. VER MIS PROYECTOS........ . . . . .  .  .  .  .   .    .    .     .     .");
        con.mostrarMisProyectos(con.retornarid(correo));
        System.out.println("Presione <C> para continuar");
        continuar = sc.nextLine();
        
    }

    public void opcion4(String correo) {

    }

    public void opcion5(String correo) {

    }

    public boolean validarFormatoFechaInicio(String fechaInicio) {
        boolean fechaValidacion;
        boolean respuesta = true;
        fechaValidacion = fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}");
        int seqYear, seqMonth, seqDay, year, month, day;
        GregorianCalendar calendar = new GregorianCalendar();

        if (fechaValidacion == false) {
            System.out.println("<----------------------| Formato de Fecha no Valido |---------------------->");
            System.out.println();
            respuesta = false;
        } else {
            seqYear = Integer.parseInt(fechaInicio.subSequence(0, 4).toString());
            seqMonth = Integer.parseInt(fechaInicio.subSequence(5, 7).toString());
            seqDay = Integer.parseInt(fechaInicio.subSequence(8, 10).toString());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DATE);

            if (seqYear < year || (seqYear == year && seqMonth < month) || (seqYear == year && seqMonth == month && seqDay < day)) {
                System.out.println("<-----------------------| Fecha menor a la Actual |------------------------>");
                System.out.println();
                respuesta = false;
            } else if (seqMonth <= 0 || seqMonth > 12) {
                System.out.println("<--------------------------| Mes fuera de rango |-------------------------->");
                System.out.println();
                respuesta = false;
            } else if (seqMonth == 1 || seqMonth == 3 || seqMonth == 5
                    || seqMonth == 7 || seqMonth == 8 || seqMonth == 10
                    || seqMonth == 12) {
                if (seqDay < 1 || seqDay > 31) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            } else if (seqMonth == 4 || seqMonth == 6 || seqMonth == 9
                    || seqMonth == 11) {
                if (seqDay < 1 || seqDay > 30) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            } else if (seqMonth == 2) {
                if (seqDay == 29) {
                    if (calendar.isLeapYear(seqYear) == false) {
                        System.out.println("<---------------------------| Año no Bisiesto |---------------------------->");
                        System.out.println();
                        respuesta = false;
                    }
                } else if (seqDay > 29) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            }
        }

        return respuesta;
    }

    public boolean validarFormatoFechaFin(String fechaInicio, String fechaFin) {
        int seqYear, seqMonth, seqDay, year, month, day;
        year = Integer.parseInt(fechaInicio.subSequence(0, 4).toString());
        month = Integer.parseInt(fechaInicio.subSequence(5, 7).toString());
        day = Integer.parseInt(fechaInicio.subSequence(8, 10).toString());
        GregorianCalendar calendar = new GregorianCalendar();
        boolean fechaValidacion;
        boolean respuesta = true;
        fechaValidacion = fechaFin.matches("\\d{4}-\\d{2}-\\d{2}");

        if (fechaValidacion == false) {
            System.out.println("<----------------------| Formato de Fecha no Valido |---------------------->");
            System.out.println();
            respuesta = false;
        } else {
            seqYear = Integer.parseInt(fechaFin.subSequence(0, 4).toString());
            seqMonth = Integer.parseInt(fechaFin.subSequence(5, 7).toString());
            seqDay = Integer.parseInt(fechaFin.subSequence(8, 10).toString());

            if (seqYear < year || (seqYear == year && seqMonth < month) || (seqYear == year && seqMonth == month && seqDay < day)) {
                System.out.println("<-----------------------| Fecha menor a la Actual |------------------------>");
                System.out.println();
                respuesta = false;
            } else if (seqMonth <= 0 || seqMonth > 12) {
                System.out.println("<--------------------------| Mes fuera de rango |-------------------------->");
                System.out.println();
                respuesta = false;
            } else if (seqMonth == 1 || seqMonth == 3 || seqMonth == 5
                    || seqMonth == 7 || seqMonth == 8 || seqMonth == 10
                    || seqMonth == 12) {
                if (seqDay < 1 || seqDay > 31) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            } else if (seqMonth == 4 || seqMonth == 6 || seqMonth == 9
                    || seqMonth == 11) {
                if (seqDay < 1 || seqDay > 30) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            } else if (seqMonth == 2) {
                if (seqDay == 29) {
                    if (calendar.isLeapYear(seqYear) == false) {
                        System.out.println("<---------------------------| Año no Bisiesto |---------------------------->");
                        System.out.println();
                        respuesta = false;
                    }
                } else if (seqDay > 29) {
                    System.out.println("<--------------------------| Día fuera de rango |-------------------------->");
                    System.out.println();
                    respuesta = false;
                }
            }
        }

        return respuesta;
    }
}
