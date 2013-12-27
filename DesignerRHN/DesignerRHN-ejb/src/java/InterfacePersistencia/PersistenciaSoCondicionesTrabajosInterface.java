/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

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
public interface PersistenciaSoCondicionesTrabajosInterface {

    public void crear(SoCondicionesTrabajos soCondicionesTrabajos);

    public void editar(SoCondicionesTrabajos soCondicionesTrabajos);

    public void borrar(SoCondicionesTrabajos soCondicionesTrabajos);

    public SoCondicionesTrabajos buscarSoCondicionTrabajo(BigInteger secuencia);

    public List<SoCondicionesTrabajos> buscarSoCondicionesTrabajos();

    public BigDecimal contadorInspecciones(BigInteger secuencia);

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia);

    public BigDecimal contadorSoDetallesPanoramas(BigInteger secuencia);

    public BigDecimal contadorSoExposicionesFr(BigInteger secuencia);
}
