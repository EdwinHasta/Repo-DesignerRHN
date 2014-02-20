/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposBloques;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTiposBloquesInterface {

    public List<TiposBloques> buscarTiposBloques(BigInteger secuenciaOperando, String tipoOperando);

    public void borrarTiposBloques(TiposBloques tiposConstantes);

    public void crearTiposBloques(TiposBloques tiposConstantes);

    public void modificarTiposBloques(TiposBloques tiposConstantes);

}
