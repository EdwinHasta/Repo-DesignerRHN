/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosCambiosSueldos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaMotivosCambiosSueldosInterface {
    
    /**
     * Crea una nueva MotivosCambiosSueldos
     * @param motivosCambiosSueldos Objeto a crear
     */
    public void crear(MotivosCambiosSueldos motivosCambiosSueldos);
    /**
     * Edita un MotivosCambiosSueldos
     * @param motivosCambiosSueldos Objeto a editar
     */
    public void editar(MotivosCambiosSueldos motivosCambiosSueldos);
    /**
     * Borra un MotivosCambiosSueldos
     * @param motivosCambiosSueldos Objeto a borrar
     */
    public void borrar(MotivosCambiosSueldos motivosCambiosSueldos);
    /**
     * Obtiene un MotivosCambiosSueldos por la llave primaria ID
     * @param id Lave Primaria Id
     * @return MotivosCambiosSueldos que cumple con la llave primaria
     */
    public MotivosCambiosSueldos buscarMotivoCambioSueldo(Object id);
    /**
     * Obtiene la lista de la tabla MotivosCambiosSueldos
     * @return Lista de MotivosCambiosSueldos
     */
    public List<MotivosCambiosSueldos> buscarMotivosCambiosSueldos();
    /**
     * Obtiene un MotivosCambiosSueldos por su secuencia
     * @param secuencia Secuencia MotivosCambiosSueldos
     * @return MotivosCambiosSueldos que cumple con la Secuencia
     */
    public MotivosCambiosSueldos buscarMotivoCambioSueldoSecuencia(BigInteger secuencia);

    public Long verificarBorradoVigenciasSueldos(BigInteger secuencia);
}
