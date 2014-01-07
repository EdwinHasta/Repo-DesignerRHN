/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.EstadosCiviles;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEstadosCivilesInterface {

    public void modificarEstadosCiviles(List<EstadosCiviles> listDeportesModificadas);

    public void borrarEstadosCiviles(EstadosCiviles deportes);

    public void crearEstadosCiviles(EstadosCiviles deportes);

    public List<EstadosCiviles> mostrarEstadosCiviles();

    public BigInteger verificarBorradoVigenciasEstadoCiviles(BigInteger secuenciaEstadosCiviles);
}
