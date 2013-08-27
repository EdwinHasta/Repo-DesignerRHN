/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasContratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVigenciasContratosInterface {
    
    /**
     * Crea un objeto VigenciasContratos
     * @param vigenciasContratos Objeto a crear
     */
    public void crear(VigenciasContratos vigenciasContratos);
    /**
     * Edita un objeto VigenciasContratos
     * @param vigenciasContratos Objeto a editar
     */
    public void editar(VigenciasContratos vigenciasContratos);
    /**
     * Borra un objeto VigenciasContratos
     * @param vigenciasContratos Objeto a borrar
     */
    public void borrar(VigenciasContratos vigenciasContratos);
    /**
     * Metodo que obtiene una VigenciaContratos por la llave primaria ID
     * @param id Llave Primaria ID
     * @return vigenciaC Vigencia Contratos que cumple con la llave primaria
     */
    public VigenciasContratos buscarVigenciaContrato(Object id);
    /**
     * Metodo que obtiene todos los elementos de VigenciasContratos
     * @return listVC Lista de Vigencias Contratos
     */
    public List<VigenciasContratos> buscarVigenciasContratos();
    /**
     * Metodo que obtiene las Vigencias Contratos de un Empleado
     * @param secEmpleado Secuencia del Empleado
     * @return listVCE Lista de Vigencias Contratos del Empleado dado por secuencia
     */
    public List<VigenciasContratos> buscarVigenciaContratoEmpleado(BigInteger secEmpleado);
    /**
     * Metodo que obtiene una VigenciaContratos por medio de la secuencia
     * @param secVC Secuencia de la VigenciaContratos
     * @return vigenciaC VigenciaContratos que cumple con la secuencia dada
     */
    public VigenciasContratos buscarVigenciaContratoSecuencia(BigInteger secVC);
    
}
