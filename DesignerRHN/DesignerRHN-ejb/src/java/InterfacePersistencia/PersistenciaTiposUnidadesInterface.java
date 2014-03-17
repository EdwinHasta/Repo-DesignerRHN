/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposUnidades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposUnidadesInterface {

    public void crear(TiposUnidades tiposUnidades);

    public void editar(TiposUnidades tiposUnidades);

    public void borrar(TiposUnidades tiposUnidades);

    public List<TiposUnidades> consultarTiposUnidades();

    public TiposUnidades consultarTipoUnidad(BigInteger secuencia);

    public BigInteger contarUnidadesTipoUnidad(BigInteger secuencia);
}
