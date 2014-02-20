/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Formulas;
import Entidades.TiposFormulas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTiposFormulasInterface {

    public List<TiposFormulas> buscarTiposFormulas(BigInteger secuenciaOperando, String tipoOperando);

    public void borrarTiposFormulas(TiposFormulas tiposFormulas);

    public void crearTiposFormulas(TiposFormulas tiposFormulas);
      
    public void modificarTiposFormulas(TiposFormulas tiposFormulas);
    
    public List<Formulas> lovFormulas();
    
}
