package org.cinchapi.concourse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.cinchapi.concourse.testing.Variables;
import org.cinchapi.concourse.util.TestData;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Tests new API named Audit which returns a time difference greater than
 * Start time provided and less than end time
 * 
 * @author Vijay
 *
 */
public class AuditTest extends ConcourseIntegrationTest {
    
  @Test
  public void testAuditRangeSanityCheck(){
      String key = "foo";
      long record = 1;
      client.add(key, 1, record);
      client.add(key, 2, record);
      client.add(key, 3, record);
      Map<Timestamp, String> auditing = client.audit(key, record);
      Timestamp preStart = Iterables.get(auditing.keySet(), 0);
      Timestamp start = Iterables.get(auditing.keySet(), 1);
      auditing = client.audit(key, record, start, Timestamp.now());
      assertFalse(auditing.keySet().contains(preStart));
      assertEquals(2, auditing.size());
  }

  @Test
  public void testAuditTimestampReturnGreaterThanStartAndLessThanEndCheck(){
      String key = "foo";
      long record = 1;
      client.add(key, 1, record);
      client.add(key, 2, record);
      client.add(key, 3, record);
      client.add(key, 4, record);
      client.add(key, 5, record);
      client.add(key, 6, record);
      Map<Timestamp, String> auditing = client.audit(key, record);
      Timestamp preStart = Iterables.get(auditing.keySet(), 1);
      Timestamp start = Iterables.get(auditing.keySet(), 2);
      Timestamp end = Iterables.get(auditing.keySet(), 4);
      Timestamp postend1 = Iterables.get(auditing.keySet(), 5);
      auditing = client.audit(key, record, start, end);
      assertFalse(auditing.keySet().contains(preStart));
      assertFalse(auditing.keySet().contains(postend1));
      assertEquals(2, auditing.size());
      Entry<Timestamp, String> entry = null;
      for(int i = 0; i < auditing.size(); i++) {
          entry = Iterables.get(auditing.entrySet(), i);
          if(entry.getKey().getMicros() > end.getMicros()) {
              System.out.println("Error\n");
          }
          if(entry.getKey().getMicros() < start.getMicros()) {
              System.out.println("Error\n");
          }    
      }
  }

  @Test
  public void testAuditTimestampReturnGreaterThanStartAndLessThanDefaultEndCheck(){
      String key = "foo";
      long record = 1;
      client.add(key, 1, record);
      client.add(key, 2, record);
      client.add(key, 3, record);
      client.add(key, 4, record);
      Map<Timestamp, String> auditing = client.audit(key, record);
      Timestamp preStart = Iterables.get(auditing.keySet(), 1);
      Timestamp start = Iterables.get(auditing.keySet(), 2);
      Timestamp end = Iterables.get(auditing.keySet(), 3);
      auditing = client.audit(key, record, start);
      client.add(key, 5, record);
      client.add(key, 6, record);
      Map<Timestamp, String> newaudit = client.audit(key, record);
      Timestamp postend1 = Iterables.get(newaudit.keySet(), 5);
      assertFalse(auditing.keySet().contains(preStart));
      assertFalse(auditing.keySet().contains(postend1));
      assertEquals(2, auditing.size());
      Entry<Timestamp, String> entry = null;
      for(int i = 0; i < auditing.size(); i++) {
          entry = Iterables.get(auditing.entrySet(), i);
          if(entry.getKey().getMicros() > end.getMicros()) {
              System.out.println("Error\n");
          }
          if(entry.getKey().getMicros() < start.getMicros()) {
              System.out.println("Error\n");
          }    
      }
  }

  @Test
  public void testAuditTimestampReturnGreaterThanStartAndLessThanEndCheckWithoutKey(){
      String key = "foo";
      long record = 1;
      client.add(key, 1, record);
      client.add(key, 2, record);
      client.add(key, 3, record);
      client.add(key, 4, record);
      client.add(key, 5, record);
      client.add(key, 6, record);
      Map<Timestamp, String> auditing = client.audit(key, record);
      Timestamp preStart = Iterables.get(auditing.keySet(), 1);
      Timestamp start = Iterables.get(auditing.keySet(), 2);
      Timestamp end = Iterables.get(auditing.keySet(), 4);
      Timestamp postend1 = Iterables.get(auditing.keySet(), 5);
      auditing = client.audit(record, start, end);
      assertFalse(auditing.keySet().contains(preStart));
      assertFalse(auditing.keySet().contains(postend1));
      assertEquals(2, auditing.size());
      Entry<Timestamp, String> entry = null;
      for(int i = 0; i < auditing.size(); i++) {
          entry = Iterables.get(auditing.entrySet(), i);
          if(entry.getKey().getMicros() > end.getMicros()) {
              System.out.println("Error\n");
          }
          if(entry.getKey().getMicros() < start.getMicros()) {
              System.out.println("Error\n");
          }    
      }
  }

  @Test
  public void testAuditTimestampReturnGreaterThanStartAndLessThanDefaultEndCheckWithoutKey(){
      String key = "foo";
      long record = 1;
      client.add(key, 1, record);
      client.add(key, 2, record);
      client.add(key, 3, record);
      client.add(key, 4, record);
      Map<Timestamp, String> auditing = client.audit(key, record);
      Timestamp preStart = Iterables.get(auditing.keySet(), 1);
      Timestamp start = Iterables.get(auditing.keySet(), 2);
      Timestamp end = Iterables.get(auditing.keySet(), 3);
      auditing = client.audit(record, start);
      client.add(key, 5, record);
      client.add(key, 6, record);
      Map<Timestamp, String> newaudit = client.audit(key, record);
      Timestamp postend1 = Iterables.get(newaudit.keySet(), 5);
      assertFalse(auditing.keySet().contains(preStart));
      assertFalse(auditing.keySet().contains(postend1));
      assertEquals(2, auditing.size());
      Entry<Timestamp, String> entry = null;
      for(int i = 0; i < auditing.size(); i++) {
          entry = Iterables.get(auditing.entrySet(), i);
          if(entry.getKey().getMicros() > end.getMicros()) {
              System.out.println("Error\n");
          }
          if(entry.getKey().getMicros() < start.getMicros()) {
              System.out.println("Error\n");
          }    
      }
  }
}  
