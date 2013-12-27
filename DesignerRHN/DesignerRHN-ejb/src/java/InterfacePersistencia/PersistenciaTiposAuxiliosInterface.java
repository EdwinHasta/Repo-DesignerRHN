/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposAuxilios;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposAuxiliosInterface {

    public void crear(TiposAuxilios tiposAuxilios);

    public void editar(TiposAuxilios tiposAuxilios);

    public void borrar(TiposAuxilios tiposAuxilios);

    public TiposAuxilios buscarTipoAuxilio(BigInteger secuenciaME);

    public List<TiposAuxilios> buscarTiposAuxilios();

    public BigInteger contadorTablasAuxilios(BigInteger secuencia);
}
