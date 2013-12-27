/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposChequeos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaTiposChequeosInterface {

    public void crear(TiposChequeos tiposChequeos);

    public void editar(TiposChequeos tiposChequeos);

    public void borrar(TiposChequeos tiposChequeos);

    public TiposChequeos buscarTipoChequeo(BigInteger secuenciaTC);

    public List<TiposChequeos> buscarTiposChequeos();

    public BigInteger contadorChequeosMedicos(BigInteger secuencia);

    public BigInteger contadorTiposExamenesCargos(BigInteger secuencia);
}
