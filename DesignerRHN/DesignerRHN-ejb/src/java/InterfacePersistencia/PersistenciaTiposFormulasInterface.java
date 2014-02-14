/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposFormulas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposFormulasInterface {

    public void crear(TiposFormulas tiposFormulas);

    public void editar(TiposFormulas tiposFormulas);

    public void borrar(TiposFormulas tiposFormulas);

    public List<TiposFormulas> tiposFormulas(BigInteger secuenciaOperando, String tipo);
    
}
