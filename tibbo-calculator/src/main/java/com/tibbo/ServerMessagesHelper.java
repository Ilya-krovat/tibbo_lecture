package com.tibbo;

public class ServerMessagesHelper
{
  public static String FIRST_MESSAGE = "First message";
  public static String SECOND_MESSAGE = "Second Message";
  public static String THIRD_MESSAGE = "Third Message";
  public static String MESSAGE_ERROR = "error!!!";
  private static char FIRST_CHAR = '\u0001';
  private static char SECOND_CHAR = '\u0002';
  private static char THIRD_CHAR = '\u0003';
  public static  String NAME_OF_HOST = "localhost";
  
  public static byte[] prepareString(String value)
  {
    //FIRST_CHAR - первый симлов, нового сообщения;
    //SECOND_CHAR - разделение между header и body
    //FIRST_CHAR + LENGTH + SECOND_CHAR + VALUE + THIRD_CHAR
    return (FIRST_CHAR + value.length() + SECOND_CHAR + value + THIRD_CHAR).getBytes();
  }
  
  public static byte[] prepareObject(Object value)
  {
    return new byte[0];
  }
  
  public static String getValue(byte[] values)
  {
    boolean flag = false;
    String string = null;
    for(int i =0;i<values.length;i++)
    {
      int bpos = i << 1;
      char c = (char)(((values[bpos]&0x00FF)<<8) + (values[bpos+1]&0x00FF));
      if(c==SECOND_CHAR)
        flag = true;
      if(c==THIRD_CHAR)
        flag=false;
      if(flag)
        string+= c;
    }
    return string;
  }
}
