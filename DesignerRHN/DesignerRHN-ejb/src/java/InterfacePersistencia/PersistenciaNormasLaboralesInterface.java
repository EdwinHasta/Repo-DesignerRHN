/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.NormasLaborales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaNormasLaboralesInterface {

    public void crear(NormasLaborales normasLaborales);

    public void editar(NormasLaborales normasLaborales);

    public void borrar(NormasLaborales normasLaborales);

    public NormasLaborales buscarNormaLaboral(BigInteger secuenciaNL);

    public List<NormasLaborales> buscarNormasLaborales();

    public Long verificarBorradoNormasLaborales(BigInteger secuencia);
}
