/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Festivos;
import Entidades.Paises;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarFestivosInterface {

    public void modificarFestivos(List<Festivos> listaFestivos);

    public void borrarFestivos(List<Festivos> listaFestivos);

    public void crearFestivos(List<Festivos> listaFestivos);

    public List<Festivos> consultarFestivosPais(BigInteger secPais);

    public List<Paises> consultarLOVPaises();
}
