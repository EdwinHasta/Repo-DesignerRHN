/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.GruposViaticos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarGruposViaticosInterface {

    public void modificarGruposViaticos(List<GruposViaticos> listGruposViativosModificadas);

    public void borrarGruposViaticos(GruposViaticos gruposViaticos);

    public void crearGruposViaticos(GruposViaticos gruposViaticos);

    public List<GruposViaticos> mostrarGruposViaticos();

    public GruposViaticos mostrarGrupoViatico(BigInteger secGruposViaticos);

    public BigInteger verificarBorradoCargos(BigInteger secuenciaCargos);

    public BigInteger verificarBorradoPlantas(BigInteger secuenciaCargos);

    public BigInteger verificarTablasViaticos(BigInteger secuenciaCargos);

    public BigInteger verificarEersViaticos(BigInteger secuenciaCargos);
}
