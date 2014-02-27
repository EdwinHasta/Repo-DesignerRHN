/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.DependenciasOperandos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaDependenciasOperandosInterface {
    
    public void crear(DependenciasOperandos dependenciasOperandos);

    public void editar(DependenciasOperandos dependenciasOperandos);

    public void borrar(DependenciasOperandos dependenciasOperandos);

    public List<DependenciasOperandos> dependenciasOperandos(BigInteger secuenciaOperando);
    
    public String nombreOperandos(int codigo);
    
}
