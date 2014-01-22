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
import Entidades.TiposFamiliares;
import InterfacePersistencia.PersistenciaHvReferenciasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
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
public class AdministrarHvReferencias implements AdministrarHvReferenciasInterface {

    @EJB
    PersistenciaHvReferenciasInterface persistenciaHvReferencias;
    @EJB
    PersistenciaTiposFamiliaresInterface persistenciaTiposFamiliares;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    List<HvReferencias> listHvReferencias;
    HvReferencias hvReferencias;
    Empleados empleado;
    List<HVHojasDeVida> hvHojasDeVida;

    @Override
    public void borrarHvReferencias(List<HvReferencias> listaHvReferencias) {
        for (int i = 0; i < listaHvReferencias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaHvReferencias.borrar(listaHvReferencias.get(i));
        }
    }

    @Override
    public void crearHvReferencias(List<HvReferencias> listaHvReferencias) {
        for (int i = 0; i < listaHvReferencias.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaHvReferencias.crear(listaHvReferencias.get(i));
        }
    }

    @Override
    public void modificarHvReferencias(List<HvReferencias> listaHvReferencias) {
        for (int i = 0; i < listaHvReferencias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaHvReferencias.editar(listaHvReferencias.get(i));
        }
    }

    @Override
    public List<HvReferencias> consultarHvReferenciasPersonalesPorEmpleado(BigInteger secEmpleado) {
        try {
            listHvReferencias = persistenciaHvReferencias.consultarHvReferenciasPersonalesPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvReferencias hvEntrevistasPorEmplado");
            listHvReferencias = null;
        }
        return listHvReferencias;
    }

    @Override
    public List<HvReferencias> consultarHvReferenciasFamiliaresPorEmpleado(BigInteger secEmpleado) {
        try {
            listHvReferencias = persistenciaHvReferencias.consultarHvReferenciasFamiliarPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvReferencias hvEntrevistasPorEmplado");
            listHvReferencias = null;
        }
        return listHvReferencias;
    }

    @Override
    public HvReferencias consultarHvReferencia(BigInteger secHvEntrevista) {
        persistenciaHvReferencias.buscarHvReferencia(secHvEntrevista);
        return hvReferencias;
    }

    @Override
    public Empleados consultarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            System.out.println("ERROR AdministrarHvReferencias  buscarEmpleado ERROR =====" + e);
            return empleado;
        }
    }

    @Override
    public List<HVHojasDeVida> consultarHvHojasDeVida(BigInteger secuencia) {
        try {
            hvHojasDeVida = persistenciaHvReferencias.consultarHvHojaDeVidaPorEmpleado(secuencia);
            return hvHojasDeVida;
        } catch (Exception e) {
            hvHojasDeVida = null;
            System.out.println("ERROR AdministrarHvReferencias  buscarHvHojasDeVida ERROR =====" + e);
            return hvHojasDeVida;
        }
    }

    @Override
    public List<TiposFamiliares> consultarLOVTiposFamiliares() {
        try {
            List<TiposFamiliares> listTiposFamiliares;
            listTiposFamiliares = persistenciaTiposFamiliares.buscarTiposFamiliares();
            return listTiposFamiliares;
        } catch (Exception e) {
            System.err.println("ERROR EN ADMINISTRAR HV REFERENCIAS 1 ERROR " + e);
            return null;
        }
    }

}
