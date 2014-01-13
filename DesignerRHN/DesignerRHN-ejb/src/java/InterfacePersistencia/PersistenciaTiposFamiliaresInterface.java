/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposFamiliares;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposFamiliaresInterface {

    public void crear(TiposFamiliares tiposFamiliares);

    public void editar(TiposFamiliares tiposFamiliares);

    public void borrar(TiposFamiliares tiposFamiliares);

    public TiposFamiliares buscarTiposFamiliares(BigInteger secuenciaTF);

    public List<TiposFamiliares> buscarTiposFamiliares();

    public BigInteger contadorHvReferencias(BigInteger secuencia);

}
