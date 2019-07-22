package com.tibbo.protocol;

public interface ObjectProtocol<Obj>
{
  public void setObject(Obj object);
  public Obj getObject();
  public byte[] getBytes();
}
