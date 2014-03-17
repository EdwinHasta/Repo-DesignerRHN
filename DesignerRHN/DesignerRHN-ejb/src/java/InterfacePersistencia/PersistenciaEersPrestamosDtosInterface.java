/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EersPrestamosDtos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaEersPrestamosDtosInterface {

    public void crear(EersPrestamosDtos eersPrestamosDtos);

    public void editar(EersPrestamosDtos eersPrestamosDtos);
    
    public void borrar(EersPrestamosDtos eersPrestamosDtos);
    
    public List<EersPrestamosDtos> eersPrestamosDtosEmpleado(BigInteger secuenciaEersPrestamo);

}
