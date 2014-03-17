/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.VWPrestamoDtosRealizados;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaVWPrestamoDtosRealizadosInterface {
    
    public List<VWPrestamoDtosRealizados> buscarPrestamosDtos(BigInteger secuencia);
    
}
