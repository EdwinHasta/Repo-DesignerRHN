/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaHvReferencias1Interface {

    public void crear(HvReferencias hvReferencias);

    public void editar(HvReferencias tiposExamenes);

    public void borrar(HvReferencias hvReferencias);

    public HvReferencias buscarHvReferencia1(BigInteger secuenciaHvReferencias);

    public List<HvReferencias> buscarHvReferencias1();

    public List<HvReferencias> buscarHvReferenciasPorEmpleado1(BigInteger secEmpleado);

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado);
}
