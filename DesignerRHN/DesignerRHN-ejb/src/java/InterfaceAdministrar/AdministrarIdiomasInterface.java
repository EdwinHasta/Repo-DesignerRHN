/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Idiomas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarIdiomasInterface {

    public void modificarIdiomas(List<Idiomas> listTiposIndicadoresModificadas);

    public void borrarIdiomas(Idiomas idiomas);

    public void crearIdiomas(Idiomas idiomas);

    public List<Idiomas> mostrarIdiomas();

    public Idiomas mostrarIdioma(BigInteger secIdiomas);

    public BigInteger verificarBorradoIdiomasPersonas(BigInteger secuenciaIdiomas);
}
