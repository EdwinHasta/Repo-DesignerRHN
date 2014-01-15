/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaHvReferencias1Interface {

    /**
     * Método encargado de insertar una HvReferencia en la base de datos.
     *
     * @param hvReferencias HvReferencia que se quiere crear.
     */
    public void crear(HvReferencias hvReferencias);

    /**
     * Método encargado de modificar una HvReferencia de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param hvReferencias HvReferencias con los cambios que se van a realizar.
     */
    public void editar(HvReferencias hvReferencias);

    /**
     * Método encargado de eliminar de la base de datos una HvReferencia que
     * entra por parámetro.
     *
     * @param hvReferencias HvReferencias que se quiere eliminar.
     */
    public void borrar(HvReferencias hvReferencias);

    /**
     * Método encargado de buscar el HvReferencias con la secuencia dada por
     * parámetro.
     *
     * @param secuenciaHvReferencias Secuencia de la HvRefencia que se quiere
     * encontrar.
     * @return Retorna la HvReferencia identificado con la secuencia dada por
     * parámetro.
     */
    public HvReferencias buscarHvReferencia1(BigInteger secuenciaHvReferencias);

    /**
     * Método encargado de buscar todas las HvReferencias existentes en la base
     * de datos.
     *
     * @return Retorna una lista de HvReferencias.
     */
    public List<HvReferencias> buscarHvReferencias1();

    /**
     * *
     * Metodo encargado de traer la lista de HvReferencias por empleado donde
     * las referecias en su campo Tipo='FAMILIARES'
     *
     * @param secEmpleado Secuencia del empleado
     * @return Lista de Referencias Por empleado
     */
    public List<HvReferencias> buscarHvReferenciasPorEmpleado1(BigInteger secEmpleado);

    /**
     * Metodo encargado de traer las hojas de vida del empleado relacionadas con
     * HvReferencias
     *
     * @param secEmpleado Secuencia del empleado
     * @return Retorna una lista De HVHojasDeVida
     */
    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado);
}
