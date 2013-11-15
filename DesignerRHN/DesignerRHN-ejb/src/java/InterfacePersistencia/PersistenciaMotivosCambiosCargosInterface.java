/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosCambiosCargos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaMotivosCambiosCargosInterface {
    
    /*
     * Metodo encargado de crear un nuevo motivoCambioCargo en la 
     * base de datos
     */
    public void crear(MotivosCambiosCargos motivoCambioCargo);
    /*
     * Metodo encargado de modificar un motivoCambioCargo de la 
     * base de datos
     */
    public void editar(MotivosCambiosCargos motivoCambioCargo);
    /*
     * Metodo encargado de borrar un motivoCambioCargo de la 
     * base de datos
     */
    public void borrar(MotivosCambiosCargos motivoCambioCargo);
    /*
     * Metodo encargado de buscar un motivoCambioCargo segun su secuencia
     * en la base de datos
     */
    public MotivosCambiosCargos buscarMotivoCambioCargo(BigInteger secuencia);
    /*
     * Metodo encargado de buscar todos los motivosCambiosCargos exitentes
     * en la base de datos. Los retorna en forma de lista
     */
    public List<MotivosCambiosCargos> buscarMotivosCambiosCargos(); 
    /*
     * Metodo encargado de buscar todos los nombres de los motivosCambiosCargos
     * existentes en la base de datos
     */
    public List<String> buscarNombresMotivosCambiosCargos();

    public Long verificarBorradoVigenciasCargos(BigInteger secuencia);
}
