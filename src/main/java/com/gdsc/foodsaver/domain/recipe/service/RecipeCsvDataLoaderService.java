package com.gdsc.foodsaver.domain.recipe.service;

import com.gdsc.foodsaver.domain.foodbank.entity.Foodbank;
import com.gdsc.foodsaver.domain.foodbank.repository.FoodbankRepository;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import com.gdsc.foodsaver.domain.recipe.repository.RecipeRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeCsvDataLoaderService {
    private final RecipeRepository recipeRepository;

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

                log.info(row[0] + row[1] + row[2] + row[3] + row[4] + row[5] + row[6] + row[7] + row[8] + row[9] + row[10] + row[11] + row[12] + row[13]);

                Recipe recipe = Recipe.builder()
                        .name(row[0])
                        .cookingMethod(row[1])
                        .dishType(row[2])
                        .servingSize(row[3])
                        .calories(Double.parseDouble(row[4]))
                        .carbohydrates(Double.parseDouble(row[5]))
                        .protein(Double.parseDouble(row[6]))
                        .fat(Double.parseDouble(row[7]))
                        .sodium(Double.parseDouble(row[8]))
                        .hashtags(row[9])
                        .imageUrlSmall(row[10])
                        .imageUrlBig(row[11])
                        .ingredients(row[12])
                        .recipe(row[13])
                        .build();

                recipeRepository.save(recipe);
            }

            log.info("CSV data loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
