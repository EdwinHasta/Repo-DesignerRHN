/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposAccidentesInterface {

    public void crear(TiposAccidentes tiposAccidentes);

    public void editar(TiposAccidentes tiposAccidentes);

    public void borrar(TiposAccidentes tiposAccidentes);

    public TiposAccidentes buscarTipoAccidente(BigInteger secuenciaTA);

    public List<TiposAccidentes> buscarTiposAccidentes();

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia);

    public BigInteger contadorAccidentes(BigInteger secuencia);
}
