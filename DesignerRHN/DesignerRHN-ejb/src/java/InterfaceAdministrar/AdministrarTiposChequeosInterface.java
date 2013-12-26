/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposChequeos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarTiposChequeosInterface {

    public void modificarTiposChequeos(List<TiposChequeos> listTiposChequeosModificadas);

    public void borrarTiposChequeos(TiposChequeos tiposChequeos);

    public void crearTiposChequeos(TiposChequeos tiposChequeos);

    public List<TiposChequeos> mostrarTiposChequeos();

    public TiposChequeos mostrarTipoChequeo(BigInteger secTipoEmpresa);

    public BigInteger verificarChequeosMedicos(BigInteger secuenciaJuzgados);

    public BigInteger verificarTiposExamenesCargos(BigInteger secuenciaJuzgados);
}
