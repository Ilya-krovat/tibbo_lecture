package com.tibbo.datatable;

import com.tibbo.aggregate.common.datatable.DataRecord;
import com.tibbo.aggregate.common.datatable.DataTable;
import com.tibbo.aggregate.common.datatable.FieldFormat;
import com.tibbo.aggregate.common.datatable.TableFormat;
import junit.framework.TestCase;

public class TestDataTable extends TestCase
{
  public void testTableFormat() throws Exception
  {
    DataTable result = StaticDataTable.getSimpleTable();
    assertNotNull(result);
  
    assertNotNull(result.getFormat());
    TableFormat format = result.getFormat();
  
    assertEquals(8, format.getFieldCount());
    
    assertEquals(1, format.getMaxRecords());
    assertEquals(1, format.getMinRecords());
  
    FieldFormat ff = format.getField(0);
    assertEquals(FieldFormat.INTEGER_FIELD, ff.getType());
    assertEquals(StaticDataTableHelper.FIELD_INTEGER_TEST, ff.getName());
  
    ff = format.getField(StaticDataTableHelper.FIELD_STRING_TEST);
    assertEquals(ff, format.getField(5));
    
    ff = format.getField(StaticDataTableHelper.FIELD_BOOLEAN_FIELD);
    assertEquals(true, ff.getDefaultValue());
    
    ff = format.getField(StaticDataTableHelper.FIELD_DOUBLE_FIELD);
    assertTrue(ff.isNullable());
    
    ff = format.getField(StaticDataTableHelper.FIELD_STRING_TEST);
    assertTrue(ff.isHidden());
    assertTrue(ff.isReadonly());
  }
  
  public void testInnerTable() throws Exception
  {
    DataTable result = StaticDataTable.getInnerTable();
    assertNotNull(result);
    assertTrue(result.hasField(StaticDataTableHelper.FIELD_DATATABLE_FIELD));
    
    assertEquals(Integer.valueOf(5), result.getRecordCount());
    
    DataRecord record = result.getRecord(0);
    assertNotNull(StaticDataTable.getSimpleTable());
    assertEquals(record.getFormat(), StaticDataTable.getSimpleTable().getFormat());
  }
  
  public void testBigTable() throws Exception
  {
    DataTable result = StaticDataTable.getBigTable();
    assertNotNull(result);
    
    assertEquals(Integer.valueOf(50), result.getRecordCount());
    
    assertEquals(Integer.valueOf(10), result.rec().getInt(StaticDataTableHelper.FIELD_INTEGER_TEST));
    assertEquals("10", result.rec().getString(StaticDataTableHelper.FIELD_STRING_TEST));
    
    assertEquals(Integer.valueOf(50), result.getRecord(45).getInt(StaticDataTableHelper.FIELD_INTEGER_TEST));
    assertEquals("50", result.getRecord(45).getString(StaticDataTableHelper.FIELD_INTEGER_TEST));
  }
}
