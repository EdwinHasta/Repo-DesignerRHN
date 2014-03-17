/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Festivos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaFestivosInterface {

    public void crear(Festivos festivos);

    public void editar(Festivos festivos);

    public void borrar(Festivos festivos);

    public List<Festivos> consultarFestivosPais(BigInteger secPais);

    public Festivos consultarPais(BigInteger secPais);
}
