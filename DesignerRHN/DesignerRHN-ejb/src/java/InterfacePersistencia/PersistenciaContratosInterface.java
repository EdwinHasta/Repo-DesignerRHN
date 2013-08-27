/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Contratos;
import java.math.BigInteger;
import java.util.List;

/**
 * Persistencia Contratos
 * @author AndresPineda
 */
public interface PersistenciaContratosInterface {
    
    /**
     * Crea un objeto de tipo Contratos
     * @param contratos Objeto a crear
     */
    public void crear(Contratos contratos);
    /**
     * Edita un objeto de tipo Contratos
     * @param contratos Objeto a editar
     */
    public void editar(Contratos contratos);
    /**
     * Borrar un objeto de tipo Contratos
     * @param contratos Objeto a borrar
     */
    public void borrar(Contratos contratos);
    /**
     * Busca un objeto de tipo Contratos por medio de su ID
     * @param id Llave Primaria ID
     * @return contrato Objeto Contratos que cumple con la llave primaria ID
     */
    public Contratos buscarContrato(Object id);
    /**
     * Metodo que busca todos los elementos de la tabla Contratos
     * @return Lista de Contratos en la tabla
     */
    public List<Contratos> buscarContratos();
    /**
     * Metodo que obtiene un objeto Contratos por su secuencia
     * @param secuencia Secuencia de Contrato a buscar
     * @return Objeto Contrato que cumple con la llave primaria dada
     */
    public Contratos buscarContratoSecuencia(BigInteger secuencia);
    
}
