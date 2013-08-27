/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Retirados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaRetiradosInterface {
    
    /**
     * Crea un objeto Retirados
     * @param retirados Objeto a crear
     */
    public void crear(Retirados retirados);
    /**
     * Edita un objeto Retirados
     * @param retirados Objeto a editar
     */
    public void editar(Retirados retirados);
    /**
     * Borra un objeto Retirados
     * @param retirados Objeto a editar
     */
    public void borrar(Retirados retirados);
    /**
     * Metodo que obtiene un Retirado por la llave primaria ID
     * @param id Llave primaria ID
     * @return Retirado que cumple con la llave primaria ID
     */
    public Retirados buscarRetirado(Object id);
    /**
     * Metodo que obtiene todos los Retirados de la tabla
     * @return listR Lista de Retirados
     */
    public List<Retirados> buscarRetirados();
    /**
     * Metodo que busca los Retiros por la secuencia del Empleado
     * @param secEmpleado Secuencia de empleado
     * @return retiroE Lista de Retiros del Empleado
     */
    public List<Retirados> buscarRetirosEmpleado(BigInteger secEmpleado);
    /**
     * Metodo que obtiene un Retiro por su secuencia
     * @param secR Secuencia del Retiro
     * @return retiro Retiro que cumple con la condicion de la secuencia
     */
    public Retirados buscarRetiroSecuencia(BigInteger secR);
    /**
     * Metodo que busca un Retiro por la secuencia de una Vigencia
     * @param secVigencia Secuencia de la Vigencia
     * @return retV Retiro dado por la secuencia de la Vigencia
     */
    public Retirados buscarRetiroVigenciaSecuencia(BigInteger secVigencia);
    
}
