/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.backend.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "Ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findByVentaID", query = "SELECT v FROM Venta v WHERE v.ventaID = :ventaID")
    , @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "VentaID")
    private Integer ventaID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @JoinColumn(name = "UsuarioID", referencedColumnName = "UsuarioID")
    @ManyToOne(optional = false)
    private Usuario usuarioID;
    
    @JoinColumn(name = "VehiculoID", referencedColumnName = "VehiculoID")
    @ManyToOne(optional = false)
    private Vehiculo vehiculoID;

    public Venta() {
    }

    public Venta(Integer ventaID) {
        this.ventaID = ventaID;
    }

    public Venta(Integer ventaID, Date fecha) {
        this.ventaID = ventaID;
        this.fecha = fecha;
    }

    public Integer getVentaID() {
        return ventaID;
    }

    public void setVentaID(Integer ventaID) {
        this.ventaID = ventaID;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Usuario usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Vehiculo getVehiculoID() {
        return vehiculoID;
    }

    public void setVehiculoID(Vehiculo vehiculoID) {
        this.vehiculoID = vehiculoID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventaID != null ? ventaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.ventaID == null && other.ventaID != null) || (this.ventaID != null && !this.ventaID.equals(other.ventaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.concesionario.backend.entities.Venta[ ventaID=" + ventaID + " ]";
    }
    
}
