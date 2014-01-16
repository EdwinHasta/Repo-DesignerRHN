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

    @Override
    public void modificarHvEntrevistas(List<HvEntrevistas> listHvEntrevistas) {
        for (int i = 0; i < listHvEntrevistas.size(); i++) {
            System.out.println("Modificando...");
            persistenciaHvEntrevistas.editar(listHvEntrevistas.get(i));
        }
    }

    @Override
    public void borrarHvEntrevistas(List<HvEntrevistas> listHvEntrevistas) {
        for (int i = 0; i < listHvEntrevistas.size(); i++) {
            System.out.println("Borrando...");
            persistenciaHvEntrevistas.borrar(listHvEntrevistas.get(i));
        }
    }

    @Override
    public void crearHvEntrevistas(List<HvEntrevistas> listHvEntrevistas) {
        for (int i = 0; i < listHvEntrevistas.size(); i++) {
            System.out.println("Creando...");
            persistenciaHvEntrevistas.crear(listHvEntrevistas.get(i));
        }
    }

    @Override
    public List<HvEntrevistas> consultarHvEntrevistasPorEmpleado(BigInteger secEmpleado) {
        List<HvEntrevistas> listHvEntrevistas;
        try {
            listHvEntrevistas = persistenciaHvEntrevistas.buscarHvEntrevistasPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en AdministrarHvEntrevistas hvEntrevistasPorEmplado");
            listHvEntrevistas = null;
        }
        return listHvEntrevistas;
    }

    @Override
    public HvEntrevistas consultarHvEntrevista(BigInteger secHvEntrevista) {
        HvEntrevistas hvEntrevistas;
        hvEntrevistas = persistenciaHvEntrevistas.buscarHvEntrevista(secHvEntrevista);
        return hvEntrevistas;
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        Empleados empleado;
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            System.out.println("ERROR AdministrarHvEntrevistas  buscarEmpleado ERROR =====" + e);
            return empleado;
        }
    }

    @Override
    public List<HVHojasDeVida> buscarHVHojasDeVida(BigInteger secuencia) {
        List<HVHojasDeVida> hvHojasDeVida;
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
