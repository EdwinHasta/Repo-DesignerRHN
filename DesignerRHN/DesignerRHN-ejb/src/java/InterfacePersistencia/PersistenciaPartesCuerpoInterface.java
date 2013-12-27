/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.PartesCuerpo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaPartesCuerpoInterface {

    public void crear(PartesCuerpo partesCuerpo);

    public void editar(PartesCuerpo partesCuerpo);

    public void borrar(PartesCuerpo partesCuerpo);

    public PartesCuerpo buscarParteCuerpo(BigInteger secuenciaPC);

    public List<PartesCuerpo> buscarPartesCuerpo();

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia);

    public BigDecimal contadorDetallesExamenes(BigInteger secuencia);

    public BigDecimal contadorSoDetallesRevisiones(BigInteger secuencia);
}
