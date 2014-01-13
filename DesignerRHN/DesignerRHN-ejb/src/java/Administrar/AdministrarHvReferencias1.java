/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import Entidades.TiposFamiliares;
import InterfaceAdministrar.AdministrarHvReferencias1Interface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaHvReferencias1Interface;
import InterfacePersistencia.PersistenciaTiposFamiliaresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarHvReferencias1 implements AdministrarHvReferencias1Interface {

  @EJB
    PersistenciaTiposFamiliaresInterface persistenciaTiposFamiliares;
    @EJB
    PersistenciaHvReferencias1Interface persistenciaHvReferencias;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    List<HvReferencias> listHvReferencias;
    HvReferencias hvReferencias;
    Empleados empleado;
    List<HVHojasDeVida> hvHojasDeVida;
    List<TiposFamiliares> listTiposFamiliares;

    public void borrarHvReferencias1(HvReferencias hvEntrevistas) {
        persistenciaHvReferencias.borrar(hvEntrevistas);
    }

    public void crearHvReferencias1(HvReferencias hvEntrevistas) {
        persistenciaHvReferencias.crear(hvEntrevistas);
    }

    public void modificarHvReferencias1(List<HvReferencias> listHvReferenciasModificadas) {
        for (int i = 0; i < listHvReferenciasModificadas.size(); i++) {
            System.out.println("Modificando...");
            hvReferencias = listHvReferenciasModificadas.get(i);
            persistenciaHvReferencias.editar(hvReferencias);
        }
    }

    public List<HvReferencias> MostrarHvReferenciasPorEmpleado1(BigInteger secEmpleado) {
        try {
            listHvReferencias = persistenciaHvReferencias.buscarHvReferenciasPorEmpleado1(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvReferencias hvEntrevistasPorEmplado");
            listHvReferencias = null;
        }
        return listHvReferencias;
    }

    public HvReferencias mostrarHvReferencia1(BigInteger secHvEntrevista) {
        persistenciaHvReferencias.buscarHvReferencia1(secHvEntrevista);
        return hvReferencias;
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            System.out.println("ERROR AdministrarHvReferencias 1  buscarEmpleado ERROR =====" + e);
            return empleado;
        }
    }

    public List<HVHojasDeVida> buscarHvHojasDeVida(BigInteger secuencia) {
        try {
            hvHojasDeVida = persistenciaHvReferencias.buscarHvHojaDeVidaPorEmpleado(secuencia);
            return hvHojasDeVida;
        } catch (Exception e) {
            hvHojasDeVida = null;
            System.out.println("ERROR AdministrarHvReferencias 1  buscarHvHojasDeVida ERROR =====" + e);
            return hvHojasDeVida;
        }
    }

    public List<TiposFamiliares> buscarTiposFamiliares() {
        try {
            listTiposFamiliares = persistenciaTiposFamiliares.buscarTiposFamiliares();
            return listTiposFamiliares;
        } catch (Exception e) {
            System.err.println("ERROR EN ADMINISTRAR HV REFERENCIAS 1 ERROR " + e);
            return null;
        }
    }
}
