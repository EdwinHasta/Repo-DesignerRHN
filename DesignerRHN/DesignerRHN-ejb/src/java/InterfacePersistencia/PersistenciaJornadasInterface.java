/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Jornadas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaJornadasInterface {

    public void crear(Jornadas jornadas);

    public void editar(Jornadas jornadas);

    public void borrar(Jornadas jornadas);

    public Jornadas consultarJornada(BigInteger secuencia);

    public List<Jornadas> consultarJornadas();

    public BigInteger contarTarifasEscalafonesJornada(BigInteger secuencia);

    public BigInteger contarJornadasLaboralesJornada(BigInteger secuencia);
}
