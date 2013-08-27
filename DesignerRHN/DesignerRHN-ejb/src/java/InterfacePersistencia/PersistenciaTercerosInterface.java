/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Terceros;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaTercerosInterface {

    /**
     * Crea un nuevo Terceros
     *
     * @param terceros Objeto a crear
     */
    public void crear(Terceros terceros);

    /**
     * Edita un Terceros
     *
     * @param terceros Objeto a editar
     */
    public void editar(Terceros terceros);

    /**
     * Borra un Terceros
     *
     * @param terceros Objeto a borrar
     */
    public void borrar(Terceros terceros);

    /**
     * Busca un Terceros por su llave primaria
     *
     * @param id Llave Primaria ID
     * @return Terceros que cumple con la llave primaria
     */
    public Terceros buscarTercero(Object id);

    /**
     * Busca la lista total de Terceros
     *
     * @return Lista de Terceros
     */
    public List<Terceros> buscarTerceros();

    /**
     * Obtiene un Terceros por la secuencia
     *
     * @param secuencia Secuencia Terceros
     * @return Terceros que cumple con la secuencia dad
     */
    public Terceros buscarTercerosSecuencia(BigInteger secuencia);

    public boolean verificarTerceroPorNit(BigInteger nit);

    public boolean verificarTerceroParaEmpresaEmpleado(BigInteger nit, BigInteger secEmpresa);
}
