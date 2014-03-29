package com.searchbox.framework.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<K extends Serializable> {

  @Column(name = "creation_time", nullable = false)
  private Date creationTime;

  @Column(name = "modification_time", nullable = false)
  private Date modificationTime;

  @Version
  private long version;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private K id;

  public K getId() {
    return id;
  }

  public void setId(K id) {
    this.id = id;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public void setModificationTime(Date modificationTime) {
    this.modificationTime = modificationTime;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public Date getModificationTime() {
    return modificationTime;
  }

  public long getVersion() {
    return version;
  }

  @PrePersist
  public void prePersist() {
    Date now = new Date();
    this.creationTime = now;
    this.modificationTime = now;
  }

  @PreUpdate
  public void preUpdate() {
    this.modificationTime = new Date();
  }
}
