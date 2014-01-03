/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.SoCondicionesTrabajos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarSoCondicionesTrabajosInterface {

    public void modificarSoCondicionesTrabajos(List<SoCondicionesTrabajos> listTiposEntidadesModificadas);

    public void borrarSoCondicionesTrabajos(SoCondicionesTrabajos tipoCentroCosto);

    public void crearSoCondicionesTrabajos(SoCondicionesTrabajos tiposCentrosCostos);

    public void buscarSoCondicionesTrabajos(SoCondicionesTrabajos tiposCentrosCostos);

    public List<SoCondicionesTrabajos> mostrarSoCondicionesTrabajos();

    public SoCondicionesTrabajos mostrarSoCondicionTrabajo(BigInteger secSoCondicionesTrabajos);

    public BigInteger verificarInspecciones(BigInteger secuenciaElementos);

    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos);

    public BigInteger verificarSoDetallesPanoramas(BigInteger secuenciaElementos);

    public BigInteger verificarSoExposicionesFr(BigInteger secuenciaElementos);
}
