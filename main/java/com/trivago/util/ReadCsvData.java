package com.trivago.util;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReadCsvData {
  public static List<List<String>> readDataFromCsv(String fileName) {
    Resource resource = new ClassPathResource(fileName);
    List<List<String>> records = new ArrayList<>();

    try (CSVReader csvReader =
        new CSVReader(new FileReader(resource.getFile().getAbsolutePath()))) {
      String[] values;
      while ((values = csvReader.readNext()) != null) {
        records.add(Arrays.asList(values));
      }

    } catch (FileNotFoundException e) {
      log.info("FileNotFoundException:" + e.getLocalizedMessage());

    } catch (IOException e) {
      log.info("IOException:" + e.getLocalizedMessage());
    }
    return records;
  }
}
