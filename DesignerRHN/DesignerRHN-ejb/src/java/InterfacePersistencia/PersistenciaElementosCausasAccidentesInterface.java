/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ElementosCausasAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaElementosCausasAccidentesInterface {

    public void crear(ElementosCausasAccidentes elementosCausasAccidentes);

    public void editar(ElementosCausasAccidentes elementosCausasAccidentes);

    public void borrar(ElementosCausasAccidentes elementosCausasAccidentes);

    public ElementosCausasAccidentes buscarElementoCausaAccidente(BigInteger secuenciaECA);

    public List<ElementosCausasAccidentes> buscarElementosCausasAccidentes();

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia);

    public BigInteger contadorSoAccidentes(BigInteger secuencia);

    public BigInteger contadorSoIndicadoresFr(BigInteger secuencia);
}
