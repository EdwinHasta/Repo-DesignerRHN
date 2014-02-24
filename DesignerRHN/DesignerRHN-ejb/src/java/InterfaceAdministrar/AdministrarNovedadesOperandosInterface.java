/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.NovedadesOperandos;
import Entidades.Operandos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarNovedadesOperandosInterface {
    
    public List<NovedadesOperandos> buscarNovedadesOperandos(BigInteger secuenciaOperando);

    public void borrarNovedadesOperandos(NovedadesOperandos novedadesOperandos);

    public void crearNovedadesOperandos(NovedadesOperandos novedadesOperandos);
      
    public void modificarNovedadesOperandos(NovedadesOperandos novedadesOperandos);
    
    public List<Operandos> buscarOperandos();
        
}
