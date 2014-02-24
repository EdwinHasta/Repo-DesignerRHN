/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposBloques;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposBloquesInterface {

    public void crear(TiposBloques tiposBloques);

    public void editar(TiposBloques tiposBloques);

    public void borrar(TiposBloques tiposBloques);

    public List<TiposBloques> tiposBloques(BigInteger secuenciaOperando, String tipo);
    
}