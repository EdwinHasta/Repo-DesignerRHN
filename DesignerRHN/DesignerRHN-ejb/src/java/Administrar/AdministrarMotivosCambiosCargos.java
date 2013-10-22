/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosCambiosCargos;
import InterfaceAdministrar.AdministrarMotivosCambiosCargosInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Administrator
 */
@Stateless
public class AdministrarMotivosCambiosCargos implements AdministrarMotivosCambiosCargosInterface {

    
    @EJB
    PersistenciaMotivosCambiosCargosInterface persistenciaMotivosCambiosCargos;
    
    private List<MotivosCambiosCargos> motivosCambiosCargos;
    private List<String> nombresMotivosCambiosCargos;
    private MotivosCambiosCargos mcc=null;
/*
    public AdministrarMotivosCambiosCargos() {
        persistenciaMotivosCambiosCargos = new PersistenciaMotivosCambiosCargos();
        nombresMotivosCambiosCargos = new ArrayList<String>();
        mcc=null;
    }
*/
    @Override
    public List<MotivosCambiosCargos> consultarTodo() {
        try {
            motivosCambiosCargos = persistenciaMotivosCambiosCargos.buscarMotivosCambiosCargos();
        } catch (Exception e) {
            motivosCambiosCargos = null;
        }
        return motivosCambiosCargos;
    }
    @Override
    public MotivosCambiosCargos consultarPorSecuencia(BigInteger secuenciaMCC) {
        try {
            mcc = persistenciaMotivosCambiosCargos.buscarMotivoCambioCargo(secuenciaMCC);
        } catch (Exception e) {
            mcc = null;
        }
        return mcc;
    }
    @Override
    public List<String> consultarNombreTodo() {
        try {            
            nombresMotivosCambiosCargos = persistenciaMotivosCambiosCargos.buscarNombresMotivosCambiosCargos();            
        } catch (Exception e) {
            System.out.println("AdministrarMotivosCambiosCargos: consultarNombreTodo trae null");
            nombresMotivosCambiosCargos = null;
        }
        return nombresMotivosCambiosCargos;
    }        

}
