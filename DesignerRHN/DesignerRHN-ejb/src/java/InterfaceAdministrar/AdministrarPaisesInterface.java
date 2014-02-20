/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Paises;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarPaisesInterface {

    public void modificarPaises(List<Paises> listaPaises);

    public void borrarPaises(List<Paises> listaPaises);

    public void crearPaises(List<Paises> listaPaises);

    public List<Paises> consultarPaises();

    public Paises consultarPais(BigInteger secPaises);

    public BigInteger contarDepartamentosPais(BigInteger secPaises);

    public BigInteger contarFestivosPais(BigInteger secPaises);
}
