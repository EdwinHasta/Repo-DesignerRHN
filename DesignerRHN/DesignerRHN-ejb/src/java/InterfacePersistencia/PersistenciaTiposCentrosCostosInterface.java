/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposCentrosCostos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposCentrosCostosInterface {

    /**
     * crear tipoCentrosCostos
     */
    public void crear(TiposCentrosCostos tiposCentrosCostos);

    /**
     * Editar tipoCentrosCostos
     */
    public void editar(TiposCentrosCostos tiposCentrosCostos);

    /**
     * borrar tipoCentrosCostos
     */
    public void borrar(TiposCentrosCostos tiposCentrosCostos);

    /**
     * buscar tipoCentrosCostos por secuenta
     */
    public TiposCentrosCostos buscarTipoCentrosCostos(BigInteger secuenciaTCC);

    /**
     * mostrar todos los tiposCentrosCostos
     */
    public List<TiposCentrosCostos> buscarTiposCentrosCostos();

    public Long verificarBorradoCentrosCostos(BigInteger secuencia);

    public Long verificarBorradoVigenciasCuentas(BigInteger secuencia);

    public Long verificarBorradoRiesgosProfesionales(BigInteger secuencia);
}
