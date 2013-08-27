/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.CentrosCostos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaCentrosCostosInterface {
    /**
     * Crea un nuevo CentroCosto
     * @param centrosCostos Objeto a crear
     */
    public void crear(CentrosCostos centrosCostos);
    /**
     * Edita un CentroCosto
     * @param centrosCostos Objeto a editar
     */
    public void editar(CentrosCostos centrosCostos);
    /**
     * Borra un CentroCosto
     * @param centrosCostos Objeto a borrar
     */
    public void borrar(CentrosCostos centrosCostos);
    /**
     * Obtiene un CentroCosto por el valor de la llave primaria ID
     * @param id Llave Primaria ID
     * @return cC Centro Costo que cumple con la llave primaria ID
     */
    public CentrosCostos buscarCentroCosto(Object id);
    /**
     * Obtiene el total de elementos de CentrosCostos
     * @return listCC Lista de Centros Costos
     */
    public List<CentrosCostos> buscarCentrosCostos();
    /**
     * Obtiene un CentroCosto por medio de la secuencia
     * @param secuencia Secuencia CentroCosto
     * @return cC Centro Costo que cumple con la secuencia
     */
    public CentrosCostos buscarCentroCostoSecuencia(BigInteger secuencia);
    
}
