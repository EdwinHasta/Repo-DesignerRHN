/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.GruposFactoresRiesgos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarGruposFactoresRiesgosInterface {

    public void modificarGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos);

    public void borrarGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos);

    public void crearGruposFactoresRiesgos(List<GruposFactoresRiesgos> listaGruposFactoresRiesgos);

    public List<GruposFactoresRiesgos> consultarGruposFactoresRiesgos();

    public GruposFactoresRiesgos consultarGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos);

    public BigInteger contarFactoresRiesgoGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos);

    public BigInteger contarSoIndicadoresGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos);

    public BigInteger contarSoProActividadesGrupoFactorRiesgo(BigInteger secGruposFactoresRiesgos);
}
