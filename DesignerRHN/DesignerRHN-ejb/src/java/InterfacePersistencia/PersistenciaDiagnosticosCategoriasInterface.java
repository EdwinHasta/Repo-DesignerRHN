/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Diagnosticoscategorias;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'DiagnosticosCategorias' 
 * de la base de datos.
 * @author Viktor
 */
public interface PersistenciaDiagnosticosCategoriasInterface {
    /**
     * Método encargado de insertar un diagnosticoCategoria en la base de datos.
     * @param diagnosticosCategorias diagnosticoCategoria que se quiere crear.
     */
    public void crear(Diagnosticoscategorias diagnosticosCategorias);
    /**
     * Método encargado de modificar un diagnosticoCategoria de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param diagnosticosCategorias diagnosticoCategoria con los cambios que se van a realizar.
     */
    public void editar(Diagnosticoscategorias diagnosticosCategorias);
    /**
     * Método encargado de eliminar de la base de datos el diagnosticoCategoria que entra por parámetro.
     * @param diagnosticosCategorias diagnosticoCategoria que se quiere eliminar.
     */
    public void borrar(Diagnosticoscategorias diagnosticosCategorias);
    /**
     * Método encargado de buscar todos los diagnosticosCategorias existentes en la base de datos.
     * @return Retorna una lista de diagnosticosCategorias
     */
    public List<Diagnosticoscategorias> buscarDiagnosticos();
}
