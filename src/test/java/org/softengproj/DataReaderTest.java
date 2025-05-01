package org.softengproj;

import org.junit.Test;
import org.softengproj.FileIO.DataReader;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataReaderTest {
    private DataReader dr = new DataReader();

    @Test
    public void query_validStatement_returnsCorrectValue() {
        try {
            var result = dr.query("select ID from clicks where Click_Cost == 8.187738;").getString(1);
            assertEquals(result, "512865360757885952");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void query_invalidStatement_returnsNull() {
        var result = dr.query("foo");
        assertNull(result);
    }

    @Test
    public void addTableFromCSV_success() {
        dr.addTableFromCSV("src\\test\\resources\\sampledata\\click_log_small.csv");
        try {
            var result = dr.query("select id from click_log_small").getString(1);
            assertEquals(result, "ID");
        } catch (SQLException e) {
            fail();
        }
    }

//    @Test
//    public void purge_removesAll() {
//        dr.purge();
//        var result1 = dr.query("select * from clicks");
//        var result2 = dr.query("select * from impressions");
//        var result3 = dr.query("select * from user_activity");
//        assertArrayEquals(new ResultSet[]{result1, result2, result3}, new Object[]{null, null, null});
//    }
}
