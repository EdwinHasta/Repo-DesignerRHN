/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposIndices;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposIndicesInterface {

    public void crear(TiposIndices tiposIndices);

    public void editar(TiposIndices tiposIndices);

    public void borrar(TiposIndices tiposIndices);

    public List<TiposIndices> consultarTiposIndices();

    public TiposIndices consultarTipoIndice(BigInteger secuencia);

    public BigInteger contarIndicesTipoIndice(BigInteger secuencia);
}
