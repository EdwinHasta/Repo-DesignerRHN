/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.ElementosCausasAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarElementosCausasAccidentesInterface {

    public void modificarElementosCausasAccidentes(List<ElementosCausasAccidentes> listElementosCausasAccidentes);

    public void borrarElementosCausasAccidentes(ElementosCausasAccidentes elementosCausasAccidentes);

    public void crearElementosCausasAccidentes(ElementosCausasAccidentes elementosCausasAccidentes);

    public List<ElementosCausasAccidentes> mostrarElementosCausasAccidentes();

    public ElementosCausasAccidentes mostrarElementoCausaAccidente(BigInteger secElementosCausasAccidentes);

    public BigInteger contadorSoAccidentes(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorSoIndicadoresFr(BigInteger secuenciaTiposAuxilios);
}
