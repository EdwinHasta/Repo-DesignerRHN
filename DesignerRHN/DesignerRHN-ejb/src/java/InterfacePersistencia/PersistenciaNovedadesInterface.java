/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Novedades;
import java.math.BigInteger;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Novedades' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaNovedadesInterface {
    /**
     * Método encargado de insertar una Novedad en la base de datos.
     * @param novedades Novedad que se quiere crear.
     */
    public void crear(Novedades novedades);
    /**
     * Método encargado de modificar una Novedad de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param novedades Novedad con los cambios que se van a realizar.
     */
    public void editar(Novedades novedades);
    /**
     * Método encargado de eliminar de la base de datos la Novedad que entra por parámetro.
     * @param novedades Novedad que se quiere eliminar.
     */
    public void borrar(Novedades novedades);    
    /**
     * Método encargado de buscar todas las novedades de un Empleado especifico.
     * @param secuenciaEmpleado Secuencia del Empleado al que se le quieren buscar las novedades.
     * @return Retorna una lista de Novedades asociadas a un empleado.
     */
    public List<Novedades> todasNovedadesEmpleado(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de buscar todas las novedades de un Concepto especifico.
     * @param secuenciaConcepto Secuencia del Concepto al que se le quieren buscar las novedades.
     * @return Retorna una lista de Novedades asociadas a un Concepto. 
     */
    public List<Novedades> todasNovedadesConcepto(BigInteger secuenciaConcepto);
    /**
     * Método encargado de buscar todas las novedades de un Tercero especifico.
     * @param secuenciaTercero Secuencia del Tercero al que se le quieren buscar las novedades.
     * @return Retorna una lista de Novedades asociadas a un Tercero. 
     */
    public List<Novedades> todasNovedadesTercero(BigInteger secuenciaTercero);
    /**
     * Método encargado de buscar las Novedades reportadas por un usuario y con un documentoSoporte específico. 
     * @param usuarioBD Secuencia del usuario que reportó las novedades.
     * @param documentoSoporte Texto referente al atributo documentoSoporte de las novedades
     * @return Retorna una lista de Novedades que cumplen con las condiciones dadas por los parametros.
     */
    public List<Novedades> novedadesParaReversar(BigInteger usuarioBD, String documentoSoporte);
    /**
     * Método encargado de eliminar las Novedades reportadas por un usuario y con un documentoSoporte específico. 
     * @param usuarioBD Secuencia del usuario que reportó las novedades.
     * @param documentoSoporte Texto referente al atributo documentoSoporte de las novedades
     * @return Retorna una lista de Novedades que cumplen con las condiciones dadas por los parametros.
     */
    public int reversarNovedades(BigInteger usuarioBD, String documentoSoporte);
    /**
     * Método encargado de traer las Novedades de un empleado que se registraron manualmente o por archivo plano
     * @param secuenciaEmpleado Secuencia del empleado al que se le buscan las novedades
     * @return Retorna una lista de Novedades
     */
    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado);
    
    /**
     * Método encargado de obtener una Novedad identificado con la secuencia enviada por parametro
     * @param secNovedad Secuencia de la Novedad a buscar
     * @return Retorna una Novedad identificada con la secuencia
     */
    public Novedades buscarNovedad(BigInteger secNovedad);
    
    public List<Novedades> novedadesConcepto(BigInteger secuenciaConcepto);
    
}
