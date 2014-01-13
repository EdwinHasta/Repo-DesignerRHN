/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposAccidentesInterface {

    public void modificarTiposAccidentes(List<TiposAccidentes> listTiposAccidentesModificada);

    public void borrarTiposAccidentes(TiposAccidentes tiposAccidentes);

    public void crearTiposAccidentes(TiposAccidentes TiposAccidentes);

    public List<TiposAccidentes> mostrarTiposAccidentes();

    public TiposAccidentes mostrarTiposAccidentes(BigInteger secTiposAccidentes);

    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaTiposAccidentes);

    public BigInteger verificarBorradoAccidentes(BigInteger secuenciaTiposAccidentes);
}
