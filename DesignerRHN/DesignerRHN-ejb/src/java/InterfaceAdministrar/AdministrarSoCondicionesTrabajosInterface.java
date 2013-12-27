/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.SoCondicionesTrabajos;
import java.math.BigDecimal;
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

    public BigDecimal verificarInspecciones(BigInteger secuenciaElementos);

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementos);

    public BigDecimal verificarSoDetallesPanoramas(BigInteger secuenciaElementos);

    public BigDecimal verificarSoExposicionesFr(BigInteger secuenciaElementos);
}
