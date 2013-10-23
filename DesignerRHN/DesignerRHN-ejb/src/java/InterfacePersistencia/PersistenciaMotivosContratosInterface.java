/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosContratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaMotivosContratosInterface {

    public void crear(MotivosContratos motivosContratos);

    public void editar(MotivosContratos motivosContratos);

    public void borrar(MotivosContratos motivosContratos);

    public MotivosContratos buscarMotivoContrato(BigInteger secuenciaMotivosContratos);

    public List<MotivosContratos> buscarMotivosContratos();

    public Long verificarBorradoVigenciasTiposContratos(BigInteger secuencia);

    public List<MotivosContratos> motivosContratos();
}
