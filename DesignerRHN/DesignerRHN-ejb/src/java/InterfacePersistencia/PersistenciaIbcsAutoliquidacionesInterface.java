/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.IbcsAutoliquidaciones;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaIbcsAutoliquidacionesInterface {

    public void crear(IbcsAutoliquidaciones autoliquidaciones);

    public void editar(IbcsAutoliquidaciones autoliquidaciones);

    public void borrar(IbcsAutoliquidaciones autoliquidaciones);

    public IbcsAutoliquidaciones buscarIbcAutoliquidacion(Object id);

    public List<IbcsAutoliquidaciones> buscarIbcsAutoliquidaciones();

    public IbcsAutoliquidaciones buscarIbcAutoliquidacionSecuencia(BigInteger secuencia);

    public List<IbcsAutoliquidaciones> buscarIbcsAutoliquidacionesTipoEntidadEmpleado(BigInteger secuenciaTE, BigInteger secuenciaEmpl);
}
