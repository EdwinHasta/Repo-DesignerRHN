/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "EERSESTADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EersEstados.findAll", query = "SELECT e FROM EersEstados e"),
    @NamedQuery(name = "EersEstados.findBySecuencia", query = "SELECT e FROM EersEstados e WHERE e.secuencia = :secuencia"),
    @NamedQuery(name = "EersEstados.findByTipoeer", query = "SELECT e FROM EersEstados e WHERE e.tipoeer = :tipoeer"),
    @NamedQuery(name = "EersEstados.findByCodigo", query = "SELECT e FROM EersEstados e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "EersEstados.findByDescripcion", query = "SELECT e FROM EersEstados e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "EersEstados.findByEnviaremail", query = "SELECT e FROM EersEstados e WHERE e.enviaremail = :enviaremail"),
    @NamedQuery(name = "EersEstados.findByMensaje", query = "SELECT e FROM EersEstados e WHERE e.mensaje = :mensaje"),
    @NamedQuery(name = "EersEstados.findByEmail", query = "SELECT e FROM EersEstados e WHERE e.email = :email")})
public class EersEstados implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigInteger secuencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPOEER")
    private String tipoeer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "ENVIAREMAIL")
    private String enviaremail;
    @Size(max = 200)
    @Column(name = "MENSAJE")
    private String mensaje;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @JoinColumn(name = "EEROPCIONESTADOAPRUEBA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EersOpcionesEstados eeropcionestadoaprueba;
    @JoinColumn(name = "EEROPCIONESTADOCANCELA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private EersOpcionesEstados eeropcionestadocancela;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eerestado")
    private Collection<EersFlujos> eersFlujosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eerestado")
    private Collection<EersCabeceras> eersCabecerasCollection;

    public EersEstados() {
    }

    public EersEstados(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public EersEstados(BigInteger secuencia, String tipoeer, short codigo, String descripcion) {
        this.secuencia = secuencia;
        this.tipoeer = tipoeer;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
    }

    public String getTipoeer() {
        return tipoeer;
    }

    public void setTipoeer(String tipoeer) {
        this.tipoeer = tipoeer;
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnviaremail() {
        return enviaremail;
    }

    public void setEnviaremail(String enviaremail) {
        this.enviaremail = enviaremail;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EersOpcionesEstados getEeropcionestadoaprueba() {
        return eeropcionestadoaprueba;
    }

    public void setEeropcionestadoaprueba(EersOpcionesEstados eeropcionestadoaprueba) {
        this.eeropcionestadoaprueba = eeropcionestadoaprueba;
    }

    public EersOpcionesEstados getEeropcionestadocancela() {
        return eeropcionestadocancela;
    }

    public void setEeropcionestadocancela(EersOpcionesEstados eeropcionestadocancela) {
        this.eeropcionestadocancela = eeropcionestadocancela;
    }

    @XmlTransient
    public Collection<EersFlujos> getEersFlujosCollection() {
        return eersFlujosCollection;
    }

    public void setEersFlujosCollection(Collection<EersFlujos> eersFlujosCollection) {
        this.eersFlujosCollection = eersFlujosCollection;
    }

    @XmlTransient
    public Collection<EersCabeceras> getEersCabecerasCollection() {
        return eersCabecerasCollection;
    }

    public void setEersCabecerasCollection(Collection<EersCabeceras> eersCabecerasCollection) {
        this.eersCabecerasCollection = eersCabecerasCollection;
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
        if (!(object instanceof EersEstados)) {
            return false;
        }
        EersEstados other = (EersEstados) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.EersEstados[ secuencia=" + secuencia + " ]";
    }
    
}
