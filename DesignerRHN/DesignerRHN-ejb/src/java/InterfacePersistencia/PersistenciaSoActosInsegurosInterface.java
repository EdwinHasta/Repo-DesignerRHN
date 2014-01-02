/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SoActosInseguros;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author John Pineda
 */
@Local
public interface PersistenciaSoActosInsegurosInterface {

    public void crear(SoActosInseguros SoActosInseguros);

    public void editar(SoActosInseguros SoActosInseguros);

    public void borrar(SoActosInseguros SoActosInseguros);

    public SoActosInseguros buscarSoActoInseguro(BigInteger secuenciaSCAP);

    public List<SoActosInseguros> buscarSoActosInseguros();

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia);
}
