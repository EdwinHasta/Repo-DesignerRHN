/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import javax.ejb.Local;
import Entidades.MotivosEmbargos;
import java.math.BigInteger;
import java.util.List;
/**
 *
 * @author user
 */
@Local
public interface PersistenciaMotivosEmbargosInterface {

    public void crear(MotivosEmbargos motivosEmbargos);

    public void editar(MotivosEmbargos motivosEmbargos);

    public void borrar(MotivosEmbargos motivosEmbargos);

    public MotivosEmbargos buscarMotivoEmbargo(BigInteger secuenciaME);

    public List<MotivosEmbargos> buscarMotivosEmbargos();

    public BigInteger contadorEersPrestamos(BigInteger secuencia);

    public BigInteger contadorEmbargos(BigInteger secuencia);
}
