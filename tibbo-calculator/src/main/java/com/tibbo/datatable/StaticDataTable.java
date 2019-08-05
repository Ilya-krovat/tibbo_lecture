package com.tibbo.datatable;

import com.tibbo.aggregate.common.datatable.*;

import java.sql.Date;

public class StaticDataTable
{
  public static DataTable getSimpleTable()
  {
    TableFormat tf = new TableFormat(1,1);
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_INTEGER_TEST, FieldFormat.INTEGER_FIELD, StaticDataTableHelper.DESTRIPTION_INTEGER_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_BOOLEAN_FIELD, FieldFormat.BOOLEAN_FIELD, StaticDataTableHelper.DESTRIPTION_BOOLEAN_FIELD, true, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_LONG_FIELD, FieldFormat.LONG_FIELD, StaticDataTableHelper.DESTRIPTION_LONG_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_FLOAT_FIELD, FieldFormat.FLOAT_FIELD, StaticDataTableHelper.DESTRIPTION_FLOAT_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DOUBLE_FIELD, FieldFormat.DOUBLE_FIELD, StaticDataTableHelper.DESTRIPTION_DOUBLE_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_STRING_TEST, FieldFormat.STRING_FIELD, StaticDataTableHelper.DESTRIPTION_STRING_FIELD, StaticDataTableHelper.DEFAULT_STRING, true);
    ff.setHidden(true);
    ff.setReadonly(true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATE_FIELD, FieldFormat.DATE_FIELD, StaticDataTableHelper.DESTRIPTION_DATE_FIELD, new Date(4),true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATATABLE_FIELD, FieldFormat.DATATABLE_FIELD, StaticDataTableHelper.DESTRIPTION_DATATABLE_FIELD);
    tf.addField(ff);
   //по 1 строке всех форматов тут важна проверка на форматы шз
    return new SimpleDataTable(tf);
  }

  public static DataTable getInnerTable()
  {
    TableFormat tf = new TableFormat();
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATATABLE_FIELD, FieldFormat.DATATABLE_FIELD, StaticDataTableHelper.DESTRIPTION_DATATABLE_FIELD,getSimpleTable(), true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_BOOLEAN_FIELD, FieldFormat.BOOLEAN_FIELD, StaticDataTableHelper.DESTRIPTION_BOOLEAN_FIELD, StaticDataTableHelper.DEFAULT_BOOL, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_LONG_FIELD, FieldFormat.LONG_FIELD, StaticDataTableHelper.DESTRIPTION_LONG_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_FLOAT_FIELD, FieldFormat.FLOAT_FIELD, StaticDataTableHelper.DESTRIPTION_FLOAT_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DOUBLE_FIELD, FieldFormat.DOUBLE_FIELD, StaticDataTableHelper.DESTRIPTION_DOUBLE_FIELD, StaticDataTableHelper.DEFAULT_NUMB, true);
    tf.addField(ff);

    DataTable table = new SimpleDataTable(tf);
    table.addRecord(getSimpleTable(),true,123);
    DataRecord rec = table.addRecord();
    rec.setValue(StaticDataTableHelper.FIELD_BOOLEAN_FIELD, false);
    rec = table.addRecord();
    rec.setValue(StaticDataTableHelper.FIELD_DOUBLE_FIELD, 35);
    rec = table.addRecord();
    rec.setValue(StaticDataTableHelper.FIELD_FLOAT_FIELD, 634);
    rec = table.addRecord();
    rec.setValue(StaticDataTableHelper.FIELD_LONG_FIELD, 36);
    return table;
  }
  
  public static DataTable getBigTable()
  {
    TableFormat tf = new TableFormat();
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_INTEGER_TEST, FieldFormat.INTEGER_FIELD, StaticDataTableHelper.DESTRIPTION_INTEGER_FIELD, 10, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_STRING_TEST, FieldFormat.STRING_FIELD, StaticDataTableHelper.DESTRIPTION_STRING_FIELD, "10", true);
    tf.addField(ff);

    DataTable table = new SimpleDataTable(tf);

    DataRecord rec;
    for(int i=0; i<50;i++)
    {
      rec = table.addRecord();
      if(i==45) {
        rec.setValue(StaticDataTableHelper.FIELD_INTEGER_TEST, 50);
        rec.setValue(StaticDataTableHelper.FIELD_STRING_TEST, "50");
      }
      else {
        rec.setValue(StaticDataTableHelper.FIELD_INTEGER_TEST, 10);
        rec.setValue(StaticDataTableHelper.FIELD_STRING_TEST, "10");
      }
    }
    return table;
  }

  public static TableFormat getTableFormat()
  {
    TableFormat tf = new TableFormat();
    FieldFormat ff = FieldFormat.create("First Table", FieldFormat.DATATABLE_FIELD, StaticDataTableHelper.DESTRIPTION_INTEGER_FIELD, getSimpleTable(), true);
    tf.addField(ff);
     ff = FieldFormat.create("Second Table", FieldFormat.DATATABLE_FIELD, StaticDataTableHelper.DESTRIPTION_INTEGER_FIELD, getInnerTable(), true);
    tf.addField(ff);
     ff = FieldFormat.create("Third Table", FieldFormat.DATATABLE_FIELD, StaticDataTableHelper.DESTRIPTION_INTEGER_FIELD, getBigTable(), true);
    tf.addField(ff);
    return tf;
  }
}