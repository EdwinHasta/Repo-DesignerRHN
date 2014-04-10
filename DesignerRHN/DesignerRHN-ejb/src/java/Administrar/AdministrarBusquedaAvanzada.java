/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import ClasesAyuda.ParametrosQueryBusquedaAvanzada;
import Entidades.Empleados;
import InterfaceAdministrar.AdministrarBusquedaAvanzadaInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author PROYECTO01
 */
@Stateful
public class AdministrarBusquedaAvanzada implements AdministrarBusquedaAvanzadaInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public List<Empleados> ejecutarQueryBusquedaAvanzadaPorModulos(String query) {
        try {
            List<Empleados> lista = null;
            return lista;
        } catch (Exception e) {
            System.out.println("Error ejecutarQueryBusquedaAvanzadaPorModulos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String armarQueryModulosBusquedaAvanzada(List<ParametrosQueryBusquedaAvanzada> listaParametro) {
        try {
            String query = "";
            int tam = listaParametro.size();
            boolean usoWhere = false;
            if (tam > 0) {

            }
            return query;
        } catch (Exception e) {
            System.out.println("Error armarQueryModulosBusquedaAvanzada Admi : " + e.toString());
            return "";
        }
    }

    // public List<Vista> ejecutarQueryColumnasAdicionadas(String query)
    // public String armarQueryColumnasAdicionadas(){} 
}
