package com.example.springtwocatchvalidation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HelloController {
    @PostMapping
    public String hello(@Valid @RequestBody HelloRequestDto dto) {
        return dto.getMessage();
    }

    @GetMapping
    public ResponseEntity<?> tesJson() {
        JSONArray cats = new JSONArray();
//        JSONObject cat1 = new JSONObject();
//        cat1.put("date", "1/12/2012");
//        JSONObject cat2 = new JSONObject();
//        cat2.put("date", "2/12/2012");
//
//        cats.put(cat1);
//        cats.put(cat2);

        for (int i = 0; i < 2; i++) {
            JSONObject cat = new JSONObject();
            cat.put("date", "1/12/2012");
            cats.put(cat);
        }

        // Custom comparator to sort JSONObjects by "date" field in descending order
        Comparator<JSONObject> dateComparator = (o1, o2) -> {
            String dateStr1 = o1.optString("date");
            String dateStr2 = o2.optString("date");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return sdf.parse(dateStr2).compareTo(sdf.parse(dateStr1));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        };

        @SuppressWarnings("rawtypes")
        JSONArray sortedCats = cats.toList().stream()
                .map(obj -> new JSONObject((Map) obj))
                .sorted(dateComparator)
                .collect(JSONArray::new, JSONArray::put, JSONArray::put);

        return ResponseEntity.ok().body(sortedCats.toString());
    }
}
