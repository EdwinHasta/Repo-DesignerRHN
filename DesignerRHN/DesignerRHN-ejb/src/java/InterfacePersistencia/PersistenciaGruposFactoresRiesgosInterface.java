/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.GruposFactoresRiesgos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaGruposFactoresRiesgosInterface {
public void crear(GruposFactoresRiesgos grupoFactoresRiesgos);
    public void editar(GruposFactoresRiesgos grupoFactoresRiesgos);
    public void borrar(GruposFactoresRiesgos grupoFactoresRiesgos);
    public List<GruposFactoresRiesgos> consultarGruposFactoresRiesgos();
    public GruposFactoresRiesgos consultarGrupoFactorRiesgo(BigInteger secuencia);
    public BigInteger contarSoProActividadesGrupoFactorRiesgo(BigInteger secuencia) ;
    public BigInteger contarSoIndicadoresGrupoFactorRiesgo(BigInteger secuencia);
    public BigInteger contarFactoresRiesgoGrupoFactorRiesgo(BigInteger secuencia);
}
