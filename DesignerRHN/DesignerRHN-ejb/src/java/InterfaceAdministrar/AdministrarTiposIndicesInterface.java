/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposIndices;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposIndicesInterface {

    public void modificarTiposIndices(List<TiposIndices> listaTiposIndices);

    public void borrarTiposIndices(List<TiposIndices> listaTiposIndices);

    public void crearTiposIndices(List<TiposIndices> listaTiposIndices);

    public List<TiposIndices> consultarTiposIndices();

    public TiposIndices consultarTipoIndice(BigInteger secTiposIndices);

    public BigInteger contarIndicesTipoIndice(BigInteger secTiposIndices);
}
