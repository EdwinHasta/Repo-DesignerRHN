/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "USUARIOSVISTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuariosVistas.findAll", query = "SELECT u FROM UsuariosVistas u")})
public class UsuariosVistas implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 10)
    @Column(name = "ALIAS")
    private String alias;
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 1)
    @Column(name = "BASEESTRUCTURA")
    private String baseestructura;
    @Size(max = 1000)
    @Column(name = "ESTRUCTURAJOIN")
    private String estructurajoin;
    @Size(max = 3000)
    @Column(name = "CONDICION")
    private String condicion;
    @Size(max = 100)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "HINTPRINCIPAL")
    private String hintprincipal;
    @Size(max = 30)
    @Column(name = "NOMBREVISTA")
    private String nombrevista;
    @JoinColumn(name = "OBJETODB", referencedColumnName = "SECUENCIA")
    @ManyToOne(optional = false)
    private ObjetosDB objetodb;
    @Transient
    private String estadoBaseEstructura;

    public UsuariosVistas() {
    }

    public UsuariosVistas(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public UsuariosVistas(BigInteger secuencia, String alias, String baseestructura) {
        this.secuencia = secuencia;
        this.alias = alias;
        this.baseestructura = baseestructura;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getAlias() {
         if (alias == null) {
            alias = ("");
        }
        return alias.toUpperCase();
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getBaseestructura() {
        if(baseestructura == null){
            baseestructura = "S";
        }
        return baseestructura.toUpperCase();
    }

    public void setBaseestructura(String baseestructura) {
        
        this.baseestructura = baseestructura;
    }

    public String getEstructurajoin() {
        return estructurajoin;
    }

    public void setEstructurajoin(String estructurajoin) {
        this.estructurajoin = estructurajoin;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getDescripcion() {
         if (descripcion == null) {
            descripcion = ("");
        }
        return descripcion.toUpperCase();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHintprincipal() {
        return hintprincipal;
    }

    public void setHintprincipal(String hintprincipal) {
        this.hintprincipal = hintprincipal;
    }

    public String getNombrevista() {
        return nombrevista;
    }

    public void setNombrevista(String nombrevista) {
        this.nombrevista = nombrevista;
    }

    public ObjetosDB getObjetodb() {
        if (objetodb == null){
            objetodb = new ObjetosDB();
        }
        return objetodb;
    }

    public void setObjetodb(ObjetosDB objetodb) {
        this.objetodb = objetodb;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuariosVistas)) {
            return false;
        }
        UsuariosVistas other = (UsuariosVistas) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.UsuariosVistas[ secuencia=" + secuencia + " ]";
    }

    public String getEstadoBaseEstructura() {
        if (estadoBaseEstructura == null) {
            if (baseestructura == null) {
                estadoBaseEstructura = "SI";

            } else {
                if (baseestructura.equalsIgnoreCase("S")) {
                    estadoBaseEstructura = "SI";
                } else if (baseestructura.equalsIgnoreCase("N")) {
                    estadoBaseEstructura = "NO";
                } 
            }
        }
        
        return estadoBaseEstructura;
    }

    public void setEstadoBaseEstructura(String estadoBaseEstructura) {
        if (estadoBaseEstructura.equals("SI")) {
            setBaseestructura("S");
        } else if (estadoBaseEstructura.equals("NO")) {
            setBaseestructura("N");
        } 
        this.estadoBaseEstructura = estadoBaseEstructura;
    }
    
    
    
}
