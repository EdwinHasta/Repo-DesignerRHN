/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarTiposTallasInterface;
import Entidades.TiposTallas;
import InterfacePersistencia.PersistenciaTiposTallasInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposTallas implements AdministrarTiposTallasInterface {

  @EJB
    PersistenciaTiposTallasInterface persistenciaTiposTallas;
    private TiposTallas tiposTallaSeleccionada;
    private TiposTallas tiposTallas;
    private List<TiposTallas> listTiposTallas;
    private BigDecimal verificadorElementos;
    private BigDecimal verificadorVigenciasTallas;

    public void modificarTiposTallas(List<TiposTallas> listTiposEmpresasModificadas) {
        for (int i = 0; i < listTiposEmpresasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tiposTallaSeleccionada = listTiposEmpresasModificadas.get(i);
            persistenciaTiposTallas.editar(tiposTallaSeleccionada);
        }
    }

    public void borrarTiposTallas(TiposTallas tiposTallas) {
        persistenciaTiposTallas.borrar(tiposTallas);
    }

    public void crearTiposTallas(TiposTallas tiposTallas) {
        persistenciaTiposTallas.crear(tiposTallas);
    }

    public List<TiposTallas> mostrarTiposTallas() {
        listTiposTallas = persistenciaTiposTallas.buscarTiposTallas();
        return listTiposTallas;
    }

    public TiposTallas mostrarTipoTalla(BigInteger secTipoEmpresa) {
        tiposTallas = persistenciaTiposTallas.buscarTipoTalla(secTipoEmpresa);
        return tiposTallas;
    }

    public BigDecimal verificarBorradoElementos(BigInteger secuenciaElementos) {
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificadorElementos = persistenciaTiposTallas.contadorElementos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposTallas verificarBorradoElementos ERROR :" + e);
        } finally {
            return verificadorElementos;
        }
    }
    public BigDecimal verificarBorradoVigenciasTallas(BigInteger secuenciaVigenciasTallas) {
        try {
            System.err.println("Secuencia Borrado Vigencias Tallas" + secuenciaVigenciasTallas);
            verificadorVigenciasTallas = persistenciaTiposTallas.contadorVigenciasTallas(secuenciaVigenciasTallas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposTallas verificarBorradoVigenciasTallas ERROR :" + e);
        } finally {
            return verificadorVigenciasTallas;
        }
    }
}
