package com.tibbo.datatable;

import com.tibbo.aggregate.common.datatable.*;

import java.sql.Date;

public class StaticDataTable
{
  public static DataTable getSimpleTable()
  {
    TableFormat tf = new TableFormat(1,1);
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_INTEGER_TEST, FieldFormat.INTEGER_FIELD, "Integer поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_BOOLEAN_FIELD, FieldFormat.BOOLEAN_FIELD, "Bool поле", true, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_LONG_FIELD, FieldFormat.LONG_FIELD, "Long поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_FLOAT_FIELD, FieldFormat.FLOAT_FIELD, "Float поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DOUBLE_FIELD, FieldFormat.DOUBLE_FIELD, "Double поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_STRING_TEST, FieldFormat.STRING_FIELD, "String поле", "String", true);
    ff.setHidden(true);
    ff.setReadonly(true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATE_FIELD, FieldFormat.DATE_FIELD, "Date поле", new Date(4),true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATATABLE_FIELD, FieldFormat.DATATABLE_FIELD, "DataTble поле");
    tf.addField(ff);
   //по 1 строке всех форматов тут важна проверка на форматы
    return new SimpleDataTable(tf);
  }

  public static DataTable getInnerTable()
  {
    TableFormat tf = new TableFormat(5,5);
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_DATATABLE_FIELD, FieldFormat.DATATABLE_FIELD, "DataTble поле",getSimpleTable(), true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_BOOLEAN_FIELD, FieldFormat.BOOLEAN_FIELD, "Bool поле", true, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_LONG_FIELD, FieldFormat.LONG_FIELD, "Long поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_FLOAT_FIELD, FieldFormat.FLOAT_FIELD, "Float поле", 0, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_DOUBLE_FIELD, FieldFormat.DOUBLE_FIELD, "Double поле", 0, true);
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
    TableFormat tf = new TableFormat(1,51);
    FieldFormat ff = FieldFormat.create(StaticDataTableHelper.FIELD_INTEGER_TEST, FieldFormat.INTEGER_FIELD, "Integer поле", 10, true);
    tf.addField(ff);

    ff = FieldFormat.create(StaticDataTableHelper.FIELD_STRING_TEST, FieldFormat.STRING_FIELD, "String поле", "10", true);
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
}
