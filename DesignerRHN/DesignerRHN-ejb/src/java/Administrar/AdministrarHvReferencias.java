/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarHvReferenciasInterface;
import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import InterfacePersistencia.PersistenciaHvReferenciasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarHvReferencias implements AdministrarHvReferenciasInterface {

    @EJB
    PersistenciaHvReferenciasInterface persistenciaHvReferencias;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    List<HvReferencias> listHvReferencias;
    HvReferencias hvReferencias;
    Empleados empleado;
    List<HVHojasDeVida> hvHojasDeVida;

    public void borrarHvReferencias(HvReferencias hvEntrevistas) {
        persistenciaHvReferencias.borrar(hvEntrevistas);
    }

    public void crearHvReferencias(HvReferencias hvEntrevistas) {
        persistenciaHvReferencias.crear(hvEntrevistas);
    }

    public void modificarHvReferencias(List<HvReferencias> listHvReferenciasModificadas) {
        for (int i = 0; i < listHvReferenciasModificadas.size(); i++) {
            System.out.println("Modificando...");
            hvReferencias = listHvReferenciasModificadas.get(i);
            persistenciaHvReferencias.editar(hvReferencias);
        }
    }

    public List<HvReferencias> MostrarHvReferenciasPorEmpleado(BigInteger secEmpleado) {
        try {
            listHvReferencias = persistenciaHvReferencias.buscarHvReferenciasPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvReferencias hvEntrevistasPorEmplado");
            listHvReferencias = null;
        }
        return listHvReferencias;
    }

    public HvReferencias mostrarHvReferencia(BigInteger secHvEntrevista) {
        persistenciaHvReferencias.buscarHvReferencia(secHvEntrevista);
        return hvReferencias;
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            System.out.println("ERROR AdministrarHvReferencias  buscarEmpleado ERROR =====" + e);
            return empleado;
        }
    }

    public List<HVHojasDeVida> buscarHvHojasDeVida(BigInteger secuencia) {
        try {
            hvHojasDeVida = persistenciaHvReferencias.buscarHvHojaDeVidaPorEmpleado(secuencia);
            return hvHojasDeVida;
        } catch (Exception e) {
            hvHojasDeVida = null;
            System.out.println("ERROR AdministrarHvReferencias  buscarHvHojasDeVida ERROR =====" + e);
            return hvHojasDeVida;
        }
    }
}
