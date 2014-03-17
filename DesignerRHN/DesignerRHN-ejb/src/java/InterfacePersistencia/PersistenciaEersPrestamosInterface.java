/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EersPrestamos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaEersPrestamosInterface {

    public void crear(EersPrestamos eersPrestamos);   

    public void editar(EersPrestamos eersPrestamos);

    public void borrar(EersPrestamos eersPrestamos);

    public List<EersPrestamos> eersPrestamosEmpleado(BigInteger secuenciaEmpleado);
}
