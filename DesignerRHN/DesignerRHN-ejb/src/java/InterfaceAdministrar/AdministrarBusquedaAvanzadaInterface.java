/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import ClasesAyuda.ParametrosQueryBusquedaAvanzada;
import Entidades.Empleados;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarBusquedaAvanzadaInterface {

    public List<Empleados> ejecutarQueryBusquedaAvanzadaPorModulos(String query);

    public String armarQueryModulosBusquedaAvanzada(List<ParametrosQueryBusquedaAvanzada> listaParametro);

}
