/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.NovedadesOperandos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaNovedadesOperandosInterface {
    
    public void crear(NovedadesOperandos novedadesOperandos);

    public void editar(NovedadesOperandos novedadesOperandos);

    public void borrar(NovedadesOperandos novedadesOperandos);

    public List<NovedadesOperandos> novedadesOperandos(BigInteger secuenciaOperando);
    
}
