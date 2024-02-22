package com.gdsc.foodsaver.domain.foodbank.service;

import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import com.gdsc.foodsaver.domain.foodbank.repository.FoodbankRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodbankCsvDataLoaderService {
    private final FoodbankRepository foodBankRepository;

    public void loadCsvData(String filePath) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "EUC-KR"))) {
            List<String[]> csvData = reader.readAll();

            // Skip the header if it exists
            boolean skipHeader = true;

            for (String[] row : csvData) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                log.info("name : " + row[5].toString() + " / location : " + row[1] + " / address : " + row[7] + " / detailaddress : " + row[8] + " / phoneNumber : " + row[6]);

                Foodbank foodbank = Foodbank.builder()
                        .name(row[5])
                        .location(row[1])
                        .address(row[7])
                        .detailAddress(row[8])
                        .phoneNumber(row[6])
                        .build();

                foodBankRepository.save(foodbank);
            }

            log.info("CSV data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
