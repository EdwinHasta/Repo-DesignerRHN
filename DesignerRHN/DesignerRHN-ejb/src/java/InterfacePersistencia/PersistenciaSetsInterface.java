/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Sets;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPimeda
 */
public interface PersistenciaSetsInterface {
    
    /**
     * Crea un objeto Set
     * @param sets Objeto a crear
     */
    public void crear(Sets sets);
    /**
     * Editar un objeto Set
     * @param sets Objeto a editar
     */
    public void editar(Sets sets);
    /**
     * Borra un objeto Set
     * @param sets Objeto a borrar
     */
    public void borrar(Sets sets);
    /**
     * Metodo que obtiene un Set por la llave primaria ID
     * @param id Llave Primaria Id
     * @return set Set que cumple con la condicion de la llave primaria
     */
    public Sets buscarSets(Object id);
    /**
     * Metodo que obtiene todos los elementos de la tabla Set
     * @return listS Lista de Sets
     */
    public List<Sets> buscarSets();
    /**
     * Metodo que obtiene un Set por la secuencia
     * @param secuencia Secuencia de Set a buscar
     * @return set Set que cumple con la condicion de la secuencia
     */
    public Sets buscarSetSecuencia(BigInteger secuencia);
    /**
     * Metodo que obtiene los Sets de un Empleado
     * @param secEmpleado Secuencia del Empleado
     * @return setE Sets del Empleado que cumple con la condicion de la secuencia
     */
    public List<Sets> buscarSetsEmpleado(BigInteger secEmpleado);
    
}
