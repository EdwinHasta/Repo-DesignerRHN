/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.DependenciasOperandos;
import Entidades.Operandos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarDependenciasOperandosInterface {
    
    public List<DependenciasOperandos> buscarDependenciasOperandos(BigInteger secuenciaOperando);

    public void borrarDependenciasOperandos(DependenciasOperandos dependenciasOperandos);

    public void crearDependenciasOperandos(DependenciasOperandos dependenciasOperandos);

    public void modificarDependenciasOperandos(DependenciasOperandos dependenciasOperandos);
    
    public List<Operandos> buscarOperandos();
    
    public String nombreOperandos(int codigo);
    
}
