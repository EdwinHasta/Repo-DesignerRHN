/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.TiposFunciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposFuncionesInterface {
    
    public List<TiposFunciones> tiposFunciones(BigInteger secuenciaOperando, String tipo);
    
    public void crear(TiposFunciones tiposFunciones);

    public void editar(TiposFunciones tiposFunciones);
    
    public void borrar(TiposFunciones tiposFunciones);
    
}
