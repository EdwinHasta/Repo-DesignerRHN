/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosPrestamos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaMotivosPrestamosInterface {

    public void crear(MotivosPrestamos motivosPrestamos);

    public void editar(MotivosPrestamos motivosPrestamos);

    public void borrar(MotivosPrestamos motivosPrestamos);

    public MotivosPrestamos buscarMotivoPrestamo(BigInteger secuenciaMP);

    public List<MotivosPrestamos> buscarMotivosPrestamos();

    public BigInteger contadorEersPrestamos(BigInteger secuencia);
}
