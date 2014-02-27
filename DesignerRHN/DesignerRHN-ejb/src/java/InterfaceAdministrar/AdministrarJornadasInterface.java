/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Jornadas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarJornadasInterface {

    public void modificarJornadas(List<Jornadas> listaJornadas);

    public void borrarJornadas(List<Jornadas> listaJornadas);

    public void crearJornadas(List<Jornadas> listaJornadas);

    public List<Jornadas> consultarJornadas();

    public Jornadas consultarJornada(BigInteger secJornadas);

    public BigInteger contarJornadasLaboralesJornada(BigInteger secJornadas);

    public BigInteger contarTarifasEscalafonesJornada(BigInteger secJornadas);
}
