package com.tibbo;

public class ServerMessagesHelper
{
  public static String FIRST_MESSAGE = "First message";
  public static String SECOND_MESSAGE = "secondMessage";
  public static String THIRD_MESSAGE = "Third Message";
  private static char FIRST_CHAR = '\u0001';
  private static char SECOND_CHAR = '\u0002';
  private static char THIRD_CHAR = '\u0003';
  
  public static byte[] prepareString(String value)
  {
    //FIRST_CHAR - первый симлов, нового сообщения;
    //SECOND_CHAR - разделение между header и body
    //FIRST_CHAR + LENGTH + SECOND_CHAR + VALUE + THIRD_CHAR
    return new byte[0];
  }
  
  public static byte[] prepareObject(Object value)
  {
    return new byte[0];
  }
  
  public static String getValue(byte[] values)
  {
    return null;
  }
}
