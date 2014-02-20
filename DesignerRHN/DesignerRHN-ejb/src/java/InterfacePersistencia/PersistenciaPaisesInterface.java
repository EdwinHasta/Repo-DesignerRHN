/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Paises;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaPaisesInterface {

    public void crear(Paises tiposAusentismos);

    public void editar(Paises tiposAusentismos);

    public void borrar(Paises tiposAusentismos);

    public List<Paises> consultarPaises();

    public Paises consultarPais(BigInteger secClaseCategoria);

    public BigInteger contarDepartamentosPais(BigInteger secuencia);

    public BigInteger contarFestivosPais(BigInteger secuencia);
}
