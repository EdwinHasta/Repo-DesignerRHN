/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposConstantes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposConstantesInterface {

    public void crear(TiposConstantes tiposConstantes);

    public void editar(TiposConstantes tiposConstantes);

    public void borrar(TiposConstantes tiposConstantes);

    public List<TiposConstantes> tiposConstantes(BigInteger secuenciaOperando, String tipo);
    
}