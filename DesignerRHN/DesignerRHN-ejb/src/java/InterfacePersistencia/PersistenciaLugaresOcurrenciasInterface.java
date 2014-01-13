/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.LugaresOcurrencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaLugaresOcurrenciasInterface {

    /**
     * Método encargado de insertar un Lugar Ocurrencia en la base de datos.
     *
     * @param lugaresOcurrencias que se quiere crear.
     */
    public void crear(LugaresOcurrencias lugaresOcurrencias);

    /**
     * Método encargado de modificar un Lugar Ocurrencia de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param lugaresOcurrencias LugaresOcurrencias con los cambios que se van a
     * realizar.
     */
    public void editar(LugaresOcurrencias lugaresOcurrencias);

    /**
     * Método encargado de eliminar de la base de datos un LugarOcurrencia que
     * entra por parámetro.
     *
     * @param lugaresOcurrencias LugaresOcurrencias que se quiere eliminar.
     */
    public void borrar(LugaresOcurrencias lugaresOcurrencias);

    /**
     * Método encargado de buscar un LugarOcurrencia con la secuencia dada por
     * parámetro.
     *
     * @param secuenciaLO Secuencia del Lugar Ocurrencia que se quiere
     * encontrar.
     * @return Retorna un LugarOcurrencia identificado con la secuencia dada por
     * parámetro.
     */
    public LugaresOcurrencias buscarLugaresOcurrencias(BigInteger secuenciaLO);

    /**
     * Método encargado de buscar todas los LugaresOcurrencias existentes en la
     * base de datos.
     *
     * @return Retorna una lista de Lugares Ocurrencias.
     */
    public List<LugaresOcurrencias> buscarLugaresOcurrencias();

    /**
     * Método encargado de revisar si existe una relacion entre una
     * LugaresOcurrencias específica y algún Proyecto. Adémas de la revisión,
     * cuenta cuantas relaciones existen.
     *
     * @param secuencia Secuencia del LugarOcurrencia.
     * @return Retorna el número de SoAccidntes relacionados con el
     * LugarOcurrencia cuya secuencia coincide con el parámetro.
     */
    public BigInteger contadorSoAccidentes(BigInteger secuencia);
}
