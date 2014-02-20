/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.TiposConstantes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTiposConstantesInterface {
    
    public List<TiposConstantes> buscarTiposConstantes(BigInteger secuenciaOperando, String tipoOperando);

    public void borrarTiposConstantes(TiposConstantes tiposConstantes);

    public void crearTiposConstantes(TiposConstantes tiposConstantes);
      
    public void modificarTiposConstantes(TiposConstantes tiposConstantes);
    
}
