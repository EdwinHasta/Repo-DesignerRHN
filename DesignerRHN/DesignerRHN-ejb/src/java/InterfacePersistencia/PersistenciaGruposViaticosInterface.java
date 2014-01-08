/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposViaticos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaGruposViaticosInterface {

    public void crear(GruposViaticos gruposViaticos);

    public void editar(GruposViaticos gruposViaticos);

    public void borrar(GruposViaticos gruposViaticos);

    public GruposViaticos buscarGrupoViatico(BigInteger secuenciaGV);

    public List<GruposViaticos> buscarGruposViaticos();

    public BigInteger contadorCargos(BigInteger secuencia);

    public BigInteger contadorPlantas(BigInteger secuencia);

    public BigInteger contadorTablasViaticos(BigInteger secuencia);

    public BigInteger contadorEersviaticos(BigInteger secuencia);
}
