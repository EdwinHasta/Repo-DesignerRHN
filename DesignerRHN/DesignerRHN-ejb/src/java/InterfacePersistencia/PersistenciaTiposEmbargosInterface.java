/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposEmbargosInterface {

    public void crear(TiposEmbargos tiposEmbargos);

    public void editar(TiposEmbargos tiposEmbargos);

    public void borrar(TiposEmbargos tiposEmbargos);

    public TiposEmbargos buscarTipoEmbargo(BigInteger secuenciaTE);

    public List<TiposEmbargos> buscarTiposEmbargos();

    public BigInteger contadorEerPrestamos(BigInteger secuencia);

    public BigInteger contadorFormasDtos(BigInteger secuencia);
}
