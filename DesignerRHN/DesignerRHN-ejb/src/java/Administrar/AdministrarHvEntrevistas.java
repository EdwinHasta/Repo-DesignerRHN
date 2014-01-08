/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import InterfaceAdministrar.AdministrarHvEntrevistasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaHvEntrevistasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarHvEntrevistas implements AdministrarHvEntrevistasInterface {

   @EJB
    PersistenciaHvEntrevistasInterface persistenciaHvEntrevistas;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    List<HvEntrevistas> listHvEntrevistas;
    HvEntrevistas hvEntrevistas;
    Empleados empleado;
    List<HVHojasDeVida> hvHojasDeVida;

    public void borrarHvEntrevistas(HvEntrevistas hvEntrevistas) {
        persistenciaHvEntrevistas.borrar(hvEntrevistas);
    }

    public void crearHvEntrevistas(HvEntrevistas hvEntrevistas) {
        persistenciaHvEntrevistas.crear(hvEntrevistas);
    }

    public void modificarHvEntrevistas(List<HvEntrevistas> listHvEntrevistasModificadas) {
        for (int i = 0; i < listHvEntrevistasModificadas.size(); i++) {
            System.out.println("Modificando...");
            hvEntrevistas = listHvEntrevistasModificadas.get(i);
            persistenciaHvEntrevistas.editar(hvEntrevistas);
        }
    }

    public List<HvEntrevistas> MostrarHvEntrevistasPorEmpleado(BigInteger secEmpleado) {
        try {
            listHvEntrevistas = persistenciaHvEntrevistas.buscarHvEntrevistasPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvEntrevistas hvEntrevistasPorEmplado");
            listHvEntrevistas = null;
        }
        return listHvEntrevistas;
    }

    public HvEntrevistas mostrarHvEntrevista(BigInteger secHvEntrevista) {
        persistenciaHvEntrevistas.buscarHvEntrevista(secHvEntrevista);
        return hvEntrevistas;
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            System.out.println("ERROR AdministrarHvEntrevistas  buscarEmpleado ERROR =====" + e);
            return empleado;
        }
    }

    public List<HVHojasDeVida> buscarHVHojasDeVida(BigInteger secuencia) {
        try {
            hvHojasDeVida = persistenciaHvEntrevistas.buscarHvHojaDeVidaPorEmpleado(secuencia);
            return hvHojasDeVida;
        } catch (Exception e) {
            hvHojasDeVida = null;
            System.out.println("ERROR AdministrarHvEntrevistas  buscarHVHojasDeVida ERROR =====" + e);
            return hvHojasDeVida;
        }
    }
}
