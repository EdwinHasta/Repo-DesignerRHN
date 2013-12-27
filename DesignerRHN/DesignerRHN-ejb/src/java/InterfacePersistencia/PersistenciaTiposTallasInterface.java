/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposTallas;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposTallasInterface {

    public void crear(TiposTallas tiposTallas);

    public void editar(TiposTallas tiposTallas);

    public void borrar(TiposTallas tiposTallas);

    public TiposTallas buscarTipoTalla(BigInteger secuenciaTT);

    public List<TiposTallas> buscarTiposTallas();

    public BigDecimal contadorElementos(BigInteger secuencia);

    public BigDecimal contadorVigenciasTallas(BigInteger secuencia);
}
