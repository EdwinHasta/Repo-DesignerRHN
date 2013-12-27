/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Lesiones;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaLesionesInterface {

    public void crear(Lesiones lesiones);

    public void editar(Lesiones lesiones);

    public void borrar(Lesiones lesiones);

    public Lesiones buscarlesion(BigInteger secuenciaL);

    public List<Lesiones> buscarlesiones();

    public BigDecimal contadorDetallesLicensias(BigInteger secuencia);

    public BigDecimal contadorSoAccidentesDomesticos(BigInteger secuencia);
}
